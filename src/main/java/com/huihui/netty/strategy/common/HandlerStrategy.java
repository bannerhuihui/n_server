package com.huihui.netty.strategy.common;

import com.huihui.netty.pojo.ReadMessage;
import com.huihui.netty.pojo.ReturnMessage;
import com.huihui.netty.pojo.UserPojo;
import com.huihui.netty.strategy.common.util.StrategyUtils;
import com.huihui.netty.strategy.server.check.CheckCc;
import com.huihui.netty.strategy.server.check.CheckContent;
import com.huihui.netty.strategy.server.check.CheckFrom;
import com.huihui.netty.strategy.server.check.CheckTo;
import com.huihui.netty.strategy.server.check.impl.CheckClineFrom;
import com.huihui.netty.strategy.server.check.impl.CheckFromDefault;
import com.huihui.netty.strategy.server.db.QueryMessage;
import com.huihui.netty.strategy.server.db.SaveMessage;
import com.huihui.netty.strategy.server.db.UpdateMessage;
import io.netty.channel.ChannelHandlerContext;


/**
 * 内容分发策略类
 *
 * @ClassName HandlerStrategy
 * @Description TODO
 * Author huihui
 * Date 15/05/19 上午 10:57
 * Version 1.0
 */
public abstract class HandlerStrategy {

    private SaveMessage saveMessage;

    private QueryMessage queryMessage;

    private UpdateMessage updateMesage;

    private CheckFrom checkFrom;

    private CheckCc checkCc;

    private CheckContent checkContent;

    private CheckTo checkTo;


    //使用该类将消息分发
    public abstract void contentDistribution(ReadMessage message, ChannelHandlerContext ctx);


    public ReturnMessage queryReadMessage(ReadMessage message, Class<?> queryMessage){
        Object obj = StrategyUtils.classToJava(queryMessage);
        if(obj instanceof  QueryMessage){
            this.queryMessage = (QueryMessage) obj;
            return this.queryMessage.queryReadMessage(message);
        }
        return null;
    }

    public ReturnMessage checkTo(ReadMessage message, Class<?> checkTo){
        Object obj = StrategyUtils.classToJava(checkTo);
        if(obj instanceof  CheckTo){
            this.checkTo = (CheckTo) obj;
            return this.checkTo.checkTo(message);
        }
        return null;
    }

    public ReturnMessage checkCc(ReadMessage message,Class<?> checkCc){
        Object obj = StrategyUtils.classToJava(checkCc);
        if(obj instanceof  CheckCc){
            this.checkCc = (CheckCc) obj;
            return this.checkCc.checkCc(message);
        }
        return null;
    }

    public ReturnMessage saveReadMessage(ReadMessage message,Class<?> saveMessage){
        Object obj = StrategyUtils.classToJava(saveMessage);
        if(obj instanceof  SaveMessage){
            this.saveMessage = (SaveMessage) obj;
            return this.saveMessage.saveMessage(message);
        }
        return null;
    }

    public ReturnMessage checkContent(ReadMessage message, Class<?> checkContent){
        Object obj = StrategyUtils.classToJava(checkContent);
        if(obj instanceof  CheckContent){
            this.checkContent = (CheckContent) obj;
            return this.checkContent.checkContent(message);
        }
        return null;
    }

    public ReturnMessage checkFrom(ReadMessage message,Class<?> checkFrom){
        Object obj = StrategyUtils.classToJava(checkFrom);
        if(obj instanceof  CheckFrom){
            this.checkFrom = (CheckFrom) obj;
            return this.checkFrom.checkFrom(message);
        }
        return null;
    }

    public ReturnMessage checkFrom (ReadMessage message){
        this.checkFrom = new CheckFromDefault();
        return this.checkFrom.checkFrom(message);
    }

    public ReturnMessage updateReadMessage(ReadMessage message,Class<?> updateMessage){
        Object obj = StrategyUtils.classToJava(updateMessage);
        if(obj instanceof UpdateMessage){
            this.updateMesage = (UpdateMessage) obj;
            return this.updateMesage.updateMessage(message);
        }
        return null;
    }

    public ReturnMessage checkContentLogin(ReadMessage message, Class<?> login){
        Object obj = StrategyUtils.classToJava(login);
        if(obj instanceof  CheckContent){
            this.checkContent = (CheckContent) obj;
            return this.checkContent.checkContent(message);
        }
        return null;
    }

    public ReturnMessage queryUser(UserPojo userPojo,Class<?> queryUser){
        Object obj = StrategyUtils.classToJava(queryUser);
        if(obj instanceof QueryMessage){
            this.queryMessage = (QueryMessage) obj;
            return this.queryMessage.queryUser(userPojo);
        }
        return null;
    }

    public ReturnMessage saveUser(UserPojo userPojo,Class<?> saveUser){
        Object obj = StrategyUtils.classToJava(saveUser);
        if(obj instanceof SaveMessage){
            this.saveMessage = (SaveMessage) obj;
            return this.saveMessage.saveUser(userPojo);
        }
        return null;
    }

    public ReturnMessage updateUser(UserPojo userPojo,Class<?> updateUser){
        Object obj = StrategyUtils.classToJava(updateUser);
        if(obj instanceof UpdateMessage){
            this.updateMesage = (UpdateMessage) obj;
            return this.updateMesage.updateUser(userPojo);
        }
        return null;
    }

    public ReturnMessage checkJavaCline(ReadMessage message){
        this.checkFrom = new CheckClineFrom();
        return checkFrom.checkFrom(message);
    }

}
