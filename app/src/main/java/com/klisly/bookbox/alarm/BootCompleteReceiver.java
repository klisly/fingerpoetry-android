package com.klisly.bookbox.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.klisly.bookbox.BookBoxApplication;

public class BootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String msg = intent.getStringExtra("msg");
        long intervalMillis = intent.getLongExtra("intervalMillis", 0);
        if (intervalMillis != 0) {
            AlarmManagerUtil.setAlarmTime(context, System.currentTimeMillis() + intervalMillis,
                    intent);
        }
        BookBoxApplication.getInstance().showMomentNotifi("朝花夕拾", msg);
    }


}
