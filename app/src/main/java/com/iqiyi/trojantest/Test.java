package com.iqiyi.trojantest;

/**
 * Created by Eric on 2017/10/18.
 */

public class Test {

    static boolean onEnterMethod(String className, String methodName, String methodSignature, Object target, Object[] parameters) {
        return className.endsWith(methodName);
    }
}
