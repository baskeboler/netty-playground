package com.victor.commandserver.parser;

import org.parboiled.MatcherContext;
import org.parboiled.matchers.CustomMatcher;

public class StringLiteralCharacterMatcher extends CustomMatcher {

	public StringLiteralCharacterMatcher() {
		super("stringLiteralCharacter");
	}

	@Override
	public <V> boolean match(MatcherContext<V> ctx) {
		// TODO Auto-generated method stub
		if (!acceptChar(ctx.getCurrentChar())) {
			return false;
		}
		ctx.advanceIndex(1);
		ctx.createNode();
		return true;
	}

	private boolean acceptChar(char currentChar) {
		// TODO Auto-generated method stub
		return Character.isLetterOrDigit(currentChar)
				|| Character.isWhitespace(currentChar);
	}

	@Override
	public boolean canMatchEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public char getStarterChar() {
		// TODO Auto-generated method stub
		return 'a';
	}

	@Override
	public boolean isSingleCharMatcher() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isStarterChar(char currentChar) {
		// TODO Auto-generated method stub
		return acceptChar(currentChar);
	}

}
