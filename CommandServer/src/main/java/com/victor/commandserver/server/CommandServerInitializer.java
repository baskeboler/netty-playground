package com.victor.commandserver.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.victor.commandserver.server.handlers.ComandoEchoHandler;
import com.victor.commandserver.server.handlers.ComandoFibonacciHandler;
import com.victor.commandserver.server.handlers.ComandoFortuneHandler;
import com.victor.commandserver.server.handlers.ComandoSalirHandler;
import com.victor.commandserver.server.handlers.CommandServerHandler;

@Component
public class CommandServerInitializer extends
		ChannelInitializer<NioSocketChannel> {

	private static final Logger LOG = Logger
			.getLogger(CommandServerInitializer.class.getName());
	@Autowired
	private ComandoFortuneHandler fortuneHandler;

	@Autowired
	private ComandoFibonacciHandler fibHandler;

	@Autowired
	private ComandoSalirHandler salirHandler;

	@Autowired
	private CommandServerHandler serverHandler;
	@Autowired
	private ComandoEchoHandler echoHandler;

	private static final CommandDecoder COMMAND_DECODER = new CommandDecoder();
	private static final StringEncoder STRING_ENCODER = new StringEncoder();
	private static final StringDecoder STRING_DECODER = new StringDecoder();
	private static final LoggingHandler LOGGING_HANDLER = new LoggingHandler(
			LogLevel.INFO);
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
		pipeline.addLast("idle-state-handler", new IdleStateHandler(20, 0, 0));
		pipeline.addLast("comando-decoder", COMMAND_DECODER);
		pipeline.addLast("handler", serverHandler);
		pipeline.addLast("comando-salir", salirHandler);
		pipeline.addLast("fibonacci-handler", fibHandler);
		pipeline.addLast("fortune-handler", fortuneHandler);
		pipeline.addLast("echo-handler", echoHandler);

		LOG.info("Initializing Channel pipeline");
		pipeline.names().stream().forEachOrdered((String handlerName) -> {
			LOG.info(handlerName);
		});
		LOG.info("End of pipeline");

	}

	public ComandoFortuneHandler getFortuneHandler() {
		return fortuneHandler;
	}

	public void setFortuneHandler(ComandoFortuneHandler fortuneHandler) {
		this.fortuneHandler = fortuneHandler;
	}

	public ComandoFibonacciHandler getFibHandler() {
		return fibHandler;
	}

	public void setFibHandler(ComandoFibonacciHandler fibHandler) {
		this.fibHandler = fibHandler;
	}

	public ComandoSalirHandler getSalirHandler() {
		return salirHandler;
	}

	public void setSalirHandler(ComandoSalirHandler salirHandler) {
		this.salirHandler = salirHandler;
	}

	public CommandServerHandler getServerHandler() {
		return serverHandler;
	}

	public void setServerHandler(CommandServerHandler serverHandler) {
		this.serverHandler = serverHandler;
	}

}
