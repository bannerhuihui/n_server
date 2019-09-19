package com.huihui.netty.common;

/**
 * @className: StateConfig
 * @description:
 * @author: huihui
 * @createDate: 2019-09-16
 * @version: 1.0
 */
public enum  StateConfig {

    TRUE_STATE(1,"正常状态"),
    FALSE_STATE(0,"异常状态");

    private int code;

    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    StateConfig(int code, String message){
        this.code = code;
        this.message = message;
    }

}
