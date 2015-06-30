package com.victor.commandserver.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import io.netty.channel.embedded.EmbeddedChannel;

import org.junit.Test;

import com.victor.commandserver.server.commands.ComandoSumar;
import com.victor.commandserver.server.handlers.ComandoSumarHandler;

public class ComandoSumarTest {

	@Test
	public void testSumar() {
		EmbeddedChannel ch = new EmbeddedChannel(new ComandoSumarHandler());
		
		ComandoSumar com = new ComandoSumar(5, 5);
		
		ch.writeInbound(com);
		String res = ch.readOutbound();
		assertNotNull(res);
		assertTrue(res instanceof String);
		assertTrue(res.length() > 0);
		assertTrue(res.toLowerCase().trim().equals("resultado = 10"));
	}

}
