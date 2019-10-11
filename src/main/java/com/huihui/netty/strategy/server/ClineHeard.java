package com.huihui.netty.strategy.server;

import com.huihui.netty.pojo.ReadMessage;
import com.huihui.netty.strategy.common.HandlerStrategy;
import io.netty.channel.ChannelHandlerContext;

/**
 * @className: ClineHeard
 * @description: java客户端心跳
 * @author: huihui
 * @createDate: 2019-09-24
 * @version: 1.0
 */
public class ClineHeard extends HandlerStrategy {
    @Override
    public void contentDistribution(ReadMessage message, ChannelHandlerContext ctx) {

    }
}
