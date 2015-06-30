package com.victor.commandserver.server.commands;

public class ComandoEcho extends AbstractBaseComando{
	private String msg;

	public ComandoEcho(String msg) {
		this.setMsg(msg);
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
