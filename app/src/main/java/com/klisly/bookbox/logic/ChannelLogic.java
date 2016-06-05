package com.klisly.bookbox.logic;

import java.util.ArrayList;
import java.util.List;

import com.klisly.bookbox.Constants;
import com.klisly.bookbox.model.Channel;

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
    public List<Channel> getChannelsByType(int type){
        List<Channel> channels = new ArrayList<>();
        getFakeChannel(channels, type);
        return channels;
    }

    private void getFakeChannel(List<Channel> channels, int type) {
        if( type == Constants.HOME){
            Channel channel = new Channel();
            channel.setId(0);
            channel.setTitle("推荐");
            channel.setOrder(0);
            channel.setUrl("/channel/recommend");
            channels.add(channel);
            channel = new Channel();
            channel.setId(1);
            channel.setTitle("热门");
            channel.setOrder(1);
            channel.setUrl("/channel/hot");
            channels.add(channel);
        } else if( type == Constants.SITE){
            Channel channel = new Channel();
            channel.setId(2);
            channel.setTitle("烟雨");
            channel.setOrder(0);
            channel.setUrl("/channel/site/yanyu");
            channels.add(channel);
            channel = new Channel();
            channel.setId(1);
            channel.setTitle("求魔");
            channel.setOrder(1);
            channel.setUrl("/channel/hot");
            channels.add(channel);
        } else if( type == Constants.MAGEZINE){

        } else if( type == Constants.HOME){

        }
    }
}
