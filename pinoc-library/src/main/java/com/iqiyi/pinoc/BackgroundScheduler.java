package com.iqiyi.pinoc;

import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import xiaofei.library.zlang.Library;

/**
 * Created by Maverick on 2017/12/5.
 */

class BackgroundScheduler extends Scheduler {

    static final String TAG = "BackgroundScheduler";

    static ExecutorService sExecuter = Executors.newCachedThreadPool();

    @Override
    Object call(final Callable callable) {
        sExecuter.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Object result = callable.call();
                    if (result != Library.NO_RETURN_VALUE) {
                        Log.e(TAG, "library no return value");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return Library.NO_RETURN_VALUE;
    }
}
