package com.iqiyi.trojantest;

import java.io.IOException;

/**
 * Created by Eric on 2017/11/1.
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
