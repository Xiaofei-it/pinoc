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

package com.iqiyi.pinocdemo;

import java.io.IOException;

/**
 * Created by Xiaofei on 2017/11/1.
 */

public class Bug2 {
    class Buffer {
        public String s;
        private void readUtf8(String s) {
            this.s = s;
        }
    }

    private class Chain {

    }
    public Buffer[] intercept(Chain chain) throws IOException {
            try {
                Buffer buffer = new Buffer();
                buffer.readUtf8("abc");
                Buffer buffer1 = new Buffer();
                buffer1.readUtf8("cd");
                if (buffer == buffer1) {
                    return new Buffer[]{buffer, buffer};
                } else {
                    throw new RuntimeException("Fuck you");
                }
//                return new Buffer[]{buffer, buffer1};
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
            return new Buffer[]{new Buffer(), new Buffer()};
        // Caution: e will have the same offset as the buffer.
        // TODO 1. var name (check)  2. try to run (check)  3. module 4.proguard

    }


}
