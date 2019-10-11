package com.huihui.netty.dao;

import com.huihui.netty.dao.mongo.MongoMessageServices;
import com.huihui.netty.dao.mongo.MongoPhoneServices;
import com.huihui.netty.dao.mongo.MongoUserServices;
import com.huihui.netty.dao.mysql.MysqlServices;
import com.huihui.netty.dao.redis.RedisServices;
import com.huihui.netty.util.SpringContextUtil;

/**
 * @className: DaoServer
 * @description:
 * @author: huihui
 * @createDate: 2019-09-23
 * @version: 1.0
 */
public class DaoServer {

    public MongoUserServices userServices = SpringContextUtil.getBean("mongoUserServices");

    public MongoPhoneServices phoneServices = SpringContextUtil.getBean("mongoPhoneServices");

    public MongoMessageServices messageServices = SpringContextUtil.getBean("mongoMessageServices");

    public MysqlServices mysqlServices = SpringContextUtil.getBean("mysqlServices");

    public RedisServices redisServices = SpringContextUtil.getBean("redisServices");
}
