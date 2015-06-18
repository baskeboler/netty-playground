package com.victor.commandserver.server.handlers;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.victor.commandserver.server.commands.ComandoFortune;
import com.victor.commandserver.services.FortuneService;

@Sharable
@Component
public class ComandoFortuneHandler extends ChannelHandlerAdapter {

	@Autowired
	private FortuneService fortuneService;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		if (msg instanceof ComandoFortune) {
			ctx.channel().eventLoop().execute(() -> {
				String fortune = fortuneService.getCookie();
				ctx.writeAndFlush(fortune);
				
			});
		} else {
			super.channelRead(ctx, msg);
		}
	}
	
	public FortuneService getFortuneService() {
		return fortuneService;
	}
	
	public void setFortuneService(FortuneService fortuneService) {
		this.fortuneService = fortuneService;
	}
}
