package com.huihui.netty.strategy.server.check.impl;

import com.huihui.netty.common.ErrorCode;
import com.huihui.netty.common.ProFunctionName;
import com.huihui.netty.pojo.ReadMessage;
import com.huihui.netty.pojo.ReturnMessage;
import com.huihui.netty.strategy.server.check.CheckContent;
import org.apache.commons.lang3.StringUtils;


/**
 * @className: CheckContentLogin
 * @description:
 * @author: huihui
 * @createDate: 2019-09-19
 * @version: 1.0
 */
public class CheckContentLogin extends CheckContent {

    @Override
    public ReturnMessage checkContent(ReadMessage message) {
        ReturnMessage returnMessage = new ReturnMessage(message.getFrom(), message.getType());
        if (StringUtils.isNotEmpty(message.getContent())) {
            if (message.getContentType() != ProFunctionName.JAVA_CLIENT_LOGIN.getCode()) {
            } else {
                returnMessage.setCode(ErrorCode.MESSAGE_CONTENT_NULL.getCode());
            }
        } else {
            returnMessage.setCode(ErrorCode.MESSAGE_FROM_NULL.getCode());
        }
        return returnMessage;
    }
}
