package com.running.toutiao.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Component
public class JedisAdapter implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(JedisAdapter.class);

//    private Jedis jedis = null;
    private JedisPool pool = null;


    //初始化完成之后执行该方法
    @Override
    public void afterPropertiesSet() throws Exception {

        String host = "116.196.88.60";
        int port = 6379;
        pool = new JedisPool(host, port);
    }


    private Jedis getJedis() {
        return pool.getResource();
    }

    public String get(String key) {
        Jedis jedis = null;

        try {
            jedis = pool.getResource();
            return jedis.get(key);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
            return null;
        }finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


    public void lpush(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            jedis.lpush(key, value);
        } catch (Exception e) {
            logger.error("发生异常" + e.getMessage());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


        public List<String> brpop(int timeout, String key){
            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                return jedis.brpop(timeout, key);
            } catch (Exception e) {
                logger.error("发生异常" + e.getMessage());
                return null;
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }


        public void set (String key, String value){

            Jedis jedis = null;
            try {
                jedis = pool.getResource();
                jedis.set(key, value);
            } catch (Exception e) {
                logger.error("发生异常" + e.getMessage());
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
        }

        public void setObject (String key, Object object){
            set(key, JSON.toJSONString(object));
        }

        public <T> T getObject(String key, Class < T > clazz) {
            String value = get(key);
            if (value != null) {
                return JSONObject.parseObject(value, clazz);
            }
            return null;
        }

}
