package com.huihui.netty.strategy.server;

import com.alibaba.fastjson.JSONObject;
import com.huihui.netty.common.*;
import com.huihui.netty.pojo.ReadMessage;
import com.huihui.netty.pojo.ReturnMessage;
import com.huihui.netty.pojo.UserPojo;
import com.huihui.netty.strategy.common.HandlerStrategy;
import com.huihui.netty.util.Base64Util;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @className: QueryOnlineUser
 * @description:
 * @author: huihui
 * @createDate: 2019-09-16
 * @version: 1.0
 */
public class QueryOnlineUser extends HandlerStrategy {


    @Override
    public void contentDistribution(ReadMessage message, ChannelHandlerContext ctx) {

        ReturnMessage returnMessage = new ReturnMessage(ProConfig.SERVER_NAME.getCode(),ProFunctionName.ONLINE_USER_QUERY.getCode());
        if(StringUtils.isNotEmpty(message.getFrom())){
            if(NettyCliention.ONLINE_USER.get(message.getFrom()) != null){
                List<UserPojo> onlineUser = NettyCliention.queryOnlineUser();
                returnMessage.setCode(SuccessCode.SUCCESS_QUERY.getCode());
                returnMessage.setContent(Base64Util.encode(JSONObject.toJSONString(onlineUser)));
            }else{
                returnMessage.setCode(ErrorCode.LOGIN_OUT.getCode());
                returnMessage.setContent(ErrorCode.LOGIN_OUT.getMessage() + "参数为："+ JSONObject.toJSONString(message));
            }
        }else {
            returnMessage.setCode(ErrorCode.MESSAGE_FROM_NULL.getCode());
            returnMessage.setContent(ErrorCode.MESSAGE_FROM_NULL.getMessage() + "参数为："+ JSONObject.toJSONString(message));
        }
        NettyCliention.returnMessage(returnMessage,ctx,ProFunctionName.ONLINE_USER_QUERY.getMessage());
    }
}
