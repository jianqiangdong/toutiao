package com.running.toutiao;

import com.running.toutiao.async.EventModel;
import com.running.toutiao.async.EventProducer;
import com.running.toutiao.async.EventType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ToutiaoApplicationTests {

    @Autowired
    EventProducer eventProducer;

    @Test
    public void contextLoads() {

        EventModel eventModel = new EventModel();
        eventModel.setType(EventType.LIKE);
        eventProducer.fireEvent(eventModel);
        EventModel eventModel1 = new EventModel();
        eventModel1.setType(EventType.LOGIN);
        eventProducer.fireEvent(eventModel1);
    }

}
