package com.huihui.netty.strategy.server.db;

import com.huihui.netty.dao.DaoServer;
import com.huihui.netty.pojo.ReadMessage;
import com.huihui.netty.pojo.ReturnMessage;
import com.huihui.netty.pojo.UserPojo;

public abstract class SaveMessage extends DaoServer {


    public ReturnMessage saveMessage(ReadMessage message) {
        return null;
    }

    public ReturnMessage saveUser(UserPojo userPojo) {
        return null;
    }
}
