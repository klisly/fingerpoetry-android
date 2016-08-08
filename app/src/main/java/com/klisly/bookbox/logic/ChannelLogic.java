package com.klisly.bookbox.logic;

import com.klisly.bookbox.Constants;
import com.klisly.bookbox.model.Topic;

import java.util.ArrayList;
import java.util.List;

public class ChannelLogic extends  BaseLogic{

    /**
     * ChannelLogic instance.
     */
    private static ChannelLogic instance;

    /**
     * 获取ConversationLogic单例对象
     *
     * @return single instance of ConversationLogic
     */
    public static ChannelLogic getInstance() {
        if (instance == null) {
            synchronized (ChannelLogic.class) {
                if (instance == null) {
                    instance = new ChannelLogic();
                }
            }
        }
        return instance;
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
//            Topic topic = new Topic();
//            topic.setId(0+"");
//            topic.setName("推荐");
//            topic.ste(0);
//            topic.setUrl("/topic/recommend");
//            topics.add(topic);
//            topic = new Topic();
//            topic.setId(1);
//            topic.setTitle("热门");
//            topic.setOrder(1);
//            topic.setUrl("/topic/hot");
//            topics.add(topic);
        } else if( type == Constants.SITE){
//            Topic topic = new Topic();
//            topic.setId(2);
//            topic.setTitle("烟雨");
//            topic.setOrder(0);
//            topic.setUrl("/topic/site/yanyu");
//            topics.add(topic);
//            topic = new Topic();
//            topic.setId(1);
//            topic.setTitle("新解");
//            topic.setOrder(1);
//            topic.setUrl("/topic/hot");
//            topics.add(topic);
        } else if( type == Constants.MAGEZINE){

        } else if( type == Constants.TOPIC){
//            Topic topic = new Topic();
//            topic.setId(2);
//            topic.setTitle("汉语");
//            topic.setOrder(0);
//            topic.setUrl("/topic/site/yanyu");
//            topics.add(topic);
//            topic = new Topic();
//            topic.setId(1);
//            topic.setTitle("新词");
//            topic.setOrder(1);
//            topic.setUrl("/topic/hot");
//            topics.add(topic);
        }
    }
}
