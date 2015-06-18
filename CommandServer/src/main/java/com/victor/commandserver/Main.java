package com.victor.commandserver;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.victor.commandserver.server.CommandServer;



@SpringBootApplication
public class Main {

	private static Logger LOG = Logger.getLogger(Main.class.getName());
	
	@Autowired
	private CommandServer commandServer;

	public static void main(String[] args) throws InterruptedException {
		LOG.info("Iniciando Command Server");
		ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
		CommandServer server = context.getBean(CommandServer.class);
		server.getCloseChannelFuture().sync();
	
	}
}
