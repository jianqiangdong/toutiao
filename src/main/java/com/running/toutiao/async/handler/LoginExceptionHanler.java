package com.running.toutiao.async.handler;

import com.running.toutiao.async.EventHandler;
import com.running.toutiao.async.EventModel;
import com.running.toutiao.async.EventType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class LoginExceptionHanler implements EventHandler {
    @Override
    public void doHandler(EventModel model) {

        //登陆需要处理的事。发邮件
        //判断是否有异常登陆(ip地址异地)
        System.out.println("有人登陆了");
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}
