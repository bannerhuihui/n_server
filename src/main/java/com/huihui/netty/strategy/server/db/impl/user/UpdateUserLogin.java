package com.huihui.netty.strategy.server.db.impl.user;

import com.alibaba.fastjson.JSONObject;
import com.huihui.netty.common.ErrorCode;
import com.huihui.netty.common.SuccessCode;
import com.huihui.netty.pojo.ReturnMessage;
import com.huihui.netty.pojo.UserPojo;
import com.huihui.netty.strategy.server.db.UpdateMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @className: UpdateUserLogin
 * @description:
 * @author: huihui
 * @createDate: 2019-09-23
 * @version: 1.0
 */
public class UpdateUserLogin extends UpdateMessage {

    private static final Logger LOGGER = LoggerFactory.getLogger(UpdateUserLogin.class);

    @Override
    public ReturnMessage updateUser(UserPojo userPojo) {
        LOGGER.info("UpdateUserLogin#updateUser入参："+ JSONObject.toJSONString(userPojo));
        ReturnMessage returnMessage = new ReturnMessage("", 0);
        if (userPojo != null && userPojo.getUserId() != 0) {
            boolean b = userServices.updateUserByUserId(userPojo);
            LOGGER.info("UpdateUserLogin#updateUser更新数据" + (b ? "成功！" : "失败！"));
            if(b){
                returnMessage.setCode(SuccessCode.SUCCESS_UPDATE.getCode(),SuccessCode.SUCCESS_UPDATE.getMessage());
            }else{
                returnMessage.setCode(ErrorCode.MESSAGE_UPDATE_ERROR.getCode(),ErrorCode.MESSAGE_UPDATE_ERROR.getMessage());
            }
        } else {
            returnMessage.setCode(ErrorCode.LOGIN_USER_ID_NULL.getCode(),ErrorCode.LOGIN_USER_ID_NULL.getMessage());
        }
        LOGGER.info("UpdateUserLogin#updateUser出参："+ JSONObject.toJSONString(userPojo));
        return returnMessage;
    }
}
