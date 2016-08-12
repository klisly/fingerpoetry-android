package com.klisly.bookbox.logic;

import android.content.res.Resources;

import com.google.gson.reflect.TypeToken;
import com.klisly.bookbox.BookBoxApplication;
import com.klisly.bookbox.Constants;
import com.klisly.bookbox.R;
import com.klisly.bookbox.listener.OnDataChangeListener;
import com.klisly.bookbox.model.Site;
import com.klisly.bookbox.model.User2Site;
import com.klisly.common.FileUtils;
import com.klisly.common.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import timber.log.Timber;

public class SiteLogic extends BaseLogic {

    /**
     * TopicLogic instance.
     */
    private static SiteLogic instance;

    private List<Site> defaults = new ArrayList<>();
    private List<Site> focuseds = new ArrayList<>();

    private Map<String, User2Site> subscribes = new HashMap<>();
    private static final String PRE_FOCUS = "PRE_FOCUS_SITE";
    private Map<Object, OnDataChangeListener> listeners = new HashMap<>();
    public static int DATA_FOCUSED = 1;
    public static int DATA_DEFAULT = 2;

    /**
     * 获取ConversationLogic单例对象
     *
     * @return single instance of ConversationLogic
     */
    public static SiteLogic getInstance() {
        if (instance == null) {
            synchronized (SiteLogic.class) {
                if (instance == null) {
                    instance = new SiteLogic();
                }
            }
        }
        return instance;
    }

    public SiteLogic() {
        initFocuses();
        initDefaults();
        initOpenData();
    }

    /**
     * 初始化需要提供出去的数据
     */
    private void initOpenData() {
        reorderDefaultTopics();
        focuseds.clear();
        if (subscribes.size() > 0) {
            for (int i = 0; i < defaults.size(); i++) {
                if (defaults.get(i).isFocused()
                        || Site.getNamePriority(defaults.get(i)) > 0) {
                    focuseds.add(defaults.get(i));
                }
            }
        } else {
            for (int i = 0; i < defaults.size() && i < Constants.DEFAULT_TOPIC_SIZE; i++) {
                focuseds.add(defaults.get(i));
            }
        }
    }

    public void updateOpenData(){
        focuseds.clear();
        if (subscribes.size() > 0) {
            for (int i = 0; i < defaults.size() || i < Constants.DEFAULT_TOPIC_SIZE; i++) {
                if (isFocused(defaults.get(i).getId())
                        || Site.getNamePriority(defaults.get(i)) > 0) {
                    focuseds.add(defaults.get(i));
                }
            }
        } else {
            for (int i = 0; i < defaults.size() && i < Constants.DEFAULT_TOPIC_SIZE; i++) {
                focuseds.add(defaults.get(i));
            }
        }

        notifyDataChange();
    }

    private void initFocuses() {
        String data = preferenceUtils.getValue(PRE_FOCUS, "");
        if (StringUtils.isNotEmpty(data)) {
            subscribes = gson.fromJson(data, new TypeToken<Map<String, User2Site>>() { }.getType());
        }
    }

    private void initDefaults() {
        // 1.Sysconfig default topics
        List<Site> tmp = SystemLogic.getInstance().getDefaultSites();
        if (tmp.size() > 0) {
            this.defaults = tmp;
        } else {
            //  2.raw default topics
            Resources resources = BookBoxApplication.getInstance().getResources();
            InputStream in = resources.openRawResource(R.raw.sites);
            String data = FileUtils.readInStream(in);
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (StringUtils.isNotEmpty(data)) {
                List<Site> defaults = gson.fromJson(data, new TypeToken<List<Site>>() { }.getType());
                Timber.i("get topics from raw.");
                this.defaults = defaults;
            }
        }
        updateDefaultTopicStatus();
    }

    private void updateDefaultTopicStatus() {
        for (int i = 0; i < defaults.size(); i++) {
            User2Site entity = subscribes.get(defaults.get(i).getId());
            if (entity != null) {
                defaults.get(i).setFocused(!entity.getIsBlock());
                defaults.get(i).setSeq(entity.getSeq());
            }
        }
    }

    public void reorderDefaultTopics() {
        Collections.sort(defaults, new Comparator<Site>() {
            @Override
            public int compare(Site t0, Site t1) {
                return t0.compareTo(t1);
            }
        });
    }

    public void updateDefaultTopics(List<Site> defaults) {
        for(Site site : defaults){
            if(!updateDefaultTopic(site)){
                this.defaults.add(site);
            }
        }
        updateDefaultTopicStatus();
        reorderDefaultTopics();
        updateOpenData();
    }

    private boolean updateDefaultTopic(Site site) {
        boolean success = false;
        for(int i = 0; i < defaults.size(); i++){
            if(defaults.get(i).getId().equals(site.getId())){
                success = true;
                defaults.get(i).updateData(site);
            }
        }
        return success;
    }


    public void updateSubscribes(List<User2Site> entities) {
        if (entities == null) {
            return;
        }
        subscribes.clear();
        for (User2Site entity : entities) {
            subscribes.put(entity.getSiteId(), entity);
        }
        persistenceSubscribe();
        updateDefaultTopicStatus();
        reorderDefaultTopics();
        updateOpenData();

    }

    private void persistenceSubscribe() {
        String data = gson.toJson(subscribes);
        preferenceUtils.setValue(PRE_FOCUS, data);
    }

    public boolean isFocused(String id) {
        User2Site tmp = subscribes.get(id);
        return tmp != null && !tmp.getIsBlock();
    }

    public List<Site> getOpenChooses() {
        return defaults;
    }

    public List<Site> getOpenFocuses() {
        return focuseds;
    }

    public void registerListener(Object object, OnDataChangeListener listener) {
        listeners.put(object, listener);
    }

    public void unRegisterListener(Object object) {
        listeners.remove(object);
    }

    /**
     * 通知外部绑定器,数据已经发生改变
     */
    private void notifyDataChange() {
        if (listeners.size() <= 0) {
            return;
        }
        for (OnDataChangeListener listener : listeners.values()) {
            listener.onDataChange();
        }
    }

    public void subscribe(User2Site data) {
        subscribes.put(data.getSiteId(), data);
        updateDefaultTopicStatus(data);
        updateOpenData();
        persistenceSubscribe();
    }

    public void unSubscribe(User2Site data) {
        subscribes.remove(data.getSiteId());
        updateDefaultTopicStatus(data);
        updateOpenData();
        persistenceSubscribe();
    }

    private void updateDefaultTopicStatus(User2Site data) {
        for(int i = 0; i < defaults.size(); i++){
            if(defaults.get(i).getId().equals(data.getSiteId())){
                defaults.get(i).setFocused(!data.getIsBlock());
                defaults.get(i).setSeq(data.getSeq());
            }
        }
    }

    public void updateFocusedOrder() {
        for(int i = 0; i < defaults.size(); i++){
            if(subscribes.containsKey(defaults.get(i).getId())){
                subscribes.get(defaults.get(i).getId()).setSeq(defaults.size() - i);
            }
        }
        updateOpenData();
    }

    public Map<String, User2Site> getSubscribes() {
        return subscribes;
    }
}
