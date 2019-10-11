package com.huihui.netty.strategy.server;

import com.alibaba.fastjson.JSONObject;
import com.huihui.netty.common.*;
import com.huihui.netty.pojo.ReadMessage;
import com.huihui.netty.pojo.ReturnMessage;
import com.huihui.netty.pojo.UserPojo;
import com.huihui.netty.strategy.common.HandlerStrategy;
import com.huihui.netty.strategy.server.check.impl.CheckFromLogin;
import com.huihui.netty.strategy.server.db.impl.user.MysqlQueryUserLogin;
import com.huihui.netty.strategy.server.db.impl.user.QueryUserLogin;
import com.huihui.netty.strategy.server.db.impl.user.SaveUserLogin;
import com.huihui.netty.strategy.server.db.impl.user.UpdateUserLogin;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @className: UserLogin
 * @description: 用户登录操作
 * @author: huihui
 * @createDate: 2019-09-19
 * @version: 1.0
 */
public class UserLogin extends HandlerStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLogin.class);

    @Override
    public void contentDistribution(ReadMessage message, ChannelHandlerContext ctx) {
        ReturnMessage returnMessage = checkFrom(message, CheckFromLogin.class);
        int userId = 0;
        String tag = "";
        UserPojo userPojo = new UserPojo();
        if(returnMessage.getCode() == SuccessCode.SUCCESS_LOGIN.getCode()){
            String [] from = message.getFrom().split("_");
            userId = Integer.valueOf(from[1]);
            tag = from [0];
            userPojo.setUserId(userId);
            userPojo.setLastUpdTime(new Date().getTime());
            ReturnMessage mongoUser = queryUser(userPojo, QueryUserLogin.class);
            if(mongoUser.getCode() == SuccessCode.SUCCESS_LOGIN.getCode()){
                //mongo中已经保存了登录信息，之后做修改
                ReturnMessage updateUser = updateUser(userPojo, UpdateUserLogin.class);
                if(updateUser.getCode() == SuccessCode.SUCCESS_UPDATE.getCode()){
                    //更新用户信息成功！
                    returnMessage.setCode(SuccessCode.SUCCESS_LOGIN.getCode(),SuccessCode.SUCCESS_LOGIN.getMessage());
                }else{
                    returnMessage.setCode(updateUser.getCode(),updateUser.getMessage());
                }
            }else{
                //mongo中没有用户的信息，查询MySql数据库认证
                ReturnMessage mysqlUser = queryUser(userPojo, MysqlQueryUserLogin.class);
                if(mysqlUser.getCode() == SuccessCode.SUCCESS_QUERY.getCode()){
                    userPojo = JSONObject.parseObject(mysqlUser.getContent(), UserPojo.class);
                    userPojo.setCreateTime(new Date().getTime());
                    userPojo.setState(StateConfig.TRUE_STATE.getCode());
                    userPojo.setLastUpdTime(userPojo.getCreateTime());
                    userPojo.setFrom(tag);
                    //将消息进行保存
                    ReturnMessage saveUser = saveUser(userPojo, SaveUserLogin.class);
                    if(saveUser.getCode() == SuccessCode.SUCCESS_SAVE.getCode()){
                        returnMessage.setCode(SuccessCode.SUCCESS_LOGIN.getCode(),SuccessCode.SUCCESS_LOGIN.getMessage());
                    }else{
                        returnMessage.setCode(saveUser.getCode(),saveUser.getMessage());
                    }
                }
                returnMessage.setCode(mongoUser.getCode(),mongoUser.getMessage());
            }
        }
        if(returnMessage.getCode() != SuccessCode.SUCCESS_LOGIN.getCode()){
            returnMessage.setBaseContent(JSONObject.toJSONString(message));
            NettyCliention.returnMessage(returnMessage,ctx, ProFunctionName.USER_LOGIN.getMessage());
        }else{
            //将连接保存到连接池
            NettyCliention.saveOrUpdClient(message.getFrom(),ctx);
            //更新用户的状态
            NettyCliention.changeUserState(userId,tag,true);
            NettyCliention.returnMessage(returnMessage,ctx, ProFunctionName.USER_LOGIN.getMessage());
            //广播该用户上线
            ReturnMessage pushAllMessage = pushAll(userPojo);
            NettyCliention.pushAllCli(pushAllMessage);
        }

    }

    private ReturnMessage pushAll(UserPojo userPojo){
        ReturnMessage returnMessage = new ReturnMessage(ProConfig.SERVER_NAME.getCode(), ProFunctionName.PUSH_ALL_MESSAGE.getCode());
        returnMessage.setBaseContent(JSONObject.toJSONString(userPojo));
        returnMessage.setCode(ProFunctionName.SERVER_CODE.getCode(),ProFunctionName.SERVER_CODE.getMessage());
        return returnMessage;
    }
}
