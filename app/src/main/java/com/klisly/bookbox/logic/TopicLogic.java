package com.klisly.bookbox.logic;

import android.content.res.Resources;

import com.google.gson.reflect.TypeToken;
import com.klisly.bookbox.BookBoxApplication;
import com.klisly.bookbox.Constants;
import com.klisly.bookbox.R;
import com.klisly.bookbox.model.Topic;
import com.klisly.bookbox.model.User2Topic;
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

public class TopicLogic extends BaseLogic {

    /**
     * TopicLogic instance.
     */
    private static TopicLogic instance;

    private List<Topic> defaultTopics = new ArrayList<>();
    private List<Topic> focusedTopics = new ArrayList<>();

    private Map<String, User2Topic> subscribes = new HashMap<>();
    private static final String PRE_FOCUS_TOPICS = "PRE_FOCUS_TOPICS";
    public static int DATA_FOCUSED = 1;
    public static int DATA_DEFAULT = 2;

    /**
     * 获取ConversationLogic单例对象
     *
     * @return single instance of ConversationLogic
     */
    public static TopicLogic getInstance() {
        if (instance == null) {
            synchronized (TopicLogic.class) {
                if (instance == null) {
                    instance = new TopicLogic();
                }
            }
        }
        return instance;
    }

    public TopicLogic() {
        initFocusTopics();
        initDefaultTopics();
        initOpenData();
    }

    /**
     * 初始化需要提供出去的数据
     */
    private void initOpenData() {
        reorderDefaultTopics();
        focusedTopics.clear();
        if (subscribes.size() > 0) {
            for (int i = 0; i < defaultTopics.size(); i++) {
                if (defaultTopics.get(i).isFocused()
                        || Topic.getNamePriority(defaultTopics.get(i)) > 0) {
                    focusedTopics.add(defaultTopics.get(i));
                }
            }
        } else {
            for (int i = 0; i < defaultTopics.size() && i < Constants.DEFAULT_TOPIC_SIZE; i++) {
                focusedTopics.add(defaultTopics.get(i));
            }
        }
    }

    public void updateOpenData(){
        focusedTopics.clear();
        if (subscribes.size() > 0) {
            for (int i = 0; i < defaultTopics.size() || i < Constants.DEFAULT_TOPIC_SIZE; i++) {
                if (defaultTopics.get(i).isFocused()
                        || Topic.getNamePriority(defaultTopics.get(i)) > 0) {
                    focusedTopics.add(defaultTopics.get(i));
                }
            }
        } else {
            for (int i = 0; i < defaultTopics.size() && i < Constants.DEFAULT_TOPIC_SIZE; i++) {
                focusedTopics.add(defaultTopics.get(i));
            }
        }
        notifyDataChange();
    }

    private void initFocusTopics() {
        String data = preferenceUtils.getValue(PRE_FOCUS_TOPICS, "");
        if (StringUtils.isNotEmpty(data)) {
            subscribes = gson.fromJson(data, new TypeToken<Map<String, User2Topic>>() { }.getType());
        }
    }

    private void initDefaultTopics() {
        // 1.Sysconfig default topics
        List<Topic> tmp = SystemLogic.getInstance().getDefaultTopics();
        if (tmp.size() > 0) {
            this.defaultTopics = tmp;
        } else {
            //  2.raw default topics
            Resources resources = BookBoxApplication.getInstance().getResources();
            InputStream in = resources.openRawResource(R.raw.topics);
            String data = FileUtils.readInStream(in);
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (StringUtils.isNotEmpty(data)) {
                List<Topic> defaults = gson.fromJson(data, new TypeToken<List<Topic>>() {
                }.getType());
                Timber.i("get topics from raw.");
                this.defaultTopics = defaults;
            }
        }
        updateDefaultTopicStatus();
    }

    private void updateDefaultTopicStatus() {
        for (int i = 0; i < defaultTopics.size(); i++) {
            User2Topic user2Topic = subscribes.get(defaultTopics.get(i).getId());
            if (user2Topic != null) {
                defaultTopics.get(i).setFocused(!user2Topic.getIsBlock());
                defaultTopics.get(i).setSeq(user2Topic.getSeq());
            }
        }
    }

