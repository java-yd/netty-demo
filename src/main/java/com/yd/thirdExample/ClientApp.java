package com.yd.thirdExample;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author yd
 */
public class ClientApp {

    public static void main(String[] args) throws InterruptedException, IOException {
        EventLoopGroup eventExecutors = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventExecutors);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(
                        new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter()),
                        new StringDecoder(CharsetUtil.UTF_8),
                        new StringEncoder(CharsetUtil.UTF_8),
                        new ClientMsgHandler()
                );
            }
        });

        ChannelFuture channelFuture = bootstrap.connect(args[0], 12345).sync();
        System.out.println("clientApp......");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        Channel channel = channelFuture.channel();

        for (;;){
            channel.writeAndFlush(bufferedReader.readLine() + "\r\n");
        }
    }

}
