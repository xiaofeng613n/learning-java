package com.xf;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


import java.util.Random;

/**
 * @Auther: xiaofeng
 * @Date: 2019-08-19 23:35
 * @Description:
 */
public class Client {
    public static void main(String[] args) {
        NioEventLoopGroup workGroup = new NioEventLoopGroup(4);
        Random random = new Random(System.currentTimeMillis());
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap
                    .group(workGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
//                            ChannelPipeline p = socketChannel.pipeline();
//                            p.addLast(new IdleStateHandler(0, 0, 5));
//                            p.addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4, -4, 0));
//                            p.addLast(new ClientHandler());
                        }
                    });

            Channel ch = bootstrap.remoteAddress("127.0.0.1", 12345).connect().sync().channel();

            ByteBuf buf = ch.alloc().buffer();
            byte[] bytes = new byte[1024*1024 * 10];
             buf.writeBytes(bytes);
            ch.writeAndFlush(buf);
            Thread.sleep(Integer.MAX_VALUE);

//            for (int i = 0; i < 10; i++) {
//                String content = "client msg " + i;
//                ByteBuf buf = ch.alloc().buffer();
//                buf.writeInt(5 + content.getBytes().length);
//                buf.writeByte(CustomHeartbeatHandler.CUSTOM_MSG);
//                buf.writeBytes(content.getBytes());
//                ch.writeAndFlush(buf);
//
//                Thread.sleep(random.nextInt(20000));
//            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            workGroup.shutdownGracefully();
        }
    }
}
