package com.xf;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Auther: xiaofeng
 * @Date: 2019-08-19 23:28
 * @Description:
 */
public class UploadHandler extends ChannelInboundHandlerAdapter {


    private int count = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //super.channelRead(ctx, msg)
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        System.out.println(bytes.length);
        count += bytes.length;
        byteBuf.readBytes(bytes);
        System.out.println(new String(bytes));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //super.channelReadComplete(ctx);
        System.out.println(count);
        System.out.println("channelReadComplete");
    }
}
