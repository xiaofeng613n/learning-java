package xiaofeng;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by xiao on 2018/7/5.
 */
public class ClientHandler extends CustomHeartbeatHandler {
	public ClientHandler() {
		super("client");
	}

	@Override
	protected void handleData(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
		byte[] data = new byte[byteBuf.readableBytes() - 5];
		byteBuf.skipBytes(5);
		byteBuf.readBytes(data);
		String content = new String(data);
		System.out.println(name + " get content: " + content);
	}

	@Override
	protected void handleAllIdle(ChannelHandlerContext ctx) {
		super.handleAllIdle(ctx);
		sendPingMsg(ctx);
	}
}