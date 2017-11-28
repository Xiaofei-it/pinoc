/**
 *
 * Copyright 2017 iQIYI.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.iqiyi.trojantest;

/**
 * Created by Xiaofei on 2017/11/1.
 */

public class Bug {
    private static String convertToHex(Object[] data) {

        return null;
    }

    private static String[] convertToHex2(Object[] data) {
//        Object result = Trojan.onEnterMethod("MainActivity", "g", "()V", null, new Object[]{data});
//        if (result != Library.NO_RETURN_VALUE) {
//            if (result == null) {
//                return null;
//            }
//            if (result instanceof String[]) {
//                return (String[]) result;
//            }
//        }
        return null;
    }
    private static byte[] convertToHex4(Object[] data) {
//        Object result = Trojan.onEnterMethod("MainActivity", "g", "()V", null, new Object[]{data});
//        if (result != Library.NO_RETURN_VALUE) {
//            if (result == null) {
//                return null;
//            }
//            if (result instanceof byte[]) {
//                return (byte[]) result;
//            }
//        }
        return null;
    }
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
