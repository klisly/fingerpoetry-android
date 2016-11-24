package com.klisly.bookbox.logic;

import com.google.gson.reflect.TypeToken;
import com.klisly.bookbox.model.Novel;
import com.klisly.bookbox.model.User2Novel;
import com.klisly.common.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NovelLogic extends BaseLogic {

    /**
     * TopicLogic instance.
     */
    private static NovelLogic instance;

    private List<User2Novel> subscribes = new ArrayList<>();
    private Map<String, User2Novel> subscribeMap = new HashMap<>();

    private String PRE_NOVEL_FOCUS = "PRE_NOVEL_FOCUS";
    /**
     * 获取NovelLogic单例对象
     *
     * @return single instance of ConversationLogic
     */
    public static NovelLogic getInstance() {
        if (instance == null) {
            synchronized (NovelLogic.class) {
                if (instance == null) {
                    instance = new NovelLogic();
                }
            }
        }
        return instance;
    }

    public NovelLogic() {
        initFocuses();
    }

    private void initFocuses() {
        String data = preferenceUtils.getValue(PRE_NOVEL_FOCUS, "");
        if (StringUtils.isNotEmpty(data)) {
            subscribes = gson.fromJson(data, new TypeToken<List<User2Novel>>() { }.getType());
            for(User2Novel user2Novel : subscribes){
                subscribeMap.put(user2Novel.getNid(), user2Novel);
            }
        }
    }

    public void updateSubscribes(List<User2Novel> entities) {
        if (entities == null) {
            return;
        }
        subscribes.clear();
        subscribeMap.clear();
        for (User2Novel entity : entities) {
            subscribeMap.put(entity.getNid(), entity);
        }
        persistenceSubscribe();
        this.subscribes.addAll(entities);
        notifyDataChange();
    }

    private void persistenceSubscribe() {
        String data = gson.toJson(subscribes);
        preferenceUtils.setValue(PRE_NOVEL_FOCUS, data);
    }

    private boolean isFocused(String id) {
        User2Novel tmp = subscribeMap.get(id);
        return tmp != null;
    }


    public boolean isFocused(Novel novel) {
        if(novel.getId() != null){
            return isFocused(novel.getId());
        } else {
            for(User2Novel entity : subscribes){
                if(entity.getAuthor().equals(novel.getAuthor()) && entity.getTitle().equals(novel.getTitle())){
                    return true;
                }
            }
        }
        return false;
    }

    public String getNovelId(String name, String title){
        for(User2Novel entity : subscribes){
            if(entity.getAuthor().equals(name) && entity.getTitle().equals(title)){
                return entity.getNid();
            }
        }
        return "";
    }

    public List<User2Novel> getSubscribes() {
        return subscribes;
    }

    public void subscribe(User2Novel data) {
        subscribeMap.put(data.getNid(), data);
        if(!updateScribe(data, false)){
            subscribes.add(0, data);
        }
        persistenceSubscribe();
        notifyDataChange();
    }

    public void unSubscribe(String nid) {
        User2Novel user2Novel = subscribeMap.get(nid);
        if(user2Novel!=null){
            unSubscribe(user2Novel);
        }
    }

    public void unSubscribe(String name, String title){
        for(User2Novel entity : subscribes){
            if(entity.getAuthor().equals(name) && entity.getTitle().equals(title)){
                 unSubscribe(entity);
                return;
            }
        }
    }

    public void unSubscribe(User2Novel data) {
        subscribeMap.remove(data.getNid());
        updateScribe(data, true);
        persistenceSubscribe();
        notifyDataChange();
    }

    private boolean updateScribe(User2Novel data, boolean isRemove) {
        for(int i = 0; i < subscribes.size(); i++){
            if(subscribes.get(i).getId().equals(data.getId())){
                subscribes.remove(i);
                return true;
            }
        }
        return false;
    }
}
