package com.victor.commandserver.parser;

import io.netty.channel.ChannelId;

import org.parboiled.Action;
import org.parboiled.BaseParser;
import org.parboiled.Context;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import org.parboiled.common.Reference;
import org.parboiled.support.Var;
import org.springframework.beans.factory.annotation.Autowired;

import com.victor.commandserver.server.commands.Comando;
import com.victor.commandserver.server.commands.ComandoEcho;
import com.victor.commandserver.server.commands.ComandoFibonacci;
import com.victor.commandserver.server.commands.ComandoFortune;
import com.victor.commandserver.server.commands.ComandoGetVar;
import com.victor.commandserver.server.commands.ComandoSalir;
import com.victor.commandserver.server.commands.ComandoSetVar;
import com.victor.commandserver.server.commands.ComandoSumar;
import com.victor.commandserver.services.VariablesService;

@BuildParseTree
public class ComandoParser extends BaseParser<Comando> {

	@Autowired
	private VariablesService variablesService;

	private ChannelId channelId;

	public ComandoParser(ChannelId channelId) {
		super();
		this.channelId = channelId;
	}

	private static final char COMILLAS = '"';

	public Rule Comando() {
		return Sequence(
				FirstOf(Sumar(), Fibonacci(), Salir(), Fortune(), Echo(),
						SetVar(), GetVar()), Optional(Spacing()), EOI);
	}

	public Rule GetVar() {
		Var<String> name = new Var<String>();
		// TODO Auto-generated method stub
		return Sequence(':', VarName(name), push(new ComandoGetVar(name.get())));
	}

	public Rule SetVar() {
		// TODO Auto-generated method stub
		Var<String> varName = new Var<String>();
		Var<Integer> value = new Var<Integer>();
		return Sequence(VarName(varName), Optional(Spacing()), '=',
				Optional(Spacing()), Numero(value), push(new ComandoSetVar(
						varName.get(), "" + value.get())));
	}

	public Rule Sumar() {
		Var<Integer> a = new Var<>(), b = new Var<>();
		return Sequence(IgnoreCase("sumar"), Spacing(), Operando(a), Spacing(),
				Operando(b), push(new ComandoSumar(a.get(), b.get())));

	}

	public Rule Fibonacci() {
		Var<Integer> a = new Var<Integer>();
		return Sequence(IgnoreCase("fibonacci"), Spacing(), Numero(a),
				push(new ComandoFibonacci(a.get())));
	}

	public Rule Salir() {
		return Sequence(IgnoreCase("chau"), push(new ComandoSalir()));
	}

	public Rule Fortune() {
		return Sequence(IgnoreCase("fortune"), push(new ComandoFortune()));
	}

	public Rule Echo() {
		return Sequence(IgnoreCase("echo"), Spacing(), StringLiteral(),
				push(new ComandoEcho(match())));
	}

	public Rule StringLiteral() {
		// TODO Auto-generated method stub
		return Sequence(COMILLAS,
				ZeroOrMore(new StringLiteralCharacterMatcher()), COMILLAS);
	}

	public Rule ValidStringChar() {
		// TODO Auto-generated method stub
		return null;
	}

	public Rule Operando(Var<Integer> value) {
		// TODO Auto-generated method stub
		return FirstOf(Numero(value), Variable(value));
	}

	public Rule Spacing() {
		// TODO Auto-generated method stub
		return OneOrMore(' ');
	}

	public Rule Numero(Var<Integer> value) {
		// TODO Auto-generated method stub
		return Sequence(OneOrMore(Digito()),
				value.set(Integer.parseInt(match())));
	}

	public Rule Digito() {
		// TODO Auto-generated method stub
		return CharRange('0', '9');
	}

	public Rule Variable(Var<Integer> value) {
		// TODO Auto-generated method stub
		Var<String> varName = new Var<String>();
		return Sequence(':', VarName(varName), new Action() {

			@Override
			public boolean run(Context context) {
				// TODO Auto-generated method stub
				String val = getVariablesService().getVariable(channelId,
						varName.get());
				value.set(Integer.parseInt(val));
				return true;
			}
		});
	}

	public Rule VarName(Var<String> name) {
		// TODO Auto-generated method stub
		return Sequence(
				OneOrMore(FirstOf(CharRange('a', 'z'), CharRange('A', 'Z'),
						CharRange('0', '9'))), name.set(match()));
	}

	public VariablesService getVariablesService() {
		return variablesService;
	}

	public void setVariablesService(VariablesService variablesService) {
		this.variablesService = variablesService;
	}
}
