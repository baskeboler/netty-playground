package com.victor.commandserver.server.handlers;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.util.AttributeKey;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.victor.commandserver.server.commands.ComandoGetVar;
import com.victor.commandserver.server.commands.ComandoSetVar;

@Component
@Sharable
public class ComandoVarHandler extends ChannelHandlerAdapter {
	private static final Logger LOGGER = Logger
			.getLogger(ComandoVarHandler.class);
	private static final AttributeKey<Map<String, String>> channelVarsKey = AttributeKey
			.valueOf("channel.vars.key");

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		LOGGER.info("Creating Var Map");
		ctx.attr(channelVarsKey).set(new HashMap<String, String>());
		super.channelActive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		if (msg instanceof ComandoSetVar) {
			ComandoSetVar c = (ComandoSetVar) msg;
			ctx.attr(channelVarsKey).get().put(c.getVarName(), c.getVarValue());
			LOGGER.info("Setting " + c.getVarName() + " = " + c.getVarValue());
			StringBuilder sb = new StringBuilder();
			sb.append(c.getVarName()).append(" = ").append(c.getVarValue())
					.append("\n");
			ctx.writeAndFlush(sb.toString());
		} else if (msg instanceof ComandoGetVar) {
			ComandoGetVar c = (ComandoGetVar) msg;
			LOGGER.info("Getting " + c.getVarName());
			String value = ctx.attr(channelVarsKey).get().get(c.getVarName());
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
