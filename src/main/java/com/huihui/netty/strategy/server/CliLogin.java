package com.huihui.netty.strategy.server;

import com.alibaba.fastjson.JSONObject;
import com.huihui.netty.common.*;
import com.huihui.netty.pojo.ReadMessage;
import com.huihui.netty.pojo.ReturnMessage;
import com.huihui.netty.strategy.common.HandlerStrategy;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.acl.LastOwnerException;

/**
 * @className: CliLogin
 * @description:
 * @author: huihui
 * @createDate: 2019-09-24
 * @version: 1.0
 */
public class CliLogin extends HandlerStrategy {

    public static final Logger LOGGER = LoggerFactory.getLogger(CliLogin.class);

    @Override
    public void contentDistribution(ReadMessage message, ChannelHandlerContext ctx) {
        ReturnMessage returnMessage = checkJavaCline(message);
        if(returnMessage.getCode() == SuccessCode.SUCCESS_LOGIN.getCode()){
            NettyCliention.saveOrUpdJavaClient(message.getFrom(),ctx);
        }
        NettyCliention.returnMessage(returnMessage,ctx, ProFunctionName.JAVA_CLIENT_LOGIN.getMessage());
    }
}