    public void reorderDefaultTopics() {
        Collections.sort(defaultTopics, new Comparator<Topic>() {
            @Override
            public int compare(Topic t0, Topic t1) {
                return t0.compareTo(t1);
            }
        });
    }

    public void updateDefaultTopics(List<Topic> defaults) {
        for(Topic topic : defaults){
            if(!updateDefaultTopic(topic)){
                defaultTopics.add(topic);
            }
        }
        updateDefaultTopicStatus();
        reorderDefaultTopics();
        updateOpenData();
    }

    private boolean updateDefaultTopic(Topic topic) {
        boolean success = false;
        for(int i = 0; i < defaultTopics.size(); i++){
            if(defaultTopics.get(i).getId().equals(topic.getId())){
                success = true;
                defaultTopics.get(i).updateData(topic);
            }
        }
        return success;
    }

    /**
     * 根据频道类型获取所有的频道
     *
     * @param type
     * @return
     */
    public List<Topic> getChannelsByType(int type) {
        List<Topic> topics = new ArrayList<>();
        getFakeChannel(topics, type);
        return topics;
    }

    private void getFakeChannel(List<Topic> topics, int type) {
        if (type == Constants.HOME) {
//            Topic topic = new Topic();
//            topic.setId(0 + "");
//            topic.setName("推荐");
//            topic.setImage("/topic/recommend");
//            topics.add(topic);
//            topic = new Topic();
//            topic.setId(1 + "");
//            topic.setName("热门");
//            topic.setImage("/topic/hot");
//            topics.add(topic);
        } else if (type == Constants.SITE) {
            Topic topic = new Topic();
            topic.setId(0 + "");
            topic.setName("烟雨");
            topic.setImage("/topic/recommend");
            topics.add(topic);
            topic = new Topic();
            topic.setId(1 + "");
            topic.setName("新解");
            topic.setImage("/topic/hot");
            topics.add(topic);
            topics.add(topic);
        } else if (type == Constants.MAGEZINE) {

        } else if (type == Constants.TOPIC) {
            Topic topic = new Topic();
            topic.setId(0 + "");
            topic.setName("汉语");
            topic.setImage("/topic/recommend");
            topics.add(topic);
            topic = new Topic();
            topic.setId(1 + "");
            topic.setName("新词");
            topic.setImage("/topic/hot");
            topics.add(topic);
            topics.add(topic);
        }
    }

    public void updateSubscribes(List<User2Topic> entities) {
        if (entities == null) {
            return;
        }
        subscribes.clear();
        for (User2Topic entity : entities) {
            subscribes.put(entity.getTopicId(), entity);
        }
        String data = gson.toJson(subscribes);
        preferenceUtils.setValue(PRE_FOCUS_TOPICS, data);
        updateDefaultTopicStatus();
        reorderDefaultTopics();
        updateOpenData();

    }

    public boolean isFocused(String id) {
        User2Topic tmp = subscribes.get(id);
        return tmp != null && !tmp.getIsBlock();
    }

    public List<Topic> getOpenChooseTopics() {
        return defaultTopics;
    }

    public List<Topic> getOpenFocuses() {
        return focusedTopics;
    }

    public void subscribe(User2Topic data) {
        subscribes.put(data.getTopicId(), data);
        updateDefaultTopicStatus(data);
        updateOpenData();
        persistentSubscribe();
    }

    private void persistentSubscribe(){

    }

    public void unSubscribe(User2Topic data) {
        subscribes.remove(data.getTopicId());
        updateDefaultTopicStatus(data);
        updateOpenData();
    }

    private void updateDefaultTopicStatus(User2Topic data) {
        for(int i = 0; i < defaultTopics.size(); i++){
            if(defaultTopics.get(i).getId().equals(data.getTopicId())){
                defaultTopics.get(i).setFocused(!data.getIsBlock());
                defaultTopics.get(i).setSeq(data.getSeq());
            }
        }
    }

    public void updateFocusedOrder() {
        for(int i = 0; i < defaultTopics.size(); i++){
            if(subscribes.containsKey(defaultTopics.get(i).getId())){
                subscribes.get(defaultTopics.get(i).getId()).setSeq(defaultTopics.size() - i);
            }
        }
        updateOpenData();
    }

    public Map<String, User2Topic> getSubscribes() {
        return subscribes;
    }
}
