package com.victor.commandserver.server.commands;

public class ComandoFibonacci extends AbstractBaseComando{
	int a;

	public ComandoFibonacci(int a) {
		super();
		this.a = a;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}
}
