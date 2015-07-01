package com.victor.commandserver.parser;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import org.parboiled.support.Var;

import com.victor.commandserver.server.commands.Comando;
import com.victor.commandserver.server.commands.ComandoEcho;
import com.victor.commandserver.server.commands.ComandoFibonacci;
import com.victor.commandserver.server.commands.ComandoFortune;
import com.victor.commandserver.server.commands.ComandoSalir;
import com.victor.commandserver.server.commands.ComandoSumar;

@BuildParseTree
public class ComandoParser extends BaseParser<Comando> {

	private static final char COMILLAS = '"';

	public Rule Comando() {
		return Sequence(
				FirstOf(Sumar(), Fibonacci(), Salir(), Fortune(), Echo()), Optional(Spacing()), EOI);
	}

	public Rule Sumar() {
		Var<Integer> a = new Var<>(), b = new Var<>();
		return Sequence(IgnoreCase("sumar"), Spacing(), Operando(),
				a.set(Integer.parseInt(match())), Spacing(), Operando(),
				b.set(Integer.parseInt(match())), push(new ComandoSumar(
						a.get(), b.get())));

	}

	public Rule Fibonacci() {
		return Sequence(IgnoreCase("fibonacci"), Spacing(), Numero(),
				push(new ComandoFibonacci(Integer.parseInt(match()))));
	}

	public Rule Salir() {
		return Sequence(IgnoreCase("salir"), push(new ComandoSalir()));
	}

	public Rule Fortune() {
		return Sequence(IgnoreCase("fortune"), push(new ComandoFortune()));
	}

	public Rule Echo() {
		return Sequence(IgnoreCase("echo"), StringLiteral(),
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

	public Rule Operando() {
		// TODO Auto-generated method stub
		return FirstOf(Numero(), Variable());
	}

	public Rule Spacing() {
		// TODO Auto-generated method stub
		return OneOrMore(' ');
	}

	public Rule Numero() {
		// TODO Auto-generated method stub
		return OneOrMore(Digito());
	}

	public Rule Digito() {
		// TODO Auto-generated method stub
		return CharRange('0', '9');
	}

	public Rule Variable() {
		// TODO Auto-generated method stub
		return Sequence(':', VarName());
	}

	public Rule VarName() {
		// TODO Auto-generated method stub
		return OneOrMore(FirstOf(CharRange('a', 'z'), CharRange('A', 'Z'),
				CharRange('0', '9')));
	}
}
