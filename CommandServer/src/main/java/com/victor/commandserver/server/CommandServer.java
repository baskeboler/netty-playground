package com.victor.commandserver.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CommandServer {
	private static Logger LOG = Logger.getLogger(CommandServer.class.getName());
	private static final int PORT = 8180;

	@Autowired
	ServerBootstrap bootstrap;

	@Autowired
	NioEventLoopGroup bossGroup;

	@Autowired
	NioEventLoopGroup workerGroup;
	
	@Autowired
	CommandServerInitializer initializer;

	private Channel serverChannel;
	private ChannelFuture closeChannelFuture;

	@Bean
	public ServerBootstrap bootstrap() {
		return new ServerBootstrap();
	}

	@Bean
	public NioEventLoopGroup nioEventLoopGroup() {
		return new NioEventLoopGroup();
	}

	@PostConstruct
	public void init() throws InterruptedException {
		bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
		bootstrap.channel(NioServerSocketChannel.class)
				.group(bossGroup, workerGroup)
				.handler(new LoggingHandler(LogLevel.INFO))
				.childHandler(initializer)
				.childOption(ChannelOption.TCP_NODELAY, true);
		serverChannel = bootstrap.bind(PORT).sync().channel();
		LOG.log(Level.INFO, "Listening on port " + PORT);
		closeChannelFuture = serverChannel.closeFuture().addListener(new ChannelFutureListener() {

			public void operationComplete(ChannelFuture future)
					throws Exception {
				LOG.log(Level.INFO, "Channel closed");
				workerGroup.shutdownGracefully();
				bossGroup.shutdownGracefully();
			}
		});

	}

	public ChannelFuture getCloseChannelFuture() {
		return closeChannelFuture;
	}

	public void setCloseChannelFuture(ChannelFuture closeChannelFuture) {
		this.closeChannelFuture = closeChannelFuture;
	}

	@PreDestroy
	public void cleanUp() {
		LOG.info("Shutting down server. Cleaning up.");
		serverChannel.disconnect();
		serverChannel.close();
	}

	public Channel getServerChannel() {
		return serverChannel;
	}

	public void setServerChannel(Channel serverChannel) {
		this.serverChannel = serverChannel;
	}

	public ServerBootstrap getBootstrap() {
		return bootstrap;
	}

	public void setBootstrap(ServerBootstrap bootstrap) {
		this.bootstrap = bootstrap;
	}

	public NioEventLoopGroup getBossGroup() {
		return bossGroup;
	}

	public void setBossGroup(NioEventLoopGroup bossGroup) {
		this.bossGroup = bossGroup;
	}

	public NioEventLoopGroup getWorkerGroup() {
		return workerGroup;
	}

	public void setWorkerGroup(NioEventLoopGroup workerGroup) {
		this.workerGroup = workerGroup;
	}
}
