package com.iqiyi.trojan;

import android.util.Log;

/**
 * Created by Xiaofei on 2017/10/17.
 */

class Logger {
    private static volatile boolean sDebug = true;
    void setDebug(boolean debug) {
        sDebug = debug;
    }

    static void e(String tag, String msg) {
        if (sDebug) {
            Log.e(tag, msg);
        }
    }

    static void e(String tag, String msg, Throwable cause) {
        if (sDebug) {
            Log.e(tag, msg, cause);
        }
    }

    public static void i(String tag, String msg) {
        if (sDebug) {
            Log.e(tag, msg);
        }
    }
}
