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

public class Heard extends HandlerStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(Heard.class);

    @Override
    public void contentDistribution(ReadMessage message, ChannelHandlerContext ctx) {

        ReturnMessage returnMessage = new ReturnMessage(message.getFrom(),message.getType());

        String tagName = "";

        if(StringUtils.isNotEmpty(message.getFrom())){
            if(NettyCliention.ONLINE_USER.get(message.getFrom()) != null){
                if(StringUtils.isNotEmpty(message.getContent())){
                    if(message.getContentType() == MessageConfig.PZB_HEARD_CONTENT_TYPE.getType()){
                        tagName = MessageConfig.PZB_HEARD_CONTENT_TYPE.getMessage();
                        pzbHeard(message,returnMessage);
                    }else if(message.getContentType() == MessageConfig.APP_HEARD_CONTENT_TYPE.getType()){
                        tagName = MessageConfig.APP_HEARD_CONTENT_TYPE.getMessage();
                        appHeard(message,returnMessage);
                    }else if(message.getContentType() == MessageConfig.GZH_HEARD_CONTENT_TYPE.getType()){
                        tagName = MessageConfig.GZH_HEARD_CONTENT_TYPE.getMessage();
                        gzhHeard(message,returnMessage);
                    }else if(message.getContentType() == MessageConfig.KF_HEARD_CONTENT_TYPE.getType()){
                        tagName = MessageConfig.KF_HEARD_CONTENT_TYPE.getMessage();
                        kfHeard(message,returnMessage);
                    }else if(message.getContentType() == MessageConfig.XCX_HEARD_CONTENT_TYPE.getType()){
                        tagName = MessageConfig.XCX_HEARD_CONTENT_TYPE.getMessage();
                        xcxHeard(message,returnMessage);
                    }else if(message.getContentType() == MessageConfig.JAVA_CLIENT_HEARD_CONTENT_TYPE.getType()){
                        tagName = MessageConfig.JAVA_CLIENT_HEARD_CONTENT_TYPE.getMessage();
                        javaClientHeard(message,returnMessage,ctx);
                    }else {
                        returnMessage.setCode(ErrorCode.MESSAGE_CONTENT_TYPE_ERROR.getCode());
                    }
                }else{
                    returnMessage.setCode(ErrorCode.MESSAGE_CONTENT_NULL.getCode());
                }
            }else{
                returnMessage.setCode(ErrorCode.LOGIN_OUT.getCode());
            }
        }else {
            returnMessage.setCode(ErrorCode.MESSAGE_FROM_NULL.getCode());
        }
        NettyCliention.returnMessage(returnMessage,ctx,tagName);
    }

    /**
     * 接收java客户端的心跳
     * @param message
     * @param returnMessage
     * @param ctx
     */
    private void javaClientHeard(ReadMessage message, ReturnMessage returnMessage, ChannelHandlerContext ctx) {
        if(StringUtils.equals(message.getContent(),ProConfig.HEARD_CONTENT.getCode())){
            if(ctx != NettyCliention.CLINE_USER.get(ProConfig.CLIENT_NAME.getCode())){
                NettyCliention.saveOrUpdJavaClient(ProConfig.CLIENT_NAME.getCode(),ctx);
                LOGGER.info("Heard#javaClientHeard java客户端发生变化！");
                returnMessage.setCode(SuccessCode.SUCCESS_HEARD.getCode(),SuccessCode.SUCCESS_HEARD.getMessage());
            }else {
                returnMessage.setCode(ErrorCode.MESSAGE_CONTENT_ERROR.getCode(),ErrorCode.MESSAGE_CONTENT_ERROR.getMessage());
            }
        }else{
            returnMessage.setCode(ErrorCode.MESSAGE_CONTENT_ERROR.getCode(),ErrorCode.MESSAGE_CONTENT_ERROR.getMessage());
        }
    }

    private void xcxHeard(ReadMessage message, ReturnMessage returnMessage) {
    }

    private void kfHeard(ReadMessage message, ReturnMessage returnMessage) {
    }

    private void gzhHeard(ReadMessage message, ReturnMessage returnMessage) {
    }

    private void appHeard(ReadMessage message, ReturnMessage returnMessage) {
    }

    private void pzbHeard(ReadMessage message, ReturnMessage returnMessage) {
    }
}
