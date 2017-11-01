package com.iqiyi.trojantest;

import com.iqiyi.trojan.Trojan;

import java.util.Map;

import xiaofei.library.zlang.Library;

/**
 * Created by Eric on 2017/11/1.
 */

public class Bug {
    private static String convertToHex(Object[] data) {

        return null;
    }

//    private static String[] convertToHex2(Object[] data) {
//
//        return null;
//    }

    private static String convertToHex3(Object... data) {
        for (Object o : data) {
            {
                String s = "" + o;
                if (s.endsWith("[")) {
                    return "l";
                } else {
                    s += "[";
                    String ss = s + "p";
                    if (ss.endsWith("0")) {
                        return ss;
                    }
                }
            }
        }
        return null;
    }
    private static String convertToHex(boolean[][] data) {
        String result = "";
        for (int i = 0; i < 100; ++i) {
            String tmp = "" + i;
            result += tmp;
        }
        return result;
    }
}
