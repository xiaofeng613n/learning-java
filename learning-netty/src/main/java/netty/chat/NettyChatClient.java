package netty.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by xiaofeng on 2018/4/10
 * Description:
 */
public class NettyChatClient implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(NettyChatClient.class);
    @Override
    public void run() {
        start();
    }

    private ChatHandler chatHandler = new ChatHandler();
    private ChatChannelInitializer channelInitializer = new ChatChannelInitializer(chatHandler);

    private Channel channel;

    public void start(){
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);

        Bootstrap client = new Bootstrap();
        client.group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(channelInitializer);
        try {
            ChannelFuture channelFuture = client.connect("127.0.0.1",8888).addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if( future.isSuccess() ){
                        LOG.info("连接到服务器成功");
                    } else {
                        LOG.error("连接到服务器失败");
                    }
                }
            }).sync();

            this.channel = channelFuture.channel();

            this.channel.closeFuture().addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    if( future.isSuccess()){
                        System.out.println();
                    } else {
                        System.out.println();
                    }
                }
            });

            workerGroup.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    channel.writeAndFlush("heartbeat from client" + System.currentTimeMillis()/1000);
                }
            },1,5, TimeUnit.SECONDS);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println();
        }
    }

    public void send(String line){
        channel.writeAndFlush(line);
    }
    public static void main(String[] args) {
        NettyChatClient client = new NettyChatClient();
        Thread thread = new Thread(client);
        thread.start();
        Scanner scanner = new Scanner(System.in);
        while (true){
            String line = scanner.nextLine();
            client.send(line);
        }
    }
}