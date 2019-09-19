package com.huihui.netty.pojo;

import java.io.Serializable;

/**
 * 返回消息模板
 */
public class ReturnMessage implements Serializable {

    private static final long serialVersionUID = -4081948326353305859L;

    private String from;

    private String content;

    private int code;

    private int type;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public ReturnMessage(String from , int type){
        this.type = type;
        this.from = from;
    }
}
