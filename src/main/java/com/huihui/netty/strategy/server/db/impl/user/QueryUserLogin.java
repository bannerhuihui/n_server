package com.huihui.netty.strategy.server.db.impl.user;

import com.alibaba.fastjson.JSONObject;
import com.huihui.netty.common.ErrorCode;
import com.huihui.netty.common.SuccessCode;
import com.huihui.netty.pojo.ReturnMessage;
import com.huihui.netty.pojo.UserPojo;
import com.huihui.netty.strategy.server.db.QueryMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @className: QueryUserLogin
 * @description:
 * @author: huihui
 * @createDate: 2019-09-23
 * @version: 1.0
 */
public class QueryUserLogin extends QueryMessage {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryUserLogin.class);

    @Override
    public ReturnMessage queryUser(UserPojo user) {
        //LOGGER.info("QueryUserLogin#queryUser入参为：" + JSONObject.toJSONString(user));
        ReturnMessage returnMessage = new ReturnMessage("", 0);
        if (user != null && user.getUserId() != 0) {
            UserPojo userPojo = userServices.queryUserByUserId(user.getUserId());
            if (userPojo != null) {
                returnMessage.setCode(SuccessCode.SUCCESS_LOGIN.getCode(),SuccessCode.SUCCESS_LOGIN.getMessage());
                returnMessage.setContent(JSONObject.toJSONString(userPojo));
            } else {
                returnMessage.setCode(ErrorCode.LOGIN_USER_NULL.getCode(),ErrorCode.LOGIN_USER_NULL.getMessage());
            }
        } else {
            returnMessage.setCode(ErrorCode.LOGIN_USER_ID_NULL.getCode(),ErrorCode.LOGIN_USER_ID_NULL.getMessage());
        }
        //LOGGER.info("QueryUserLogin#queryUser出参为：" + JSONObject.toJSONString(user));
        return returnMessage;
    }
}
