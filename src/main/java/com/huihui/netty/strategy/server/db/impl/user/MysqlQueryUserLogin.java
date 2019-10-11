package com.huihui.netty.strategy.server.db.impl.user;

import com.alibaba.fastjson.JSONObject;
import com.huihui.netty.common.ErrorCode;
import com.huihui.netty.common.SuccessCode;
import com.huihui.netty.pojo.ReturnMessage;
import com.huihui.netty.pojo.UserPojo;
import com.huihui.netty.strategy.server.db.QueryMessage;

/**
 * @className: MysqlQueryUserLogin
 * @description:
 * @author: huihui
 * @createDate: 2019-09-23
 * @version: 1.0
 */
public class MysqlQueryUserLogin extends QueryMessage {

    @Override
    public ReturnMessage queryUser(UserPojo user) {
        ReturnMessage returnMessage = new ReturnMessage("", 0);
        if (user != null && user.getUserId() != 0) {
            UserPojo userPojo = mysqlServices.queryUserById(user.getUserId());
            if (userPojo != null) {
                returnMessage.setCode(SuccessCode.SUCCESS_QUERY.getCode(),SuccessCode.SUCCESS_QUERY.getMessage());
                returnMessage.setContent(JSONObject.toJSONString(userPojo));
            } else {
                returnMessage.setCode(ErrorCode.LOGIN_USER_NULL.getCode(),ErrorCode.LOGIN_USER_NULL.getMessage());
            }
        } else {
            returnMessage.setCode(ErrorCode.LOGIN_USER_ID_NULL.getCode(),ErrorCode.LOGIN_USER_ID_NULL.getMessage());
        }
        return returnMessage;
    }
}
