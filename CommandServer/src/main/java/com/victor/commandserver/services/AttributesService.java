package com.victor.commandserver.services;

import io.netty.channel.ChannelId;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.AttributeMap;

public interface AttributesService {
	void register(ChannelId id, AttributeMap map);
	void unregister(ChannelId id);
	<T> Attribute<T> attr(ChannelId id, AttributeKey<T> key);
	<T> boolean hasAttr(ChannelId id, AttributeKey<T> key);
}
