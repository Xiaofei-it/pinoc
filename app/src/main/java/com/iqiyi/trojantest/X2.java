package com.iqiyi.trojantest;

import com.iqiyi.trojan.Trojan;

import xiaofei.library.zlang.Library;

/**
 * Created by Eric on 2017/10/24.
 */

public class X2 {
    public int g(long a) {
        Object r = Trojan.onEnterMethod("com/iqiyi/trojantest/X", "g", "(II)I", this, new Object[]{Long.valueOf(a)});
        if(r != Library.NO_RETURN_VALUE) {
            if (r instanceof Integer) {
                return (int) r;
            }
        }
        return (int) a * 2;
    }

    public int g2(long a) {
        return (int) a * 2;
    }
}
