package com.victor.commandserver.server.handlers;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import org.apache.log4j.Logger;

@Sharable
public class WelcomeMessageHandler extends ChannelHandlerAdapter {
	
	private static final String LINE_SEPARATOR = "==============================\n";
	private static final Logger LOGGER = Logger.getLogger(WelcomeMessageHandler.class);
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append(LINE_SEPARATOR);
		sb.append("Bienvenido a Comando Server!\n");
		sb.append(LINE_SEPARATOR);

		sb.append("Comandos disponibles: \n");

		sb.append("             echo <mensaje>\n");
		sb.append("             <var> = <number>\n");
		sb.append("             :<var>\n");
		sb.append("             sumar <numero> <numero>\n");
		sb.append("             fortune\n");
		sb.append("             fibonacci <numero>\n");
		sb.append("             chau\n");
		sb.append(LINE_SEPARATOR);
		ctx.writeAndFlush(sb.toString());
		ctx.pipeline().remove(this);
		super.channelActive(ctx);
	}
	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		LOGGER.info("Handler removed.");
		super.handlerRemoved(ctx);
	}
}
