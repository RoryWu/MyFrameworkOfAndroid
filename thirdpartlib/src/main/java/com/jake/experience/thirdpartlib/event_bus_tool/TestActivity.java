package com.jake.experience.thirdpartlib.event_bus_tool;

import android.os.Bundle;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author
 * @version 1.0
 * @date 19-1-23
 */
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBusUtil.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBusUtil.unregister(this);
    }

 
    @Subscribe(threadMode = ThreadMode.MAIN)
    private void onEventBusMsgReceive(EventBusMsg<String> msg) {
        if (msg.getCode() == 200) {
            msg.getData();
        }
    }
}
