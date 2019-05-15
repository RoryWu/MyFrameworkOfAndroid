package com.jake.example.jakelearnandroid.ioc;

import android.os.Bundle;
import android.widget.TextView;

import com.jake.example.jakelearnandroid.BaseActivity;
import com.jake.example.jakelearnandroid.R;

import androidx.annotation.Nullable;

@ContentView(R.layout.activity_main)
public class InjectActivity extends BaseActivity {

//    @BindView(R.id.main)
    TextView mainText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        findViewById()
    }

//    @OnClick(R.id.main)
    public void clickEvent() {

    }
}
