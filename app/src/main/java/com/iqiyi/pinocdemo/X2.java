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

package com.iqiyi.pinocdemo;

import com.iqiyi.pinoc.Pinoc;
import xiaofei.library.zlang.Library;

/**
 * Created by Xiaofei on 2017/10/24.
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
        Object r = Pinoc.onEnterMethod("com/iqiyi/trojantest/X", "g", "(II)I", this, new Object[]{Long.valueOf(a), Double.valueOf(b), Integer.valueOf(c), Boolean.valueOf(d)});
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
        Object r = Pinoc.onEnterMethod("com/iqiyi/trojantest/X", "g", "(II)I", null, new Object[]{Long.valueOf(a), Double.valueOf(b), Integer.valueOf(c), Boolean.valueOf(d)});
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
