package com.victor.commandserver.server.handlers;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.victor.commandserver.services.AttributesService;

@Sharable
@Component
public class AttributesHandler extends ChannelHandlerAdapter{

	@Autowired
	private AttributesService attributesService;
	
	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		attributesService.register(ctx.channel().id(), ctx);
		super.channelRegistered(ctx);
	}
	
	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		attributesService.unregister(ctx.channel().id());
		super.channelUnregistered(ctx);
	}

	public AttributesService getAttributesService() {
		return attributesService;
	}

	public void setAttributesService(AttributesService attributesService) {
		this.attributesService = attributesService;
	}
}
