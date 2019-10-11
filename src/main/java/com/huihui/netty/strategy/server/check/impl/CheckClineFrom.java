package com.huihui.netty.strategy.server.check.impl;

import com.alibaba.fastjson.JSONObject;
import com.huihui.netty.common.ErrorCode;
import com.huihui.netty.common.NettyCliention;
import com.huihui.netty.common.ProConfig;
import com.huihui.netty.common.SuccessCode;
import com.huihui.netty.pojo.ReadMessage;
import com.huihui.netty.pojo.ReturnMessage;
import com.huihui.netty.strategy.server.check.CheckFrom;
import org.apache.commons.lang3.StringUtils;

/**
 * @className: CheckClineFrom
 * @description:
 * @author: huihui
 * @createDate: 2019-09-24
 * @version: 1.0
 */
public class CheckClineFrom extends CheckFrom {

    @Override
    public ReturnMessage checkFrom(ReadMessage message) {
        ReturnMessage returnMessage = new ReturnMessage(ProConfig.SERVER_NAME.getCode(),message.getType());
        if(StringUtils.equals(message.getFrom(), ProConfig.CLIENT_NAME.getCode())){
            //将该客户端保存到单独的端口中，之后集群后可以轮训执行
            returnMessage.setCode(SuccessCode.SUCCESS_LOGIN.getCode(), SuccessCode.SUCCESS_LOGIN.getMessage());
        }else{
            returnMessage.setCode(ErrorCode.LOGIN_CLINE_ERROR.getCode(),ErrorCode.LOGIN_CLINE_ERROR.getMessage());
            returnMessage.setBaseContent(JSONObject.toJSONString(message));
        }
        return returnMessage;
    }
}
