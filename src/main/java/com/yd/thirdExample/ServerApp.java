package com.yd.thirdExample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.io.IOException;

/**
 * @author yd
 */
public class ServerApp {

    public static void main(String[] args) throws InterruptedException, IOException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup wokerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, wokerGroup);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(
                        new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter()),
                        new StringDecoder(CharsetUtil.UTF_8),
                        new StringEncoder(CharsetUtil.UTF_8),
                        new ServerMsgHandler()
                );
            }
        });
        ChannelFuture channelFuture = serverBootstrap.bind(12345).sync();
        System.out.println("serverApp.....");


        channelFuture.channel().closeFuture().sync();
    }

}
