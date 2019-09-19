package com.huihui.netty.common;

import com.alibaba.fastjson.JSONObject;
import com.huihui.netty.pojo.ReturnMessage;
import com.huihui.netty.pojo.UserPojo;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.json.JsonObjectDecoder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Netty通道管理
 */
public class NettyCliention {

    public static final String[] _TAG = {"APP", "PZB", "KF", "GZH", "XCX"};

    private static NettyCliention _this = null;

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyCliention.class);

    public static Map<String, ChannelHandlerContext> ONLINE_USER = new ConcurrentHashMap<>();

    // 单例线程锁
    private static Object lock = new Object();

    // 操作客户端线程锁
    private static Object ONLINE_USER_LOCK = new Object();

    // 用户登录状态
    public static Map<String, Integer> SWITCHES = new ConcurrentHashMap<>();

    public static Map<String, JSONObject> HEARD_STATE = new HashMap<>();

    public static Map<Integer, BitSet> USER_STATE = new HashMap<>();


    /**
     * 单例模式对象调用
     *
     * @return
     */
    public static NettyCliention newNettyStaticClazz() {
        if (_this == null) {
            synchronized (lock) {
                if (_this == null) {
                    _this = new NettyCliention();
                }
            }
        }
        return _this;
    }

    static {
        for (int i = 0; i < _TAG.length; i++) {
            SWITCHES.put(_TAG[i], i);
        }
    }

    private NettyCliention() {
    }


    /**
     * 发送消息到客户端
     *
     * @param returnMessage
     * @param ctx
     */
    public static void returnMessage(ReturnMessage returnMessage, ChannelHandlerContext ctx, String tag) {
        if (returnMessage != null && ctx != null) {
            ctx.channel().writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(returnMessage)));
            LOGGER.info("功能：" + tag + "返回内容：" + JSONObject.toJSONString(returnMessage));
        }
    }

    /**
     * 从内存中清除ctx连接
     *
     * @param ctx
     */
    public static void cleanCliention(ChannelHandlerContext ctx) {
        String contextId = "";
        synchronized (ONLINE_USER_LOCK) {
            for (Iterator<Map.Entry<String, ChannelHandlerContext>> iterator = ONLINE_USER.entrySet().iterator(); iterator.hasNext(); ) {
                Map.Entry<String, ChannelHandlerContext> next = iterator.next();
                if (ctx == next.getValue()) {
                    contextId = next.getKey();
                    break;
                }
            }
            if (StringUtils.isNotEmpty(contextId)) {
                ONLINE_USER.remove(contextId);
            }
        }

    }

    /**
     * 查询所有的在线客户端
     *
     * @return
     */
    public static List<ChannelHandlerContext> queryAllChannel() {
        List<ChannelHandlerContext> allChannel = new ArrayList<>();
        synchronized (ONLINE_USER_LOCK) {
            for (Iterator<Map.Entry<String, ChannelHandlerContext>> iterator = ONLINE_USER.entrySet().iterator(); iterator.hasNext(); ) {
                Map.Entry<String, ChannelHandlerContext> next = iterator.next();
                allChannel.add(next.getValue());
            }
        }
        return allChannel;
    }


    /**
     * 更新用户的在线状态
     *
     * @param userId
     * @param tag
     * @param flag
     */
    public static void changeUserState(int userId, String tag, boolean flag) {
        if (USER_STATE.get(userId) == null) {
            USER_STATE.put(userId, new BitSet());
        }
        BitSet userState = USER_STATE.get(userId);
        userState.set(SWITCHES.get(tag), flag);
        USER_STATE.put(userId, userState);
    }


    /**
     * 查询在线的用户信息
     *
     * @return
     */
    public static List<UserPojo> queryOnlineUser() {
        //TODO 返回在线的用户信息
        return null;
    }
}
