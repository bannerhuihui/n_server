package com.huihui.netty.server;

import com.huihui.netty.common.Log;
import com.huihui.netty.server.util.SslUtil;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import java.util.concurrent.TimeUnit;

@Component
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {

    private static final String type = "JKS";

    @Value("${netty.action}")
    private String path;

    @Value("${netty.password}")
    private String password;

    /**
     * 初始化channel
     */
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        SSLContext sslContext = SslUtil.createSSLContext(type,path,password);
        SSLEngine engine = sslContext.createSSLEngine();
        engine.setUseClientMode(false);
        ChannelPipeline pipeline = ch.pipeline();
        //TimeUnit.DAYS         日
        //TimeUnit.HOURS        时
        //TimeUnit.MINUTES      分
        //TimeUnit.SECONDS      秒
        //TimeUnit.MILLISECONDS 毫秒
        pipeline.addLast(new IdleStateHandler(1,1,1, TimeUnit.MINUTES));
        pipeline.addLast(new SslHandler(engine));
        //websocket协议本身是基于http协议的，所以这边也要使用http解编码器
        pipeline.addLast(new HttpServerCodec());
        //http压缩处理  1产生最快的压缩，9产生最佳压缩。 0表示不压缩。 默认压缩级别为6。
        pipeline.addLast(new HttpContentCompressor(1));
        //以块的方式来写的处理器
        pipeline.addLast(new ChunkedWriteHandler());
        //netty是基于分段请求的，HttpObjectAggregator的作用是将请求分段再聚合,参数是聚合字节的最大长度
        pipeline.addLast(new HttpObjectAggregator(8192));
        //ws://localhost:9999/ws
        pipeline.addLast(new WebSocketServerCompressionHandler());
        pipeline.addLast(new LineBasedFrameDecoder(1024));
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new NettyServerHandler());
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws",null,true,10485760));

    }
}
