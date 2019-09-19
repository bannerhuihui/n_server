package com.huihui.netty.strategy.server.check;

import com.huihui.netty.pojo.ReadMessage;
import com.huihui.netty.pojo.UserPojo;

import java.util.HashMap;

public abstract class CheckContent {

    public HashMap<String,Object> checkContent(ReadMessage message){
        return null;
    }

    public UserPojo login(ReadMessage message){
        return null;
    }
}
