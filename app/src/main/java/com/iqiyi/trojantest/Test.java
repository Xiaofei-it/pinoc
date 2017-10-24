package com.iqiyi.trojantest;

import android.app.Activity;

import com.iqiyi.trojan.Trojan;

import java.util.Random;

import xiaofei.library.zlang.Library;

/**
 * Created by Eric on 2017/10/18.
 */

public class Test {

    void g1(String s, int i) {
        Object result = Trojan.onEnterMethod("Test", "g1", "(Ljava/lang/String;I)V", this, new Object[]{s, i});
        if (result != Library.NO_RETURN_VALUE) {
            return;
        }
        System.out.println();
    }

//    private Long returnLong1(Long a, long b) {
//        int s = 0;
//        int k = 1;
//        return a + b;
//    }
//
//    private long returnLong2(Long a, long b) {
//        int s = 0;
//        int k = 1;
//        return a + b;
//    }
//
    private long returnLong1(Long a) {
//        Object result = Trojan.onEnterMethod("MainActivity", "g", "()V", this, new Object[]{a});
//        if (result != Library.NO_RETURN_VALUE) {
//            if (result instanceof Long) {
//                return (long) result;
//            }
//        }
//        int s = 0;
//        int k = 1;
        return a;
    }

    private long returnLong2(Long a) {
        Object result = Trojan.onEnterMethod("MainActivity", "g", "()V", this, new Object[]{a});
        if (result != Library.NO_RETURN_VALUE) {
            if (result instanceof Long) {
                return (Long) result;
            }
        }
        int s = 0;
        int k = 1;
        return a;
    }

    private Void returnVoid(Long a, Void b) {
        int s = 0;
        int k = 1;
        return b;
    }
    private Character returnChar1(Character a, char b) {
        int s = 0;
        int k = 1;
        return b;
    }
    private char returnChar2(Character a, char b) {
        int s = 0;
        int k = 1;
        return b;
    }
    private Activity returnA(Character a, char b) {
        int s = 0;
        int k = 1;
        return new Activity();
    }

    private Float returnFloat1(Float a) {
        float s = 0;
        int k = 1;
        return a + s + k;
    }

    private float returnFloat2(float a) {
        return a;
    }

//    private Double returnDouble1(double a) {
//        int s = 0;
//        int k = 1;
//        return a;
//    }

    private double returnDouble2(Double a) {
        int s = 0;
        int k = 1;
        return a;
    }

    private String returnString(String a) {
        int s = 0;
        int k = 1;
        return a;
    }

    private MainActivity returnMain(MainActivity a) {
        int s = 0;
        int k = 1;
        return a;
    }
}
