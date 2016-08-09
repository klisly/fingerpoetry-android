package com.klisly.bookbox.logic;

import android.content.res.Resources;

import com.google.gson.reflect.TypeToken;
import com.klisly.bookbox.BookBoxApplication;
import com.klisly.bookbox.Constants;
import com.klisly.bookbox.R;
import com.klisly.bookbox.model.Topic;
import com.klisly.common.FileUtils;
import com.klisly.common.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class TopicLogic extends BaseLogic{

    /**
     * TopicLogic instance.
     */
    private static TopicLogic instance;

    private List<Topic> defaultTopics = new ArrayList<>();
    private List<Topic> topics = new ArrayList<>();
    private static final String PRE_TOPICS = "PRE_TOPICS";

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
        initDefaultTopics();
        initTopics();
    }

    private void initTopics() {
        String data = preferenceUtils.getValue(PRE_TOPICS, "");
        if (StringUtils.isNotEmpty(data)) {
            topics = gson.fromJson(data, new TypeToken<List<Topic>>(){}.getType());
        }
    }

    public List<Topic> getTopics(){
        return topics;
    }

    public void setTopics(List<Topic> entities){
        if (entities == null) {
            return;
        }
        this.topics = entities;
        String data = gson.toJson(entities);
        preferenceUtils.setValue(PRE_TOPICS, data);
    }

    private void initDefaultTopics() {
        // 1.Sysconfig default topics? 2.raw default topics
        Resources resources = BookBoxApplication.getInstance().getResources();
        InputStream in = resources.openRawResource(R.raw.topics);
        String data = FileUtils.readInStream(in);
        if(in != null){
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(StringUtils.isNotEmpty(data)){
            List<Topic>  defaults =  BookBoxApplication.getInstance().getGson().fromJson(data,
                   new TypeToken<List<Topic>>(){}.getType());
            Timber.i("get topics from raw.");
            setDefaultTopics(defaults);
        }
    }

    public void setDefaultTopics(List<Topic> defaults) {
        this.defaultTopics = defaults;
    }

    /**
     * 根据频道类型获取所有的频道
     * @param type
     * @return
     */
    public List<Topic> getChannelsByType(int type){
        List<Topic> topics = new ArrayList<>();
        getFakeChannel(topics, type);
        return topics;
    }

    private void getFakeChannel(List<Topic> topics, int type) {
        if( type == Constants.HOME){
            Topic topic = new Topic();
            topic.setId(0+"");
            topic.setName("推荐");
            topic.setImage("/topic/recommend");
            topics.add(topic);
            topic = new Topic();
            topic.setId(1+"");
            topic.setName("热门");
            topic.setImage("/topic/hot");
            topics.add(topic);
        } else if( type == Constants.SITE){
            Topic topic = new Topic();
            topic.setId(0+"");
            topic.setName("烟雨");
            topic.setImage("/topic/recommend");
            topics.add(topic);
            topic = new Topic();
            topic.setId(1+"");
            topic.setName("新解");
            topic.setImage("/topic/hot");
            topics.add(topic);
            topics.add(topic);
        } else if( type == Constants.MAGEZINE){

        } else if( type == Constants.TOPIC){
            Topic topic = new Topic();
            topic.setId(0+"");
            topic.setName("汉语");
            topic.setImage("/topic/recommend");
            topics.add(topic);
            topic = new Topic();
            topic.setId(1+"");
            topic.setName("新词");
            topic.setImage("/topic/hot");
            topics.add(topic);
            topics.add(topic);
        }
    }

    public boolean isFocus(String id) {
        return false;
    }

    public List<Topic> getChooseTopics() {
        return defaultTopics;
    }
}
