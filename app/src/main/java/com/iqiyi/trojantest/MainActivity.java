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

package com.iqiyi.trojantest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.iqiyi.trojan.Trojan;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import xiaofei.library.zlang.JavaFunction;
import xiaofei.library.zlang.JavaLibrary;
import xiaofei.library.zlang.Library;

public class MainActivity extends AppCompatActivity {


    private int iii = 90;
    private void init() {
        Trojan.addJavaDependency(new JavaLibrary.Builder().addFunction(new JavaFunction() {
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
                return "test_java";
            }

            @Override
            public Object call(Object[] input) {
                return "test_java_result" + input;
            }
        }).build());
        Trojan.addDependency(new Library.Builder().addFunctions("function test_internal(a) {return a + 1;}").build());
        InputStream is = getResources().openRawResource(R.raw.config);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int num;
        try {
            while ((num = is.read(bytes, 0, 1024)) != -1) {
                baos.write(bytes, 0, num);
            }
            Trojan.config(baos.toString().replace("\r", ""));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        g1("a", "j", true, 8, 8, 8.0f, this, false);
        g2("a", "j", true, 8, 8, 8.0, 8.0f, 8L, 'c', this, false);
        new Test().g1("7u", iii);
        try {
            Bug2.Buffer[] buffers = new Bug2().intercept(null);
            Log.d("Main", buffers[0].s + " " + buffers[1].s);
        } catch (Throwable t) {

        }
        findViewById(R.id.demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DemoActivity.class);
                intent.putExtra("temp", "Hello");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void g1(String a, String b, boolean c, Integer d, int e, double f, MainActivity j, Boolean k) {
        Object result = Trojan.onEnterMethod("MainActivity", "g", "()V", this, new Object[]{a, b, c, d, e, f, j, k});
        if (result != Library.NO_RETURN_VALUE) {
            return;
        }
        System.gc();
    }
    private boolean g2(String a, String b, boolean c, Integer d, int e, double f, float g, long h ,char i, MainActivity j, Boolean k) {
        Object result = Trojan.onEnterMethod("MainActivity", "g", "()V", this, new Object[]{a, b, c, d, e, f, g, h, i, j ,k});
        if (result != Library.NO_RETURN_VALUE) {
            if (result instanceof Boolean) {
                return (boolean) result;
            }
        }
        @U int kk = -9;
        if (kk + d > 10) {
            System.gc();
        }
        return false;
    }

    private Boolean g3(String a, String b, boolean c, Integer d, int e, double f, float g, long h ,char i, MainActivity j, Boolean k) {
        Object result = Trojan.onEnterMethod("MainActivity", "g", "()V", this, new Object[]{a, b, c, d, e, f, g, h, i, j ,k});
        if (result != Library.NO_RETURN_VALUE) {
            if (result == null) {
                return null;
            }
            if (result instanceof Boolean) {
                return (Boolean) result;
            }
        }
        System.gc();
        return false;
    }


    private Test g4(String a, String b, boolean c, Integer d, int e, double f, float g, long h ,char i, MainActivity j, Boolean k) {
        Object result = Trojan.onEnterMethod("MainActivity", "g", "()V", this, new Object[]{a, b, c, d, e, f, g, h, i, j ,k});
        if (result != Library.NO_RETURN_VALUE) {
            if (result == null) {
                return null;
            }
            if (result instanceof Test) {
                return (Test) result;
            }
        }
        System.gc();
        return null;
    }
}
// "c:\Program Files\Android\Android Studio\jre\bin\javap.exe" -c -l -s -verbose -private MainActivity.class
