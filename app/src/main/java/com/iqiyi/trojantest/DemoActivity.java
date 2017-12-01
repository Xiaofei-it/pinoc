package com.iqiyi.trojantest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DemoActivity extends AppCompatActivity {

    private int num = 0;
    private void f(int num) {
        this.num = num + 1;
    }

    private void init() {
        Intent intent = getIntent();
        TextView textView = (TextView) findViewById(R.id.tv);
        textView.setText(intent.getStringExtra("tmp").toUpperCase());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            f(savedInstanceState.getInt("num"));
        }
        setContentView(R.layout.activity_demo);
        init();
    }

    private void initTmp() {
        Intent intent = getIntent();
        String tmp = intent.getStringExtra("tmp");
        if (tmp != null) {
            TextView textView = (TextView) findViewById(R.id.tv);
            textView.setText(tmp.toUpperCase());
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("num", num);
    }
}
