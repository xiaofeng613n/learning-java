package netty.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * Created by xiaofeng on 2018/4/10
 * Description:
 */
public class NettyChatServer implements Runnable{

    private static final Logger LOG = LoggerFactory.getLogger(NettyChatServer.class);

    @Override
    public void run() {
        start();
    }

    public void send(String line){
        final Channel channel = chatHandler.getChannel();
        channel.writeAndFlush(line);
    }

    private ChatHandler chatHandler = new ChatHandler();
    private ChatChannelInitializer channelInitializer = new ChatChannelInitializer(chatHandler);


    public void start(){
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(2);

        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(channelInitializer)
                    .childOption(ChannelOption.SO_BACKLOG.SO_KEEPALIVE, true);

            ChannelFuture f = server.bind(8888).addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if( future.isSuccess()){
                        LOG.info("服务器启动成功");
                    } else {
                        LOG.error("服务器启动失败");
                    }
                }
            }).sync();

            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            LOG.error("InterruptedException",e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        NettyChatServer server = new NettyChatServer();
        Thread thread = new Thread(server);
        thread.start();
        Scanner scanner = new Scanner(System.in);
        while (true){
            String line = scanner.nextLine();
            server.send(line);
        }
    }
}