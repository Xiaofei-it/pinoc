package com.iqiyi.pinoc;

import java.util.concurrent.Callable;

/**
 * Created by Maverick on 2017/12/5.
 */

abstract class Scheduler {
    abstract Object call(Callable callable);
}
