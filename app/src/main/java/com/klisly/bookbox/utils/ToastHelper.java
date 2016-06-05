package com.klisly.bookbox.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastHelper {

    private static Context context;

    public static void init(Context context){
        ToastHelper.context = context;
    }

    public static void showShortTip(int tip) {
        Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
    }

    public static void showShortTip(String tip) {
        Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
    }

    public static void showLongTip(int tip) {
        Toast.makeText(context, tip, Toast.LENGTH_LONG).show();
    }

    public static void showLoneTip(String tip) {
        Toast.makeText(context, tip, Toast.LENGTH_LONG).show();
    }
}
