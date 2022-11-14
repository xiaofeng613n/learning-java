package netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xiaofeng on 2018/4/20
 * Description:
 */
public class ChatHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger LOG = LoggerFactory.getLogger(ChatHandler.class);
    @Getter
    @Setter
    private Channel channel;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOG.info("channelActive");
        super.channelActive(ctx);
        ctx.pipeline().write()
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOG.warn("channelInactive");
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        LOG.error("exceptionCaught [{}]",cause);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        this.channel = ctx.channel();
        System.out.println("recieve a ...from " + channel.remoteAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("recieve a message:" + msg);
       ctx.writeAndFlush()

    }

    public Channel getChannel() {
        return channel;
    }
}