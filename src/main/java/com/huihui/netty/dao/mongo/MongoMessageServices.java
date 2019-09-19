package com.huihui.netty.dao.mongo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class MongoMessageServices {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoMessageServices.class);

    @Autowired
    private MongoTemplate template;
}
