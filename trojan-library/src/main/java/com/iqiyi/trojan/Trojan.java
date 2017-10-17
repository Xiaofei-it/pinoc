package com.iqiyi.trojan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Xiaofei on 2017/10/17.
 */

public class Trojan {

    private static final TrojanCore TROJAN_CORE = TrojanCore.getInstance();

    public static void onEnterMethod(String className, String methodName, String methodSignature, Object target, Object[] parameters) {
        TROJAN_CORE.onEnterMethod(className, methodName, methodSignature, target, parameters);
    }

    public static void config(String configuration) {
        TROJAN_CORE.config(configuration);
    }
}
