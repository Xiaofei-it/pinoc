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

package com.iqiyi.pinoc;

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
