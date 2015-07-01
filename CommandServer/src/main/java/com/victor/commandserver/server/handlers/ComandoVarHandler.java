package com.victor.commandserver.server.handlers;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.victor.commandserver.server.commands.ComandoGetVar;
import com.victor.commandserver.server.commands.ComandoSetVar;
import com.victor.commandserver.services.VariablesService;

@Component
@Sharable
public class ComandoVarHandler extends ChannelHandlerAdapter {
	private static final Logger LOGGER = Logger
			.getLogger(ComandoVarHandler.class);
	
	@Autowired
	private VariablesService variablesService;

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		LOGGER.info("Creating Var Map");
		variablesService.register(ctx.channel().id());
		// TODO Auto-generated method stub
		super.channelRegistered(ctx);
	}
	
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		ChannelId id = ctx.channel().id();
		if (msg instanceof ComandoSetVar) {
			ComandoSetVar c = (ComandoSetVar) msg;
			variablesService.setVariable(id, c.getVarName(), c.getVarValue());
			LOGGER.info("Setting " + c.getVarName() + " = " + c.getVarValue());
			StringBuilder sb = new StringBuilder();
			sb.append(c.getVarName()).append(" = ").append(c.getVarValue())
					.append("\n");
			ctx.writeAndFlush(sb.toString());
		} else if (msg instanceof ComandoGetVar) {
			ComandoGetVar c = (ComandoGetVar) msg;
			LOGGER.info("Getting " + c.getVarName());
			String value = variablesService.getVariable(id, c.getVarName());//ctx.attr(channelVarsKey).get().get(c.getVarName());
			if (value != null) {
				StringBuilder sb = new StringBuilder();
				sb.append(c.getVarName()).append(" = ").append(value)
						.append("\n");
				ctx.writeAndFlush(sb.toString());
			} else {
				StringBuilder sb = new StringBuilder();
				sb.append(c.getVarName()).append(" not set.\n");
				ctx.writeAndFlush(sb.toString());
			} 

		} else {
			super.channelRead(ctx, msg);

		}
	}
}
