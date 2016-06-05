package com.klisly.bookbox.logic;

import com.google.gson.Gson;
import com.klisly.bookbox.BookBoxApplication;
import com.klisly.common.SharedPreferenceUtils;

public class BaseLogic {
    public static String TAG;
    protected SharedPreferenceUtils preferenceUtils = BookBoxApplication.getInstance().getPreferenceUtils();
    protected Gson gson = new Gson();

    public BaseLogic() {
        TAG = this.getClass().getSimpleName();

    }
}
