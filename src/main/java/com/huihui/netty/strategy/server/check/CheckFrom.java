package com.huihui.netty.strategy.server.check;

import com.huihui.netty.pojo.ReadMessage;
import com.huihui.netty.pojo.ReturnMessage;

public abstract class CheckFrom {

    public ReturnMessage checkFrom(ReadMessage message){
        return null;
    }

}
