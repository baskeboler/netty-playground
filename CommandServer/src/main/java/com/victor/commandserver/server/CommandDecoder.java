package com.victor.commandserver.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class CommandDecoder extends MessageToMessageDecoder<String> {

	@Override
	protected void decode(ChannelHandlerContext ctx, String msg,
			List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		String s = msg.trim().toLowerCase();
		String[] palabras = s.split(" ");
		if (palabras.length >= 3) {
			if (palabras[0].equals("sumar")) {
				int a = Integer.valueOf(palabras[1]);
				int b = Integer.valueOf(palabras[2]);
				
				out.add(new ComandoSumar(a, b));
			}
		}
	}

}
