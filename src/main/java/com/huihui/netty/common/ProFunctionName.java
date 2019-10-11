package com.huihui.netty.common;

/**
 * @className: ProFunctionName
 * @description:
 * @author: huihui
 * @createDate: 2019-09-10
 * @version: 1.0
 */
public enum  ProFunctionName {

    USER_LOGIN(1001,"用户登录"),
    ONLINE_USER_QUERY(0,"查询在线用户列表"),
    JAVA_CLIENT_LOGIN(1,"java客户端登录"),
    MESSAGE_ERROR(2,"接收的message信息"),
    PUSH_ALL_MESSAGE(300001,"消息群发"),
    SERVER_CODE(400001,"系统消息"),
    HEARD(2,"心跳")
    ;

    private int code;

    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ProFunctionName(int code, String message){
        this.code = code;
        this.message = message;
    }

}
