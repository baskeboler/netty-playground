package com.victor.commandserver.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.parserunners.TracingParseRunner;
import org.parboiled.support.ParsingResult;

import com.victor.commandserver.server.commands.Comando;
import com.victor.commandserver.server.commands.ComandoFortune;
import com.victor.commandserver.server.commands.ComandoSalir;
import com.victor.commandserver.server.commands.ComandoSumar;

public class ParserTest {

	private ComandoParser parser;

	@Before
	public void init() {
		parser = Parboiled.createParser(ComandoParser.class);
	}

	@SuppressWarnings("unused")
	@Test
	public void testParse1() {
		String comString = "sumar 23 33333";
		Rule rootRule = parser.Comando();

		ParsingResult<?> res = new TracingParseRunner(rootRule)
				.run(comString);
		assertTrue(res.matched);
		Comando c = (Comando) res.resultValue;
		assertTrue(c instanceof Comando);
		assertTrue(c instanceof ComandoSumar);

		ComandoSumar s = (ComandoSumar) c;
		assertEquals(s.getA(), 23);
		assertEquals(s.getB(), 33333);
	}

	@Test
	public void testFib() {
		String com = "Fibonacci 33";
		Rule rootRule = parser.Comando();

		ParsingResult<?> res = new TracingParseRunner(rootRule)
				.run(com);
		assertTrue(res.matched);
		Comando c = (Comando) res.resultValue;
		assertTrue(c instanceof Comando);
	}
	
	@Test
	public void testSalir() {
		String com = "SaLir";
		Rule rootRule = parser.Comando();

		ParsingResult<?> res = new TracingParseRunner(rootRule)
				.run(com);
		assertTrue(res.matched);
		Comando c = (Comando) res.resultValue;
		assertTrue(c instanceof ComandoSalir);
	}
	
	@Test
	public void testFortune() {
		String com = "Fortune  ";
		Rule rootRule = parser.Comando();

		ParsingResult<?> res = new TracingParseRunner(rootRule)
				.run(com);
		assertTrue(res.matched);
		Comando c = (Comando) res.resultValue;
		assertTrue(c instanceof ComandoFortune);
	}
}
