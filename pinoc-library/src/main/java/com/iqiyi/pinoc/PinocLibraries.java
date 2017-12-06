package com.iqiyi.pinoc;

import android.app.Activity;
import android.os.Build;

import xiaofei.library.zlang.JavaFunction;
import xiaofei.library.zlang.JavaLibrary;

/**
 * Created by Xiaofei on 2017/12/4.
 */

class PinocLibraries {
    static final JavaLibrary JAVA_LIBRARY
            = new JavaLibrary.Builder()
                    .addFunction(new JavaFunction() {

                        private boolean isContext(Class<?> clazz) {
                            if (Activity.class.isAssignableFrom(clazz)) {
                                return true;
                            } else if (Build.VERSION.SDK_INT >= 11 && android.app.Fragment.class.isAssignableFrom(clazz)) {
                                return true;
                            } else if (android.support.v4.app.Fragment.class.isAssignableFrom(clazz)) {
                                return true;
                            }
                            return false;
                        }
                        @Override
                        public boolean isVarArgs() {
                            return false;
                        }

                        @Override
                        public int getParameterNumber() {
                            return 1;
                        }

                        @Override
                        public String getFunctionName() {
                            return "get_outer_context";
                        }

                        @Override
                        public Object call(Object[] input) {
                            Class<?> clazz = input[0].getClass();
                            while (clazz != null) {
                                if (isContext(clazz)) {
                                    return clazz;
                                }
                                clazz = clazz.getEnclosingClass();
                            }
                            return null;
                        }
                    })
                    .build();
}
