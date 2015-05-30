package com.victor.commandserver.server;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;

@Sharable
public class ComandoFibonacciHandler extends ChannelHandlerAdapter{

	private int fib(int n) {
		if (n <= 0) {
			return 0;
		} else if (n ==1) { 
			return 1;
		} else return fib(n-1) + fib(n-2);
	}
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		if (msg instanceof ComandoFibonacci) {
			ctx.channel().eventLoop().execute(() -> {
				ComandoFibonacci f = (ComandoFibonacci) msg;
				int res = fib(f.getA());
				ctx.writeAndFlush("resultado = " + res + "/n");
			});
		} else super.channelRead(ctx, msg);
	}
}
