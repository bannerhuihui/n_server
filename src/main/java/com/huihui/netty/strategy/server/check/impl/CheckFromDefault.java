package com.huihui.netty.strategy.server.check.impl;

import com.huihui.netty.common.ErrorCode;
import com.huihui.netty.common.NettyCliention;
import com.huihui.netty.common.SuccessCode;
import com.huihui.netty.pojo.ReadMessage;
import com.huihui.netty.pojo.ReturnMessage;
import com.huihui.netty.strategy.server.check.CheckFrom;
import org.apache.commons.lang3.StringUtils;

public class CheckFromDefault extends CheckFrom {

    @Override
    public ReturnMessage checkFrom(ReadMessage message) {
        ReturnMessage returnMessage = new ReturnMessage(message.getFrom(),message.getType());
        if(StringUtils.isNotEmpty(message.getFrom())){
            if(NettyCliention.ONLINE_USER != null && NettyCliention.ONLINE_USER.get(message.getFrom()) != null){
                returnMessage.setCode(SuccessCode.SUCCESS_LOGIN.getCode(),SuccessCode.SUCCESS_LOGIN.getMessage());
            }else{
                returnMessage.setCode(ErrorCode.LOGIN_OUT.getCode(),ErrorCode.LOGIN_OUT.getMessage());
            }
        }else{
            returnMessage.setCode(ErrorCode.LOGIN_ERROR.getCode(),ErrorCode.LOGIN_ERROR.getMessage());
        }
        return returnMessage;
    }
}
