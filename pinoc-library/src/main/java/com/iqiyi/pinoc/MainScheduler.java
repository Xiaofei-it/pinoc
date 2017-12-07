package com.iqiyi.pinoc;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.Callable;

import xiaofei.library.zlang.Library;

/**
 * Created by Maverick on 2017/12/5.
 */

class MainScheduler extends Scheduler {

    static final String TAG = "MainScheduler";

    static Handler sHandler = new Handler(Looper.getMainLooper());

    @Override
    Object call(final Callable callable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            try {
                return callable.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            sHandler.post(new Runnable() {
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
        }
        return Library.NO_RETURN_VALUE;
    }
}
