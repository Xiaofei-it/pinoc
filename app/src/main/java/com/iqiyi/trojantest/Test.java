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
    private int g2(String a, MainActivity j, Boolean k) {
        Object result = Trojan.onEnterMethod("MainActivity", "g", "()V", this, new Object[]{a,j ,k});
        if (result != Library.NO_RETURN_VALUE) {
            if (result instanceof Integer) {
                return (int) result;
            }
        }
        @U int kk = -9;
        if (new Random().nextInt() > kk) {
            return kk;
        }
        ++kk;
        return kk;
    }

    private Integer g3() {
        Object result = Trojan.onEnterMethod("MainActivity", "g", "()V", this, new Object[]{});
        if (result != Library.NO_RETURN_VALUE) {
            if (result instanceof Integer) {
                return (Integer) result;
            }
        }
        result = null;
        return null;
    }

    private Integer returnValueInteger1(int a) {
        Object result = Trojan.onEnterMethod("MainActivity", "g", "()V", this, new Object[]{a});
        if (result != Library.NO_RETURN_VALUE) {
            if (result == null) {
                return null;
            }
            if (result instanceof Integer) {
                return (Integer) result;
            }
        }
        int s = 0;
        int k = 1;
        return s + k + 1;
    }
    private Integer returnValueInteger2(int a) {
        int s = 0;
        int k = 1;
        return s + k + 1;
    }

    private int returnValueInt1(Integer a) {
        Object result = Trojan.onEnterMethod("MainActivity", "g", "()V", this, new Object[]{a});
        if (result != Library.NO_RETURN_VALUE) {
            if (result instanceof Integer) {
                return (Integer) result;
            }
        }
        int s = 0;
        int k = 1;
        return s + k + 1;
    }

    private int returnValueInt2(Integer a) {
        int s = 0;
        int k = 1;
        return s + k + 1;
    }

    private void void1(Boolean a, int b) {
        a = (b == b);
    }

    private Void void2(Boolean a, int b) {
        return null;
    }

    private Void void3(Boolean a, int b) {
        Object result = Trojan.onEnterMethod("MainActivity", "g", "()V", this, new Object[]{a});
        if (result != Library.NO_RETURN_VALUE) {
            return null;
        }
        a = (b == b);
        return null;
    }

    private Boolean returnBoolean1(Boolean a, boolean b) {
        int s = 0;
        int k = 1;
        return true;
    }

    private boolean returnBoolean2(Boolean a, boolean b) {
        int s = 0;
        int k = 1;
        return true;
    }

    private byte returnByte1(Byte a, byte b) {
        int s = 0;
        int k = 1;
        return (byte) (a + b);
    }

    private Byte returnByte2(Byte a, byte b) {
        int s = 0;
        int k = 1;
        return (byte) (a + b);
    }

    private Short returnShort1(Short a, short b) {
        int s = 0;
        int k = 1;
        return (short)(a + b);
    }

    private short returnShort2(Short a, short b) {
        int s = 0;
        int k = 1;
        return (short) (a + b);
    }

    private int returnInt1(Integer a, int b) {
        int s = 0;
        int k = 1;
        return a + b;
    }

    private Integer returnInt2(Integer a, int b) {
        int s = 0;
        int k = 1;
        return a + b;
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
    private long returnLong1(long a) {
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
