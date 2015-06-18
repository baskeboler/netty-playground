package com.victor.commandserver;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

import com.victor.commandserver.server.CommandServer;



@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
public class Main {

	private static Logger LOG = Logger.getLogger(Main.class.getName());
	
	@Autowired
	private CommandServer commandServer;

	public static void main(String[] args) throws InterruptedException {
		LOG.info("Iniciando Command Server");
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		
		URL[] urls = ((URLClassLoader)cl).getURLs();
		
		for(URL url: urls){
			System.out.println(url.getFile());
		}
		ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
		
        
		CommandServer server = context.getBean(CommandServer.class);
		server.getCloseChannelFuture().sync();
	
	}
}
