package com.iqiyi.pinocdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DemoActivity extends AppCompatActivity {

    private void init() {
        Intent intent = getIntent();
        TextView textView = (TextView) findViewById(com.iqiyi.pinocdemo.R.id.tv);
        textView.setText(intent.getStringExtra("tmp").toUpperCase());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.iqiyi.pinocdemo.R.layout.activity_demo);
        init();
        findViewById(com.iqiyi.pinocdemo.R.id.bn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
