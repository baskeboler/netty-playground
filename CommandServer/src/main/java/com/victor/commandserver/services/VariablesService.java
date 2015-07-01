package com.victor.commandserver.services;

import io.netty.channel.ChannelId;

public interface VariablesService {
	void register(ChannelId id);
	void unregister(ChannelId id);
	String getVariable(ChannelId id, String varName);
	void setVariable(ChannelId id, String varName, String value);
	void deleteVariable(ChannelId id, String varName);
}
