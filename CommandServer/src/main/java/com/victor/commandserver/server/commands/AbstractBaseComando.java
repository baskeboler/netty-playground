package com.victor.commandserver.server.commands;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public abstract class AbstractBaseComando {
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ReflectionToStringBuilder.toString(this);
	}
}
