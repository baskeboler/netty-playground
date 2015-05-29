package com.victor.commandserver;

import java.util.logging.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.victor.commandserver.server.CommandServer;


@Configuration
@ComponentScan
public class Main {

	private static Logger LOG = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) {
		LOG.info("Hola!");
		ApplicationContext ctx = new AnnotationConfigApplicationContext(Main.class);
	//	ctx.
		CommandServer server = ctx.getBean(CommandServer.class);
		try {
			server.getCloseChannelFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
