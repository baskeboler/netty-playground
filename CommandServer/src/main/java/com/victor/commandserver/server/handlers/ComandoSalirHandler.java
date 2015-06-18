package com.victor.commandserver.server.handlers;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import org.springframework.stereotype.Component;

import com.victor.commandserver.server.commands.ComandoSalir;

@Sharable
@Component
public class ComandoSalirHandler extends ChannelHandlerAdapter{
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		if (msg instanceof ComandoSalir) {
			ctx.writeAndFlush("Chau!\n").addListener(ChannelFutureListener.CLOSE);
		} else super.channelRead(ctx, msg);
	}
	
}
