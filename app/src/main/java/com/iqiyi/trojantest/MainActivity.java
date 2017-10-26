package com.iqiyi.trojantest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.iqiyi.trojan.Trojan;

import xiaofei.library.zlang.Library;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //g1("a", "j", true, 8, 8, 8.0, 8.0f, 8L, 'c', this, false);
        //g2("a", "j", true, 8, 8, 8.0, 8.0f, 8L, 'c', this, false);
    }

    private void g1(String a, String b, boolean c, Integer d, int e, double f, /*MainActivity j,*/ Boolean k) {
        Object result = Trojan.onEnterMethod("MainActivity", "g", "()V", this, new Object[]{a, b, c, d, e, f, k});
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
