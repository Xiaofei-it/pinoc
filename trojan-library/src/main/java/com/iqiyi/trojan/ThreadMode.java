package com.iqiyi.trojan;

/**
 * Created by Maverick on 2017/12/5.
 */

public class ThreadMode {
    public static final int CURRENT = 0;
    public static final int MAIN = 1;
    public static final int BACKGROUND = 2;

    private int mMode = CURRENT;

    public ThreadMode(int mode) {
        if (CURRENT <= mode && mode <= BACKGROUND) {
            mMode = mode;
        }
    }

    public int getMode() {
        return mMode;
    }
}
