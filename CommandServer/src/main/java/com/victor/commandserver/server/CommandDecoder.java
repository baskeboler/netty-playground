package com.victor.commandserver.server;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.Arrays;
import java.util.List;

import com.victor.commandserver.server.commands.ComandoEcho;
import com.victor.commandserver.server.commands.ComandoFibonacci;
import com.victor.commandserver.server.commands.ComandoFortune;
import com.victor.commandserver.server.commands.ComandoGetVar;
import com.victor.commandserver.server.commands.ComandoSalir;
import com.victor.commandserver.server.commands.ComandoSetVar;
import com.victor.commandserver.server.commands.ComandoSumar;
import com.victor.commandserver.server.exceptions.ComandoUnknownException;

@Sharable
public class CommandDecoder extends MessageToMessageDecoder<String> {

	@Override
	protected void decode(ChannelHandlerContext ctx, String msg,
			List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		String s = msg.trim().toLowerCase();
		String[] palabras = s.split(" ");
		if (palabras[0].equals("sumar")) {
			int a = Integer.valueOf(palabras[1]);
			int b = Integer.valueOf(palabras[2]);

			out.add(new ComandoSumar(a, b));
		} else if (palabras[0].equals("chau")) {
			out.add(new ComandoSalir());
		} else if (palabras[0].equals("fibonacci")) {
			int a = Integer.valueOf(palabras[1]);
			out.add(new ComandoFibonacci(a));
		} else if (palabras[0].equals("fortune")) {
			out.add(new ComandoFortune());
		} else if (palabras[0].equals("echo")) {
			out.add(new ComandoEcho(msg));
		} else if (palabras[0].equals("var")) {
			if (palabras[1].equals("set")) {
				String[] values = Arrays.copyOfRange(palabras, 3, palabras.length);
				String val = String.join(" ", values);
				out.add(new ComandoSetVar(palabras[2], val));
			} else if (palabras[1].equals("get")) {
				out.add(new ComandoGetVar(palabras[2]));
			}
		} else {
			throw new ComandoUnknownException(palabras[0]);
		}
	}
}
