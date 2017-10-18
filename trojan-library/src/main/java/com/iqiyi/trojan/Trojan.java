package com.iqiyi.trojan;

/**
 * Created by Xiaofei on 2017/10/17.
 */

public class Trojan {

    private static final TrojanCore TROJAN_CORE = TrojanCore.getInstance();

    public static Object onEnterMethod(String className, String methodName, String methodSignature, Object target, Object[] parameters) {
        return TROJAN_CORE.onEnterMethod(className, methodName, methodSignature, target, parameters);
    }

    public static void config(String configuration) {
        TROJAN_CORE.config(configuration);
    }
}
