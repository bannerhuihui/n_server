package com.huihui.netty.dao.mongo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class MongoPhoneServices {

    private static final Logger LOGGER = LoggerFactory.getLogger(MongoPhoneServices.class);

    @Autowired
    private MongoTemplate template;
}
