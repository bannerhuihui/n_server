package com.huihui.netty.server;

import com.huihui.netty.util.SpringContextUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class NettyServer implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServer.class);

    /**
     * 端口号
     */
    @Value("${netty.port}")
    private int port = 13145;

    @Autowired
    private NettyServerInitializer nettyServerInitializer = SpringContextUtil.getBean("nettyServerInitializer");

    /**
     * 启动服务器方法
     *
     * @param
     */
    @Override
    public void run() {
        //更新用户表

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
            serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);
            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            serverBootstrap.childHandler(nettyServerInitializer);
            // 绑定端口,开始接收进来的连接
            ChannelFuture channelFuture1 = serverBootstrap.bind(port).sync();
            LOGGER.info("netty服务启动: [port:" + port + "]");
            // 等待服务器socket关闭
            channelFuture1.channel().closeFuture().sync();
        } catch (Exception e) {
            LOGGER.error("netty服务启动异常-" + e.getMessage());
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }



}
