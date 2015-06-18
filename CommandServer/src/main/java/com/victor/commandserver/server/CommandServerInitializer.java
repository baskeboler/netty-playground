package com.victor.commandserver.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.logging.Logger;

public class CommandServerInitializer extends
		ChannelInitializer<NioSocketChannel> {

	private static final Logger LOG = Logger
			.getLogger(CommandServerInitializer.class.getName());
	private static final ComandoFibonacciHandler COMANDO_FIBONACCI_HANDLER = new ComandoFibonacciHandler();
	private static final ComandoSalirHandler COMANDO_SALIR_HANDLER = new ComandoSalirHandler();
	private static final CommandServerHandler COMMAND_SERVER_HANDLER = new CommandServerHandler();
	private static final CommandDecoder COMMAND_DECODER = new CommandDecoder();
	private static final StringEncoder STRING_ENCODER = new StringEncoder();
	private static final StringDecoder STRING_DECODER = new StringDecoder();
	private static final LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.INFO);
	private static final int MAX_LENGTH = 1024;

	@Override
	protected void initChannel(NioSocketChannel ch) throws Exception {
		// TODO Auto-generated method stub
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast("logger-pipeline", LOGGING_HANDLER);
		pipeline.addLast("framer", new LineBasedFrameDecoder(MAX_LENGTH, true,
				true));
		pipeline.addLast("string-decoder", STRING_DECODER);
		pipeline.addLast("string-encoder", STRING_ENCODER);
		pipeline.addLast("comando-decoder", COMMAND_DECODER);
		pipeline.addLast("readtimeouthandler", new ReadTimeoutHandler(20));
		pipeline.addLast("handler", COMMAND_SERVER_HANDLER);
		pipeline.addLast("comando-salir", COMANDO_SALIR_HANDLER);
		pipeline.addLast("fibonacci-handler", COMANDO_FIBONACCI_HANDLER);
		
		LOG.info("Initializing Channel pipeline");
		pipeline.names().stream().forEachOrdered((String handlerName) -> {
			LOG.info(handlerName);
		});
		LOG.info("End of pipeline");

	}

}
