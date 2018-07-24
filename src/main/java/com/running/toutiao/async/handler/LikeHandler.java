package com.running.toutiao.async.handler;

import com.running.toutiao.async.EventHandler;
import com.running.toutiao.async.EventModel;
import com.running.toutiao.async.EventType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
//点赞事件处理器
public class LikeHandler implements EventHandler {
    @Override
    public void doHandler(EventModel model) {
        System.out.println("发生点赞事件");
        //可以给用户发一个站内信


    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
