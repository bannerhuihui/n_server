package com.huihui.netty.dao.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisServices {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisServices.class);

    @Autowired
    private RedisTemplate redisTemplate;


}
