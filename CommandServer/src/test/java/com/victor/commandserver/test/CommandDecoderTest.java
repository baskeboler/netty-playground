package com.victor.commandserver.test;

import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.DecoderException;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.victor.commandserver.server.CommandDecoder;
import com.victor.commandserver.server.commands.ComandoEcho;
import com.victor.commandserver.server.commands.ComandoFortune;
import com.victor.commandserver.server.commands.ComandoSalir;
import com.victor.commandserver.server.commands.ComandoSumar;

import static org.junit.Assert.*;

public class CommandDecoderTest {
	
	private EmbeddedChannel ch;
	private static final Logger LOGGER = Logger.getLogger(CommandDecoderTest.class);
	
	@Before
	public void init() {
		LOGGER.info("Running init() method");
		ch = new EmbeddedChannel(new CommandDecoder());
	}
	
	@Test
	public void testDecodeSumar() {
		ch.writeInbound("sumar 2 3\n");
		Object ret = ch.readInbound();
		assertNotNull(ret);
		assertTrue(ret instanceof ComandoSumar);
		ComandoSumar s = (ComandoSumar) ret;
		assertTrue(s.getA() == 2);
		assertTrue(s.getB() == 3);
	}
	
	@Test
	public void testDecodeFortune() {
		ch.writeInbound("fortune");
		Object ret = ch.readInbound();
		assertNotNull(ret);
		assertTrue(ret instanceof ComandoFortune);
	}
	
	@Test
	public void testDecodeSalir() {
		ch.writeInbound("chau");
		Object ret = ch.readInbound();
		assertNotNull(ret);
		assertTrue(ret instanceof ComandoSalir);
	}
	
	@Test
	public void testDecodeEcho() {
		ch.writeInbound("echo blablabla");
		Object ret = ch.readInbound();
		assertNotNull(ret);
		assertTrue(ret instanceof ComandoEcho);
	}
	
	@Test(expected=DecoderException.class)
	public void testDecodeError() {
		ch.writeInbound("inexistentcommand 2 3");
	}
	

}
