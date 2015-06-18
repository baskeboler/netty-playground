package com.victor.commandserver.server.handlers;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.victor.commandserver.server.commands.ComandoSumar;

@Sharable
@Component
public class CommandServerHandler extends ChannelHandlerAdapter {
	private static Logger LOG = Logger.getLogger(CommandServerHandler.class
			.getName());

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
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
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		// TODO Auto-generated method stub
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent idleState = (IdleStateEvent) evt;
			switch (idleState.state()) {
			case READER_IDLE: {
				ctx.writeAndFlush("Ipa bo? Que onda?\n");
			}
			default:
				break;
			}
		} else {
			super.userEventTriggered(ctx, evt);
		}
	}
}
