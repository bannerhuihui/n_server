package com.huihui.netty.strategy.server;

import com.alibaba.fastjson.JSONObject;
import com.huihui.netty.common.*;
import com.huihui.netty.pojo.ReadMessage;
import com.huihui.netty.pojo.ReturnMessage;
import com.huihui.netty.strategy.common.HandlerStrategy;
import com.huihui.netty.util.Base64Util;
import io.netty.channel.ChannelHandlerContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Log
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
                        JSONObject heard = JSONObject.parseObject(Base64Util.decode(message.getContent()));
                        if(NettyCliention.HEARD_STATE.get(message.getFrom()) != heard){
                            NettyCliention.HEARD_STATE.put(message.getFrom(),heard);
                            //TODO
                        }else{
                            //TODO
                        }
                    }else if(message.getContentType() == MessageConfig.APP_HEARD_CONTENT_TYPE.getType()){
                        tagName = MessageConfig.APP_HEARD_CONTENT_TYPE.getMessage();
                        //TODO
                    }else if(message.getContentType() == MessageConfig.GZH_HEARD_CONTENT_TYPE.getType()){
                        tagName = MessageConfig.GZH_HEARD_CONTENT_TYPE.getMessage();
                    }else if(message.getContentType() == MessageConfig.KF_HEARD_CONTENT_TYPE.getType()){
                        tagName = MessageConfig.KF_HEARD_CONTENT_TYPE.getMessage();
                    }else if(message.getContentType() == MessageConfig.XCX_HEARD_CONTENT_TYPE.getType()){
                        tagName = MessageConfig.XCX_HEARD_CONTENT_TYPE.getMessage();
                    }else{
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
        if(returnMessage.getCode() != 0){
            returnMessage.setContent(JSONObject.toJSONString(message));
        }else{
            returnMessage.setCode(SuccessCode.SUCCESS_HEARD.getCode());
        }
        NettyCliention.returnMessage(returnMessage,ctx,tagName);
    }
}
