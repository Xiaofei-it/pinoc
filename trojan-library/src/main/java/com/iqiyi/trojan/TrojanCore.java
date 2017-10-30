package com.iqiyi.trojan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import xiaofei.library.zlang.CompileException;
import xiaofei.library.zlang.Library;
import xiaofei.library.zlang.ZlangRuntimeException;

/**
 * Created by zhaolifei on 2017/10/17.
 */

class TrojanCore {

    private static final String TAG = "Trojan";

    private static volatile TrojanCore sInstance = null;

    private ConcurrentHashMap<String, ConcurrentHashMap<String, Library>> mLibraries;

    private TrojanCore() {
        mLibraries = new ConcurrentHashMap<>();
    }

    static TrojanCore getInstance() {
        if (sInstance == null) {
            synchronized (TrojanCore.class) {
                if (sInstance == null) {
                    sInstance = new TrojanCore();
                }
            }
        }
        return sInstance;
    }

    private static String getMethodId(String methodName, String methodSignature) {
        return methodName + "~" + methodSignature;
    }

    /**
     *
     * @param className
     * @param methodName
     * @param methodSignature
     * @param target
     * @param parameters
     * @return true if replaced.
     */
    Object onEnterMethod(String className, String methodName, String methodSignature, Object target, Object[] parameters) {
        Logger.i(TAG, "enter " + className + " " + methodName + " " + methodSignature + " " + target);
        ConcurrentHashMap<String, Library> libraries = mLibraries.get(className);
        if (libraries == null) {
            return Library.NO_RETURN_VALUE;
        }
        Library library = libraries.get(getMethodId(methodName, methodSignature));
        if (library == null) {
            return Library.NO_RETURN_VALUE;
        }
        try {
            Object result = library.execute("main", new Object[]{className, methodName, methodSignature, target, parameters});
            return result;
        } catch (ZlangRuntimeException e) {
            Logger.e(TAG, "Execution error.", e);
            return Library.NO_RETURN_VALUE;
        }
    }

    private static boolean isEmpty(String string) {
        return string == null || string.equals("");
    }

    private void store(String className, String methodName, String methodSignature, Library library) {
        ConcurrentHashMap<String, Library> map = mLibraries.get(className);
        if (map == null) {
            map = new ConcurrentHashMap<>();
            mLibraries.put(className, map);
        }
        String key = getMethodId(methodName, methodSignature);
        map.put(key, library);
    }

    void config(String configuration) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(configuration);
        } catch (JSONException e) {
            Logger.e(TAG, "Configuration error.", e);
            return;
        }
        JSONArray targets = jsonObject.optJSONArray("targets");
        if (targets == null) {
            Logger.e(TAG, "Targets missing in the configuration.");
            return;
        }
        JSONArray libraries = jsonObject.optJSONArray("libraries");
        if (libraries == null) {
            Logger.e(TAG, "Libraries missing in the configuration.");
            return;
        }
        int libraryNumber = libraries.length();
        Library[] zlangLibraries = new Library[libraryNumber];
        for (int i = 0; i < libraryNumber; ++i) {
            String library = libraries.optString(i);
            if (isEmpty(library)) {
                zlangLibraries[i] = null;
                continue;
            }
            try {
                Library lib = new Library.Builder().addFunctions(library).build(); //depende
                zlangLibraries[i] = lib;
            } catch (CompileException e) {
                Logger.e(TAG, "Compile exception occurs in " + library, e);
                zlangLibraries[i] = null;
            }
        }
        int methodNumber = targets.length();
        for (int i = 0; i < methodNumber; ++i) {
            JSONObject target = targets.optJSONObject(i);
            String className = target.optString(Constants.CLASS);
            if (isEmpty(className)) {
                continue;
            }
            String methodName = target.optString(Constants.METHOD_NAME);
            if (isEmpty(methodName)) {
                continue;
            }
            String methodSignature = target.optString(Constants.METHOD_SIGNATURE);
            if (isEmpty(methodSignature)) {
                continue;
            }
            int libraryIndex = target.optInt(Constants.LIBRARY_INDEX);
            Library library = zlangLibraries[libraryIndex];
            if (library != null) {
                store(className, methodName, methodSignature, library);
            }
        }
    }
}
