package com.huihui.netty.strategy.server.db;

import com.huihui.netty.dao.DaoServer;
import com.huihui.netty.pojo.ReadMessage;
import com.huihui.netty.pojo.ReturnMessage;
import com.huihui.netty.pojo.UserPojo;

public abstract class UpdateMessage extends DaoServer {


    public ReturnMessage updateMessage(ReadMessage message) {
        return null;
    }

    public ReturnMessage updateUser(UserPojo userPojo) {
        return null;
    }
}
