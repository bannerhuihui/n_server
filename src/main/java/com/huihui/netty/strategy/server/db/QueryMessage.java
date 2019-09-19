package com.huihui.netty.strategy.server.db;

import com.huihui.netty.pojo.ReadMessage;

import java.util.List;

public abstract class QueryMessage {

    public List<ReadMessage> queryReadMessage(ReadMessage message){
        return null;
    }
}
