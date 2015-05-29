package com.victor.commandserver.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;

public class CommandServerInitializer extends ChannelInitializer<NioSocketChannel> {

	private static final int MAX_LENGTH = 1024;

	@Override
	protected void initChannel(NioSocketChannel ch) throws Exception {
		// TODO Auto-generated method stub
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast("logger-pipeline", new LoggingHandler());
		pipeline.addLast("framer", new LineBasedFrameDecoder(MAX_LENGTH, true, true));
		pipeline.addLast("string-decoder", new StringDecoder());
		pipeline.addLast("string-encoder", new StringEncoder());
		pipeline.addLast("comando-decoder", new CommandDecoder());
		pipeline.addLast("handler", new CommandServerHandler());
		
	}

}
