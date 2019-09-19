package com.huihui.netty.strategy.server.db;

import com.huihui.netty.pojo.ReadMessage;

public abstract class UpdateMesage {

    public boolean updateMessage(ReadMessage message){
        return false;
    }
}
