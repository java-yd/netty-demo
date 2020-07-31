package com.yd.fourthExample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author yd
 */
public class MyIdleHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("userEventTriggered");
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            switch (event.state()){
                case WRITER_IDLE:
                    System.out.println("写超时");
                    break;
                case READER_IDLE:
                    System.out.println("读超时");
                    break;
                case ALL_IDLE:
                    System.out.println("读写超时");
                    break;
                default:
                    System.out.println("未识别的状态" + event.state());
            }
        }

    }
}
