package com.iqiyi.trojantest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DemoActivity extends AppCompatActivity {

    private void init() {
        Intent intent = getIntent();
        TextView textView = (TextView) findViewById(R.id.tv);
        textView.setText(intent.getStringExtra("tmp").toUpperCase());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        init();
    }
}
