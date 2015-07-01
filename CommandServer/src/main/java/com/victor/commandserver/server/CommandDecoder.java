package com.victor.commandserver.server;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

import org.apache.log4j.Logger;
import org.parboiled.Parboiled;
import org.parboiled.Rule;
import org.parboiled.errors.ParseError;
import org.parboiled.parserunners.ReportingParseRunner;
import org.parboiled.support.ParsingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.victor.commandserver.parser.ComandoParser;
import com.victor.commandserver.server.commands.Comando;
import com.victor.commandserver.server.exceptions.ComandoUnknownException;
import com.victor.commandserver.services.VariablesService;

@Sharable
@Component
public class CommandDecoder extends MessageToMessageDecoder<String> {
	private static final Logger LOGGER = Logger.getLogger(CommandDecoder.class);

	@Autowired
	private VariablesService variablesService;
	@Override
	protected void decode(ChannelHandlerContext ctx, String msg,
			List<Object> out) throws Exception {
		ComandoParser parser = Parboiled.createParser(ComandoParser.class, ctx
				.channel().id());
		parser.setVariablesService(getVariablesService());
		Rule rootRule = parser.Comando();
		ReportingParseRunner<Comando> runner = new ReportingParseRunner<Comando>(
				rootRule);
		ParsingResult<Comando> result = runner.run(msg);
		if (result.matched) {
			out.add(result.resultValue);
		} else {
			result.parseErrors.stream().forEach((ParseError e) -> {
				LOGGER.error(e.getErrorMessage());
			});
			throw new ComandoUnknownException(result.toString());
		}
	}
	public VariablesService getVariablesService() {
		return variablesService;
	}
	public void setVariablesService(VariablesService variablesService) {
		this.variablesService = variablesService;
	}
}
