package com.klisly.bookbox.logic;

import com.google.gson.Gson;
import com.klisly.bookbox.BookBoxApplication;
import com.klisly.bookbox.listener.OnDataChangeListener;
import com.klisly.common.SharedPreferenceUtils;

import java.util.HashMap;
import java.util.Map;

public class BaseLogic {
    public static String TAG;
    protected SharedPreferenceUtils preferenceUtils = BookBoxApplication.getInstance().getPreferenceUtils();
    protected Gson gson = new Gson();
    protected Map<Object, OnDataChangeListener> listeners = new HashMap<>();

    public BaseLogic() {
        TAG = this.getClass().getSimpleName();
    }

    public void registerListener(Object object, OnDataChangeListener listener) {
        listeners.put(object, listener);
    }

    public void unRegisterListener(Object object) {
        listeners.remove(object);
    }

    /**
     * 通知外部绑定器,数据已经发生改变
     *
     */
    protected void notifyDataChange() {
        if (listeners.size() <= 0) {
            return;
        }
        for (OnDataChangeListener listener : listeners.values()) {
            listener.onDataChange();
        }
    }


}
