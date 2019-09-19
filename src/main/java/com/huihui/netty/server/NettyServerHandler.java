package com.huihui.netty.server;

import com.alibaba.fastjson.JSONObject;
import com.huihui.netty.common.NettyCliention;
import com.huihui.netty.common.ProConfig;
import com.huihui.netty.common.ProFunctionName;
import com.huihui.netty.common.SuccessCode;
import com.huihui.netty.pojo.ReadMessage;
import com.huihui.netty.pojo.ReturnMessage;
import com.huihui.netty.strategy.common.Context;
import com.huihui.netty.strategy.common.HandlerFactory;
import com.huihui.netty.strategy.common.HandlerStrategy;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.bcel.BcelGenericSignatureToTypeXConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class NettyServerHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServerHandler.class);

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static final long max_channelGroup = 2000;


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (null != msg && msg instanceof FullHttpRequest) {

            FullHttpRequest request = (FullHttpRequest) msg;
            String uri = request.uri();
            String origin = request.headers().get("Origin");
            if (null == origin) {
                ctx.close();
            } else {
                if (null != uri && uri.contains(ProConfig.NETTY_URL.getCode()) && uri.contains("?")) {
                    String[] uriArray = uri.split("\\?");
                    if (null != uriArray && uriArray.length > 1) {
                        String[] paramsArray = uriArray[1].split("=");
                        if (null != paramsArray && paramsArray.length > 1) {
                            if (StringUtils.equals(paramsArray[0], ProConfig.LOGIN_PAR.getCode())) {
                                ReturnMessage successYN = null;
                                if (StringUtils.isNotEmpty(paramsArray[1])) {
                                    successYN = login(paramsArray[1]);
                                }
                                if (successYN != null && successYN.getCode() != SuccessCode.SUCCESS_LOGIN.getCode()) {
                                    ctx.close();
                                } else {
                                    NettyCliention.ONLINE_USER.put(paramsArray[1], ctx);
                                    NettyCliention.returnMessage(successYN, ctx,ProFunctionName.USER_LOGIN.getMessage());
                                }
                            } else {
                                LOGGER.info("NettyServerHandler#channelRead参数传递错误！" + uriArray[1]);
                                ctx.close();
                            }
                        }
                    }
                    request.setUri(ProConfig.NETTY_URL.getCode());
                } else {
                    ctx.close();
                }
            }

        }
        super.channelRead(ctx, msg);
    }

    /**
     * 执行统一认证逻辑
     *
     * @param s
     * @return
     */
    private ReturnMessage login(String s) {
        LOGGER.info("NettyServerHandler#channelRead#login入参" + s);
        UserLogin userLogin = new UserLogin();
        return userLogin.login(s);
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) {
        ReadMessage message = null;
        if (msg != null) {
            try {
                message = JSONObject.parseObject(((TextWebSocketFrame) msg).text(), ReadMessage.class);
            } catch (Exception e) {
                LOGGER.error("NettyServerHandler#channelRead0消息解析异常", e);
            }
            LOGGER.info("NettyServerHandler#channelRead0收到客户端消息" + JSONObject.toJSONString(message));
            Context context = new Context();
            HandlerFactory factory = new HandlerFactory();
            HandlerStrategy strategy = (HandlerStrategy) factory.createHandlerStraategy(message.getType());
            context.setHandlerStrategy(strategy);
            context.result(message, ctx);
        } else {
            ReturnMessage returnMessage = new ReturnMessage(message.getFrom(), message.getType());
            NettyCliention.returnMessage(returnMessage, ctx, "NettyServerHandler#channelRead0");
        }

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
        String clientIp = insocket.getAddress().getHostAddress();
        LOGGER.info("NettyServerHandler#channelActive收到客户端[ip:" + clientIp + "]连接");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOGGER.error("NettyServerHandler#exceptionCaught连接异常" + cause);
        cause.printStackTrace();
        ctx.channel().writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(cause)));
    }

    /**
     * 检测到客户端下线后进行清除
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //断开连接后的操作
        NettyCliention.cleanCliention(ctx);
    }

    /**
     * 服务端自动判断是否已经登录
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (channelGroup.size() > max_channelGroup) {
            LOGGER.info("NettyServerHandler#userEventTriggered连接数大于" + max_channelGroup + "将连接删除");
            if (evt instanceof IdleStateEvent) {
                IdleState state = ((IdleStateEvent) evt).state();
                if (state == IdleState.READER_IDLE) {
                    LOGGER.info("NettyServerHandler#userEventTriggered空闲连接清除前的连接池" + JSONObject.toJSONString(NettyCliention.ONLINE_USER));
                    NettyCliention.cleanCliention(ctx);
                    LOGGER.info("NettyServerHandler#userEventTriggered清除后的连接池连接" + JSONObject.toJSONString(NettyCliention.ONLINE_USER));
                }
            }
            ctx.close();
        }
    }

}
