package com.huihui.netty.strategy.server.check;

import com.huihui.netty.pojo.ReadMessage;

import java.util.List;

public abstract class CheckTo {

    public List<String> checkTo(ReadMessage message){
        return null;
    }
}
