package com.huihui.netty.dao.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class MysqlLoginServices {

    private static final Logger LOGGER = LoggerFactory.getLogger(MysqlLoginServices.class);

    @Autowired
    private JdbcTemplate template;


}
