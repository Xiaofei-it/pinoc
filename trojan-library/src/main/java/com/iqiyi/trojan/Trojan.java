package com.iqiyi.trojan;

import xiaofei.library.zlang.JavaLibrary;
import xiaofei.library.zlang.Library;

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

    public static void addDependency(Library library) {
        TROJAN_CORE.addDependency(library);
    }

    public static void addJavaDependency(JavaLibrary library) {
        TROJAN_CORE.addJavaDependency(library);
    }
}
