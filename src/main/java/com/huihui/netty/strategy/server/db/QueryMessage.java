package com.huihui.netty.strategy.server.db;

import com.huihui.netty.dao.DaoServer;
import com.huihui.netty.pojo.ReadMessage;
import com.huihui.netty.pojo.ReturnMessage;
import com.huihui.netty.pojo.UserPojo;


public abstract class QueryMessage extends DaoServer {

    public ReturnMessage queryReadMessage(ReadMessage message){
        return null;
    }

    public ReturnMessage queryUser(UserPojo user){return null;}
}
