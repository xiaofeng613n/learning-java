package netty.httpserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;

import java.util.concurrent.ExecutorService;

/**
 * Created by xiaofeng on 2018/7/6
 * Description:
 */
public class MiniHttpServer {

    private EventLoopGroup bossThreadPool;
    private EventLoopGroup workerThreadPool;

    private ServerBootstrap bootstrap;

    private String ip;
    private int port;
    private int bossNum;
    private int workerNum;

    public MiniHttpServer(String ip,int port,int bossNum,int workNum){
        this.ip = ip;
        this.port = port;
        this.bossNum = bossNum;
        this.workerNum = workNum;
    }
}