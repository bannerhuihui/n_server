package com.huihui.netty.strategy.server.db.impl.user;

import com.alibaba.fastjson.JSONObject;
import com.huihui.netty.common.ErrorCode;
import com.huihui.netty.common.SuccessCode;
import com.huihui.netty.pojo.ReturnMessage;
import com.huihui.netty.pojo.UserPojo;
import com.huihui.netty.strategy.server.db.SaveMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @className: SaveUserLogin
 * @description:
 * @author: huihui
 * @createDate: 2019-09-23
 * @version: 1.0
 */
public class SaveUserLogin extends SaveMessage {

    private static final Logger LOGGER = LoggerFactory.getLogger(SaveUserLogin.class);

    @Override
    public ReturnMessage saveUser(UserPojo userPojo) {
        LOGGER.info("SaveUserLogin#saveUser入参为："+ JSONObject.toJSONString(userPojo));
        ReturnMessage returnMessage = new ReturnMessage("", 0);
        if (userPojo != null) {
            boolean b = userServices.saveUser(userPojo);
            if (b) {
                returnMessage.setCode(SuccessCode.SUCCESS_SAVE.getCode(),SuccessCode.SUCCESS_SAVE.getMessage());
            } else {
                returnMessage.setCode(ErrorCode.MESSAGE_SAVE_ERROR.getCode(),ErrorCode.MESSAGE_SAVE_ERROR.getMessage());
            }
            LOGGER.info("SaveUserLogin#saveUser登录添加用户信息到mongo数据库" + (b ? "成功！" : "失败！"));
        } else {
            returnMessage.setCode(ErrorCode.LOGIN_USER_NULL.getCode(),ErrorCode.LOGIN_USER_NULL.getMessage());
        }
        LOGGER.info("SaveUserLogin#saveUser出参为："+ JSONObject.toJSONString(returnMessage));
        return returnMessage;
    }
}
