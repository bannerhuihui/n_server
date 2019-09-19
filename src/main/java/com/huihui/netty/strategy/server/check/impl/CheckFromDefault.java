package com.huihui.netty.strategy.server.check.impl;

import com.huihui.netty.common.NettyCliention;
import com.huihui.netty.pojo.ReadMessage;
import com.huihui.netty.strategy.server.check.CheckFrom;
import org.apache.commons.lang3.StringUtils;

public class CheckFromDefault extends CheckFrom {

    @Override
    public boolean checkFrom(ReadMessage message) {
        if(StringUtils.isNotEmpty(message.getFrom()) && NettyCliention.ONLINE_USER != null && NettyCliention.ONLINE_USER.get(message.getFrom()) != null){
            return true;
        }
        return false;
    }
}
