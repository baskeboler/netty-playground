package com.victor.commandserver.server.handlers;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

@Sharable
public class PromptPrinterHandler extends ChannelHandlerAdapter {
	private static final String PROMPT = "$ > ";

	@Override
	public void write(ChannelHandlerContext ctx, Object msg,
			ChannelPromise promise) throws Exception {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(msg).append("\n");
		sb.append(PROMPT);
		super.write(ctx, sb.toString(), promise);
	}
}
