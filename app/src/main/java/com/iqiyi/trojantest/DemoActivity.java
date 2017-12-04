package com.iqiyi.trojantest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        findViewById(R.id.bn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
