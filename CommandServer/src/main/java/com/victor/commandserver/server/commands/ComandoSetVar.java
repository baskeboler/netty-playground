package com.victor.commandserver.server.commands;

public class ComandoSetVar extends AbstractBaseComando {
	private String varName;
	public ComandoSetVar(String varName, String varValue) {
		super();
		this.varName = varName;
		this.varValue = varValue;
	}

	private String varValue;

	public String getVarValue() {
		return varValue;
	}

	public void setVarValue(String varValue) {
		this.varValue = varValue;
	}

	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

}
