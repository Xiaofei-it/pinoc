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

import xiaofei.library.zlang.JavaLibrary;
import xiaofei.library.zlang.Library;

/**
 * Created by Xiaofei on 2017/10/17.
 */

public class Pinoc {

    private static final PinocCore PINOC_CORE = PinocCore.getInstance();

    public static Object onEnterMethod(String className, String methodName, String methodSignature, Object thiz, Object[] parameters) {
        return PINOC_CORE.onEnterMethod(className, methodName, methodSignature, thiz, parameters);
    }

    public static void config(String configuration) {
        PINOC_CORE.config(configuration);
    }

    public static void addDependency(Library library) {
        PINOC_CORE.addDependency(library);
    }

    public static void addJavaDependency(JavaLibrary library) {
        PINOC_CORE.addJavaDependency(library);
    }
}
