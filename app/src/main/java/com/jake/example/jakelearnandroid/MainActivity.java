package com.jake.example.jakelearnandroid;

import androidx.appcompat.app.AppCompatActivity;

import com.jake.example.jakelearnandroid.view.MyScrollView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyScrollView myScrollView = (MyScrollView) findViewById(R.id.myscroll);
        LinearLayout container = findViewById(R.id.container);
        View v1 = getLayoutInflater().inflate(R.layout.activity_test_first , null ,false);
        View v2 = getLayoutInflater().inflate(R.layout.activity_test_sec , null ,false);
        container.addView(v1);
        container.addView(v2);
    }
}

