package com.huihui.netty.common;

/**
 * @className: MongClicationName
 * @description:
 * @author: huihui
 * @createDate: 2019-09-06
 * @version: 1.0
 */
public enum MongoClicationName {

    MSG_ALL("msg_all","所有的reqMessage消息"),
    USER_LOGIN("user_login","用户登录的信息");

    private String name;

    private String message;

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    MongoClicationName(String name, String message) {
        this.name = name;
        this.message = message;
    }
}
