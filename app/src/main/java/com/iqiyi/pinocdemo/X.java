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

import com.iqiyi.pinoc.Pinoc;
import xiaofei.library.zlang.Library;

/**
 * Created by Xiaofei on 2017/10/24.
 */

public class X {
    public int g(int a, int b) {
        Object r = Pinoc.onEnterMethod("com/iqiyi/trojantest/X", "g", "(II)I", this, new Object[]{Integer.valueOf(a), Integer.valueOf(b)});
        if(r != Library.NO_RETURN_VALUE) {
            if (r instanceof Integer) {
                return (int) r;
            }
        }
        int c = a + b;
        return c * 2;
    }

    public int g2(int a, int b) {
        int c = a + b;
        return c * 2;
    }
}
