package com.iqiyi.trojantest;

import com.iqiyi.trojan.Trojan;

import xiaofei.library.zlang.Library;

/**
 * Created by Eric on 2017/10/24.
 */

public class X2 {
    long a;
    int b;
    String c;
    static long a2;
    static int b2;
    static String c2;
//    static {
//        a2 = b2 = 0;
//        c2 = "";
//    }
    {
        a = 0;
        b = 1;
        c = "";
        System.gc();
    }
    public X2(int d){
        a = b = d;
        c = "" +d;
    }
    public static void s() {

    }

    public void st() {

    }
    public int g1(long a, double b, int c, boolean d) {
        a2=90;
        Object r = Trojan.onEnterMethod("com/iqiyi/trojantest/X", "g", "(II)I", this, new Object[]{Long.valueOf(a), Double.valueOf(b), Integer.valueOf(c), Boolean.valueOf(d)});
        if(r != Library.NO_RETURN_VALUE) {
            if (r instanceof Integer) {
                return (int) r;
            }
        }
        double e = b + c;
        return (int) e * 2;
    }

    public int g2(long a) {
        return (int) a * 2;
    }

    public static int g3(long a, double b, int c, boolean d) {
        a2=90;
        Object r = Trojan.onEnterMethod("com/iqiyi/trojantest/X", "g", "(II)I", null, new Object[]{Long.valueOf(a), Double.valueOf(b), Integer.valueOf(c), Boolean.valueOf(d)});
        if(r != Library.NO_RETURN_VALUE) {
            if (r instanceof Integer) {
                return (int) r;
            }
        }
        double e = b + c;
        return (int) e * 2;
    }

    public static int g4(long a) {
        return (int) a * 2;
    }
}
