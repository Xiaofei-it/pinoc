package com.iqiyi.trojan;

import android.support.annotation.NonNull;

/**
 * Created by Maverick on 2017/12/5.
 */

class Schedulers {
    @NonNull
    static final Scheduler MAIN;

    @NonNull
    static final Scheduler BACKGROUND;

    static {
        MAIN = new MainScheduler();
        BACKGROUND = new BackgroundScheduler();
    }
}
