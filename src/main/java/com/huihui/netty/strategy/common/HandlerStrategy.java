package com.huihui.netty.strategy.common;

import com.huihui.netty.pojo.ReadMessage;
import com.huihui.netty.pojo.UserPojo;
import com.huihui.netty.strategy.common.util.StrategyUtils;
import com.huihui.netty.strategy.server.check.CheckCc;
import com.huihui.netty.strategy.server.check.CheckContent;
import com.huihui.netty.strategy.server.check.CheckFrom;
import com.huihui.netty.strategy.server.check.CheckTo;
import com.huihui.netty.strategy.server.check.impl.CheckFromDefault;
import com.huihui.netty.strategy.server.db.QueryMessage;
import com.huihui.netty.strategy.server.db.SaveMessage;
import com.huihui.netty.strategy.server.db.UpdateMesage;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;


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

    private static final Logger LOGGER = LoggerFactory.getLogger(HandlerStrategy.class);

    private SaveMessage saveMessage;

    private QueryMessage queryMessage;

    private UpdateMesage updateMesage;

    private CheckFrom checkFrom;

    private CheckCc checkCc;

    private CheckContent checkContent;

    private CheckTo checkTo;


    //使用该类将消息分发
    public abstract void contentDistribution(ReadMessage message, ChannelHandlerContext ctx);


    public List<ReadMessage> queryReadMessage(ReadMessage message, Class<?> queryMessage){
        Object obj = StrategyUtils.classToJava(queryMessage);
        if(obj instanceof  QueryMessage){
            this.queryMessage = (QueryMessage) obj;
            return this.queryMessage.queryReadMessage(message);
        }
        return null;
    }

    public List<String> checkTo(ReadMessage message,Class<?> checkTo){
        Object obj = StrategyUtils.classToJava(checkTo);
        if(obj instanceof  CheckTo){
            this.checkTo = (CheckTo) obj;
            return this.checkTo.checkTo(message);
        }
        return null;
    }

    public List<String> checkCc(ReadMessage message,Class<?> checkCc){
        Object obj = StrategyUtils.classToJava(checkCc);
        if(obj instanceof  CheckCc){
            this.checkCc = (CheckCc) obj;
            return this.checkCc.checkCc(message);
        }
        return null;
    }

    public boolean saveReadMessage(ReadMessage message,Class<?> saveMessage){
        Object obj = StrategyUtils.classToJava(saveMessage);
        if(obj instanceof  SaveMessage){
            this.saveMessage = (SaveMessage) obj;
            return this.saveMessage.saveMessage(message);
        }
        return false;
    }

    public HashMap<String,Object> checkContent(ReadMessage message, Class<?> checkContent){
        Object obj = StrategyUtils.classToJava(checkContent);
        if(obj instanceof  CheckContent){
            this.checkContent = (CheckContent) obj;
            return this.checkContent.checkContent(message);
        }
        return null;
    }

    public boolean checkFrom(ReadMessage message,Class<?> checkFrom){
        Object obj = StrategyUtils.classToJava(checkFrom);
        if(obj instanceof  CheckFrom){
            this.checkFrom = (CheckFrom) obj;
            return this.checkFrom.checkFrom(message);
        }
        return false;
    }

    public boolean checkFrom (ReadMessage message){
        this.checkFrom = new CheckFromDefault();
        return this.checkFrom.checkFrom(message);
    }

    public boolean updateReadMessage(ReadMessage message,Class<?> updateMessage){
        Object obj = StrategyUtils.classToJava(updateMessage);
        if(obj instanceof  UpdateMesage){
            this.updateMesage = (UpdateMesage) obj;
            return this.updateMesage.updateMessage(message);
        }
        return false;
    }

    public UserPojo checkContentLogin(ReadMessage message, Class<?> login){
        Object obj = StrategyUtils.classToJava(login);
        if(obj instanceof  CheckContent){
            this.checkContent = (CheckContent) obj;
            return this.checkContent.login(message);
        }
        return null;
    }


}
