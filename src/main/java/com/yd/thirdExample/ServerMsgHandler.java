package com.yd.thirdExample;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Iterator;

/**
 * @author yd
 */
public class ServerMsgHandler extends SimpleChannelInboundHandler<String> {

    private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        Iterator<Channel> channelIterator = channelGroup.iterator();
        while (channelIterator.hasNext()){
            Channel ch = channelIterator.next();
            if (ch != null && ch.isActive()){
                if (ch == channel)
                    ch.writeAndFlush("[自己]：" + msg + "\r\n");
                else
                    ch.writeAndFlush(channel.remoteAddress() + "-[其他用户]：" + msg + "\r\n");
            }
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("新用户：" + channel.remoteAddress() + "加入了\r\n");
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        channelGroup.writeAndFlush("用户：" + ctx.channel().remoteAddress() + "离开了\r\n");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("用户：" + ctx.channel().remoteAddress() + "上线了\r\n");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("用户：" + ctx.channel().remoteAddress() + "下线了\r\n");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }
}
