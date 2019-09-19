package com.huihui.netty.strategy.server.db;

import com.huihui.netty.pojo.ReadMessage;

public abstract class SaveMessage {

    public boolean saveMessage(ReadMessage message){
        return false;
    }
}
