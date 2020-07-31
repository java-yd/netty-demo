package com.yd.secondExample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author yd
 */
public class ClientMsgHandler extends SimpleChannelInboundHandler<String> {

    private static int count = 0;

    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "-客户端接收到的消息：" + msg);

        ctx.writeAndFlush("访问次数：" + (++count));
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        ctx.writeAndFlush("哈哈   我张三又回来了");
    }
}
