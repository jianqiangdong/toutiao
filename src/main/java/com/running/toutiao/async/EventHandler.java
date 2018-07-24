package com.running.toutiao.async;

import java.util.List;

public interface EventHandler {
    void doHandler(EventModel model);
    List<EventType> getSupportEventTypes();//事件处理器要处理的事件类型
}
