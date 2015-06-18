package com.victor.commandserver.server.handlers;

import org.springframework.stereotype.Component;

import com.victor.commandserver.server.commands.ComandoEcho;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;

@Sharable
@Component
public class ComandoEchoHandler extends ChannelHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		if (msg instanceof ComandoEcho) {
			String str = ((ComandoEcho) msg).getMsg();
			str = str.replaceFirst("echo ", "");
			ctx.writeAndFlush("Echo: " + str + "\n");
		} else
			super.channelRead(ctx, msg);
	}

}
