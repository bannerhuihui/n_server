package com.huihui.netty.strategy.server.check.impl;

import com.huihui.netty.common.*;
import com.huihui.netty.pojo.ReadMessage;
import com.huihui.netty.pojo.ReturnMessage;
import com.huihui.netty.strategy.server.check.CheckFrom;
import org.apache.commons.lang3.StringUtils;

/**
 * @className: CheckFromLogin
 * @description:
 * @author: huihui
 * @createDate: 2019-09-05
 * @version: 1.0
 */
public class CheckFromLogin extends CheckFrom {

    @Override
    public ReturnMessage checkFrom(ReadMessage message) {
        ReturnMessage returnMessage = new ReturnMessage(message.getFrom(), message.getType());
        if (StringUtils.isNotEmpty(message.getFrom())) {
            int userId = 0;
            String from = "";
            String[] f = message.getFrom().split("_");
            if (f.length == 2) {
                //判断登录的用户类型是否正确
                for (String tag : NettyCliention._TAG) {
                    if (StringUtils.equals(tag, f[0])) {
                        from = f[0];
                        try {
                            userId = Integer.valueOf(f[1]);
                        } catch (Exception e) {
                            returnMessage.setCode(ErrorCode.LOGIN_USER_ID_NULL.getCode(),ErrorCode.LOGIN_USER_ID_NULL.getMessage());
                            e.printStackTrace();
                        }
                        break;
                    }
                }
                if (StringUtils.isNotEmpty(from) && userId != 0) {
                    //根据userId 查询用户在此处的登录信息，其他的登录信息不做处理，属于单独的实时信息
                    returnMessage.setCode(SuccessCode.SUCCESS_LOGIN.getCode(),SuccessCode.SUCCESS_LOGIN.getMessage());
                } else {
                    returnMessage.setCode(ErrorCode.MESSAGE_FROM_ERROR.getCode(),ErrorCode.MESSAGE_FROM_ERROR.getMessage());
                }
            } else {
                returnMessage.setCode(ErrorCode.MESSAGE_FROM_NULL.getCode(),ErrorCode.MESSAGE_FROM_NULL.getMessage());
            }
        }
        return returnMessage;
    }
}
