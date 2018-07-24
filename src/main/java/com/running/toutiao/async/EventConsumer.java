package com.running.toutiao.async;

import com.alibaba.fastjson.JSONObject;
import com.running.toutiao.util.JedisAdapter;
import com.running.toutiao.util.RedisKeyUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;
    private Map<EventType, List<EventHandler>> config = new HashMap<>();

    @Autowired
    private JedisAdapter jedisAdapter;



    @Override

    public void afterPropertiesSet() throws Exception {
        //拿到容器中所有EventHandler类型的Bean
        Map<String, EventHandler> handlers = applicationContext.getBeansOfType(EventHandler.class);
        if (handlers != null) {
            for (Map.Entry<String, EventHandler> entry : handlers.entrySet()) {
                EventHandler eventHandler = entry.getValue();
                List<EventType> eventTypes = eventHandler.getSupportEventTypes();
                    for (EventType type : eventTypes) {
                        if (!config.containsKey(type)) {
                            config.put(type, new ArrayList<>());
                        }
                        config.get(type).add(eventHandler);
                    }
                System.out.println("================");
                System.out.println(entry.getKey());
            }
        }


        //开启一个线程处理队列中的事件
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String redisKey = RedisKeyUtil.getEventQueueKey();
                    //每次从队列中取一个值。返回值是列表，第一个元素是队列key,第二个元素是队列中最右边的元素
                    List<String> event = jedisAdapter.brpop(0, redisKey);
                    System.out.println(event);//[EVENT,
                                                // {"actorId":0,"entityId":0,"entityOwnerId":0,"entityType":0,"exts":{},"type":"LIKE"}]
                    String eventJson = event.get(1);
                    EventModel eventModel = JSONObject.parseObject(eventJson, EventModel.class);
                    if (!config.containsKey(eventModel.getType())) {
                        System.out.println("不能识别的事件");
                        continue;
                    }
                    List<EventHandler> handlerList = config.get(eventModel.getType());
                    for (EventHandler eventHandler : handlerList) {
                        eventHandler.doHandler(eventModel);
                    }
                }
            }
        }).start();


    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
