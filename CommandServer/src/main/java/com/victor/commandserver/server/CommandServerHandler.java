package com.victor.commandserver.server;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.ReadTimeoutException;

import java.util.logging.Level;
import java.util.logging.Logger;

@Sharable
public class CommandServerHandler extends ChannelHandlerAdapter {
	private static Logger LOG = Logger.getLogger(CommandServerHandler.class
			.getName());

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Channel READ!");
		if (msg instanceof ComandoSumar) {
			ctx.channel().eventLoop().execute(() -> {
				ComandoSumar c = (ComandoSumar) msg;
				int res = c.getA() + c.getB();
				ctx.writeAndFlush("Resultado = " + res + "\n");
			});
		} else {
			super.channelRead(ctx, msg);
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		if (cause instanceof ReadTimeoutException) {
			ctx.writeAndFlush("Y bo que onda?\n");
		} else {
			super.exceptionCaught(ctx, cause);
		}
	}
}
