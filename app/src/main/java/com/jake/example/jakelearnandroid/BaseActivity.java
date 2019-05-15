package com.jake.example.jakelearnandroid;

import android.app.AppComponentFactory;
import android.os.Bundle;

import com.jake.example.jakelearnandroid.ioc.InjectUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.inject(this);
    }
}
