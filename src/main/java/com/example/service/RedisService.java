package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;


    public void testRedis(){

        ValueOperations<Object, String> ops = redisTemplate.opsForValue();
        String key = "spring.boot.redis.test";
        if (!redisTemplate.hasKey(key)) {
            ops.set(key, "foo");
        }
        System.out.println("Found" + key + ", value=" + ops.get(key));

    }


}
