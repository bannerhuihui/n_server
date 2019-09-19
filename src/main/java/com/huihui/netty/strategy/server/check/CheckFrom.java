package com.huihui.netty.strategy.server.check;

import com.huihui.netty.pojo.ReadMessage;
import com.huihui.netty.pojo.UserPojo;

public abstract class CheckFrom {

    public boolean checkFrom(ReadMessage message){
        return false;
    }

}
