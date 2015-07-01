package com.victor.commandserver.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.annotation.PostConstruct;

import io.netty.channel.ChannelId;
import io.netty.util.DefaultAttributeMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.parserunners.TracingParseRunner;
import org.parboiled.support.ParsingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.victor.commandserver.Main;
import com.victor.commandserver.server.commands.Comando;
import com.victor.commandserver.server.commands.ComandoFortune;
import com.victor.commandserver.server.commands.ComandoSalir;
import com.victor.commandserver.server.commands.ComandoSumar;
import com.victor.commandserver.services.AttributesService;
import com.victor.commandserver.services.VariablesService;
import com.victor.commandserver.services.impl.AttributesServiceImpl;
import com.victor.commandserver.services.impl.VariablesServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Main.class)
public class ParserTest {

	@Autowired
	private AttributesService attributesService;
	@Autowired
	private VariablesService variablesService;

	private ComandoParser parser;

	private ChannelId channelId;

	public ParserTest() {
		// TODO Auto-generated constructor stub
		channelId = new ChannelId() {

			@Override
			public int compareTo(ChannelId arg0) {
				// TODO Auto-generated method stub
				return asLongText().compareTo(arg0.asLongText());
			}

			@Override
			public String asShortText() {
				// TODO Auto-generated method stub
				return "1234";
			}

			@Override
			public String asLongText() {
				// TODO Auto-generated method stub
				return "12341234";
			}

		};
	}

	@PostConstruct
	public void init() {
		getAttributesService().register(channelId, new DefaultAttributeMap());
		getVariablesService().register(channelId);
		getVariablesService().setVariable(channelId, "var1", "23");
		getVariablesService().setVariable(channelId, "var2", "2");
		parser = Parboiled.createParser(ComandoParser.class, channelId);
		parser.setVariablesService(getVariablesService());
	}

	@SuppressWarnings("unused")
	@Test
	public void testParse1() {
		String comString = "sumar 23 33333";
		Rule rootRule = parser.Comando();

		ParsingResult<?> res = new TracingParseRunner(rootRule).run(comString);
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

		ParsingResult<?> res = new TracingParseRunner(rootRule).run(com);
		assertTrue(res.matched);
		Comando c = (Comando) res.resultValue;
		assertTrue(c instanceof Comando);
	}

	@Test
	public void testSalir() {
		String com = "SaLir";
		Rule rootRule = parser.Comando();

		ParsingResult<?> res = new TracingParseRunner(rootRule).run(com);
		assertTrue(res.matched);
		Comando c = (Comando) res.resultValue;
		assertTrue(c instanceof ComandoSalir);
	}

	@Test
	public void testFortune() {
		String com = "Fortune  ";
		Rule rootRule = parser.Comando();

		ParsingResult<?> res = new TracingParseRunner(rootRule).run(com);
		assertTrue(res.matched);
		Comando c = (Comando) res.resultValue;
		assertTrue(c instanceof ComandoFortune);
	}
	
	@Test
	public void testSumarVars() {
		String com = "sumar :var1 :var2";
		Rule rootRule = parser.Comando();

		ParsingResult<?> res = new TracingParseRunner(rootRule).run(com);
		assertTrue(res.matched);
		Comando c = (Comando) res.resultValue;
		assertTrue(c instanceof ComandoSumar);
	}

	public AttributesService getAttributesService() {
		return attributesService;
	}

	public void setAttributesService(AttributesService attributesService) {
		this.attributesService = attributesService;
	}

	public VariablesService getVariablesService() {
		return variablesService;
	}

	public void setVariablesService(VariablesService variablesService) {
		this.variablesService = variablesService;
	}
	
	@Bean
	public AttributesService createAttributesService() {
		return new AttributesServiceImpl();
	}
	
	@Bean
	public VariablesService createVariablesService() {
		return new VariablesServiceImpl();
	}
}
