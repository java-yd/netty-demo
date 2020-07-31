package com.yd.firstExample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup);
        b.channel(NioServerSocketChannel.class);
        b.childHandler(new ChannelInitializer<SocketChannel>() {

            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline()
                        .addLast("httpServerCodec", new HttpServerCodec())
                .addLast("msgHandler", new MsgHandler());

            }
        });

        try {
            ChannelFuture future = b.bind(12345).sync();
            future.channel().closeFuture();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
