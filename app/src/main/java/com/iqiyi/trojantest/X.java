package com.iqiyi.trojantest;

import com.iqiyi.trojan.Trojan;

import xiaofei.library.zlang.Library;

/**
 * Created by Eric on 2017/10/24.
 */

public class X {
    public int g(int a, int b) {
        Object r = Trojan.onEnterMethod("com/iqiyi/trojantest/X", "g", "(II)I", this, new Object[]{Integer.valueOf(a), Integer.valueOf(b)});
        if(r != Library.NO_RETURN_VALUE) {
            if (r instanceof Integer) {
                return (int) r;
            }
        }
        int c = a + b;
        return c * 2;
    }

    public int g2(int a, int b) {
        int c = a + b;
        return c * 2;
    }
}
