package com.victor.commandserver.server;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;

import java.util.logging.Level;
import java.util.logging.Logger;

@Sharable
public class CommandServerHandler extends ChannelHandlerAdapter {
	private static Logger LOG = Logger.getLogger(CommandServerHandler.class
			.getName());

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		LOG.log(Level.WARNING, "Exception caught: " + cause.getMessage());
		ctx.write(cause.getMessage());
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Channel active");
		ctx.writeAndFlush("Bienvenido!\n");
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Channel read complete. flushing.");
		ctx.flush();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		LOG.log(Level.INFO, "Channel READ!");
		String s = (String) msg;
		s = s.trim().toLowerCase();
		if (s.equals("ping")) {
			LOG.log(Level.INFO, "Ping message received");
			ctx.writeAndFlush("pong\n");
			return;
		} else if (s.equals("chau")) {
			LOG.log(Level.INFO, "Chau message received. closing connection.");
			ctx.writeAndFlush("byebye\n").addListener(
					ChannelFutureListener.CLOSE);
		} else {
			LOG.log(Level.WARNING, "No entiendo");
			ctx.writeAndFlush("no entiendo \n");
		}
	}
}
