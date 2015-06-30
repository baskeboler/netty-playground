package com.victor.commandserver.test;

import static org.junit.Assert.*;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LoggingHandler;

import org.junit.Before;
import org.junit.Test;

import com.victor.commandserver.server.commands.ComandoFibonacci;
import com.victor.commandserver.server.handlers.ComandoFibonacciHandler;

public class ComandoFibonacciTest {
	private EmbeddedChannel ch;

	@Before
	public void init() {
		ch = new EmbeddedChannel(new LoggingHandler(), new ComandoFibonacciHandler());
	}
	
	@Test
	public void testFibonacci1() {
		ComandoFibonacci com = new ComandoFibonacci(3);
		ch.writeInbound(com);
		
		Object ret = ch.readOutbound();
		assertNotNull(ret);
		assertTrue(ret instanceof String);
		String s = (String) ret;
		assertTrue(s.trim().toLowerCase().equals("resultado = 2"));
	}
}
