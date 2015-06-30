package com.victor.commandserver.server.commands;

public class ComandoGetVar extends AbstractBaseComando{
	private String varName;

	public ComandoGetVar(String varName) {
		super();
		this.varName = varName;
	}

	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}
}
