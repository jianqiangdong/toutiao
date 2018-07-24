package com.running.toutiao.async;

import com.alibaba.fastjson.JSON;
import com.running.toutiao.util.JedisAdapter;
import com.running.toutiao.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {

    @Autowired
    JedisAdapter jedisAdapter;

    //发送一个事件
    public boolean fireEvent(EventModel model) {
        try {
            String jsonModel = JSON.toJSONString(model);
            String redisKey = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(redisKey, jsonModel);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }





}
