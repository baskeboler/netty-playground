package com.victor.commandserver.server.handlers;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;

import org.apache.log4j.Logger;

import com.victor.commandserver.server.exceptions.ComandoUnknownException;

@Sharable
public class CustomExceptionHandler extends ChannelHandlerAdapter {

	private static final Logger LOGGER = Logger.getLogger(CustomExceptionHandler.class);
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		// TODO Auto-generated method stub
		if (cause instanceof DecoderException) {
			if (cause.getCause() instanceof ComandoUnknownException) {
				LOGGER.error("Comando Desconocido: " + cause.getCause().getMessage());
				ctx.writeAndFlush("Comando desconocido: " + cause.getCause().getMessage() + "\n");
			} else {
				LOGGER.error("Error leyendo comando: " + cause.getMessage());
				ctx.writeAndFlush("No entiendo nada.\n");
			}
			
		}
		else {
			LOGGER.error("Error Desconocido: " + cause.getMessage() );
			ctx.writeAndFlush("Error desconocido."+ "\n");
		}
	}
}
