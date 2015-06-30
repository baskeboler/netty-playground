package com.victor.commandserver.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LoggingHandler;

import org.junit.Before;
import org.junit.Test;

import com.victor.commandserver.server.commands.ComandoSumar;
import com.victor.commandserver.server.handlers.ComandoSumarHandler;

public class ComandoSumarTest {
	private EmbeddedChannel ch;

	@Before
	public void init() {
		
		ch = new EmbeddedChannel(new LoggingHandler(), new ComandoSumarHandler());
	}

	@Test
	public void testSumar() {
		
		ComandoSumar com = new ComandoSumar(5, 5);
		
		ch.writeInbound(com);
		String res = ch.readOutbound();
		assertNotNull(res);
		assertTrue(res instanceof String);
		assertTrue(res.length() > 0);
		assertTrue(res.toLowerCase().trim().equals("resultado = 10"));
	}

}
