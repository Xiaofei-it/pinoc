/**
 * Copyright 2017 iQIYI.com
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iqiyi.pinoc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import xiaofei.library.zlang.CompileException;
import xiaofei.library.zlang.JavaLibrary;
import xiaofei.library.zlang.Library;
import xiaofei.library.zlang.ZlangRuntimeException;

/**
 * Created by Xiaofei on 2017/10/17.
 */

class PinocCore {

    private static final String TAG = "Pinoc";

    private static volatile PinocCore sInstance = null;

    private ConcurrentHashMap<String, ConcurrentHashMap<String, LibraryWrapper>> mLibraries;

    private ConcurrentLinkedQueue<Library> mDependencies;

    private ConcurrentLinkedQueue<JavaLibrary> mJavaDependencies;

    private PinocCore() {
        mLibraries = new ConcurrentHashMap<>();
        mDependencies = new ConcurrentLinkedQueue<>();
        mJavaDependencies = new ConcurrentLinkedQueue<>();
        mJavaDependencies.add(PinocLibraries.JAVA_LIBRARY);
    }

    static PinocCore getInstance() {
        if (sInstance == null) {
            synchronized (PinocCore.class) {
                if (sInstance == null) {
                    sInstance = new PinocCore();
                }
            }
        }
        return sInstance;
    }

    void addDependency(Library library) {
        mDependencies.add(library);
    }

    void addJavaDependency(JavaLibrary library) {
        mJavaDependencies.add(library);
    }

    private static String getMethodId(String methodName, String methodSignature) {
        return methodName + "~" + methodSignature;
    }

    /**
     * @param className
     * @param methodName
     * @param methodSignature
     * @param thiz
     * @param parameters
     * @return true if replaced.
     */
    Object onEnterMethod(String className, String methodName, String methodSignature, Object thiz, Object[] parameters) {
        //Logger.i(TAG, "enter " + className + " " + methodName + " " + methodSignature);
        // We can *not* append a target to the string because if the target class has override toString, it will cause a stack-over-flow.
        ConcurrentHashMap<String, LibraryWrapper> libraries = mLibraries.get(className);
        if (libraries == null) {
            return Library.NO_RETURN_VALUE;
        }
        LibraryWrapper wrapper = libraries.get(getMethodId(methodName, methodSignature));
        final Library library = wrapper.mLibrary;
        if (library == null) {
            return Library.NO_RETURN_VALUE;
        }

        final Object[] objects = new Object[]{className, methodName, methodSignature, thiz, parameters};
        Callable callable = new Callable() {
            @Override
            public Object call() throws Exception {
                return library.execute("main", objects);
            }
        };

        Object result = Library.NO_RETURN_VALUE;
        try {
            switch (wrapper.mMode.getMode()) {
                case ThreadMode.CURRENT:
                    result = callable.call();
                    break;
                case ThreadMode.MAIN:
                    result = Schedulers.MAIN.call(callable);
                    break;
                case ThreadMode.BACKGROUND:
                    result = Schedulers.BACKGROUND.call(callable);
                    break;
            }
        } catch (ZlangRuntimeException e) {
            Logger.e(TAG, "Execution error.", e);
            return Library.NO_RETURN_VALUE;
        } catch (Throwable t) {
            Logger.e(TAG, "Unknown error.", t);
            return Library.NO_RETURN_VALUE;
        }
        return result;
    }

    private static boolean isEmpty(String string) {
        return string == null || string.equals("");
    }

    private void store(String className, String methodName, String methodSignature, LibraryWrapper libraryWrapper) {
        ConcurrentHashMap<String, LibraryWrapper> map = mLibraries.get(className);
        if (map == null) {
            map = new ConcurrentHashMap<>();
            mLibraries.put(className, map);
        }
        String key = getMethodId(methodName, methodSignature);
        map.put(key, libraryWrapper);
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
                Library.Builder builder = new Library.Builder();
                for (Library lib : mDependencies) {
                    builder.addDependency(lib);
                }
                for (JavaLibrary lib : mJavaDependencies) {
                    builder.addJavaDependency(lib);
                }
                zlangLibraries[i] = builder.addFunctions(library).build();
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
            int libraryIndex = target.optInt(Constants.LIBRARY_INDEX, -1);
            if (libraryIndex <= -1 || libraryIndex >= zlangLibraries.length) {
                continue;
            }

            Library library = zlangLibraries[libraryIndex];
            int threadMode = target.optInt(Constants.THREAD_MODE, ThreadMode.CURRENT);

            if (library != null) {
                store(className, methodName, methodSignature, new LibraryWrapper(new ThreadMode(threadMode), library));
            }
        }
    }

    static final class LibraryWrapper {
        final ThreadMode mMode;
        final Library mLibrary;

        LibraryWrapper(ThreadMode mode, Library library) {
            this.mMode = mode;
            this.mLibrary = library;
        }
    }
}
