package com.huihui.netty.server;

import com.huihui.netty.common.*;
import com.huihui.netty.dao.mongo.MongoLoginServices;
import com.huihui.netty.pojo.ReturnMessage;
import com.huihui.netty.pojo.UserPojo;
import com.huihui.netty.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @className: UserLogin
 * @description: 用户登录，保存登录的用户历史以及登录的信息，该数据保存到数据库
 * @author: huihui
 * @createDate: 2019-09-16
 * @version: 1.0
 */
public class UserLogin {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLogin.class);

    private MongoLoginServices loginServices = SpringContextUtil.getBean("mongoLoginServices");

    public ReturnMessage login(String token) {

        ReturnMessage returnMessage = new ReturnMessage(ProConfig.SERVER_NAME.getCode(), ProFunctionName.USER_LOGIN.getCode());
        UserPojo userPojo = checkToken(token);
        if (userPojo != null && userPojo.getUserId() != 0) {
            //保存用户的登录信息
            UserPojo user = loginServices.queryUserPojoByUserId(userPojo.getUserId());
            boolean b = false;
            if (user != null) {
                //之前有过登录历史，做修改操作，将最近的活跃时间改为当前时间
                userPojo.setLastUpdTime(new Date().getTime());
                b = loginServices.updateUserByUserId(userPojo);
                LOGGER.info("UserLogin#login更新用户信息：保存" + (b ? "成功！" : "失败！"));
            } else {
                //之前没有登录，做登录记录
                userPojo.setCreateTime(new Date().getTime());
                userPojo.setLastUpdTime(new Date().getTime());
                userPojo.setState(StateConfig.TRUE_STATE.getCode());
                //保存用户的登录信息，如果需要其他相关的用户信息，可以从其他库进行查询
                b = loginServices.saveLoginUser(userPojo);
                LOGGER.info("UserLogin#login保存用户信息：保存" + (b ? "成功！" : "失败！"));
            }
            returnMessage.setCode(SuccessCode.SUCCESS_LOGIN.getCode());
            returnMessage.setContent(SuccessCode.SUCCESS_LOGIN.getMessage());
        } else {
            returnMessage.setCode(ErrorCode.LOGIN_TOKEN_ERROR.getCode());
            returnMessage.setContent(ErrorCode.LOGIN_TOKEN_ERROR.getMessage() + "参数为：" + token);
        }
        return returnMessage;
    }


    private UserPojo checkToken(String token) {

        return null;
    }

}
