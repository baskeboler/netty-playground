package com.victor.commandserver.services.impl;

import io.netty.channel.ChannelId;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.AttributeMap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.victor.commandserver.services.AttributesService;

@Service
public class AttributesServiceImpl implements AttributesService {

	private static final Logger LOGGER = Logger
			.getLogger(AttributesServiceImpl.class);

	private Map<ChannelId, AttributeMap> mapsById;

	public AttributesServiceImpl() {
		LOGGER.info("Initializing AttributesService.");
		mapsById = new ConcurrentHashMap<>();
	}

	@Override
	public void register(ChannelId id, AttributeMap map) {
		mapsById.put(id, map);
		LOGGER.info("Registering channel ID " + id.asShortText());
	}

	@Override
	public void unregister(ChannelId id) {
		mapsById.remove(id);
		LOGGER.info("Unregistering channel ID " + id.asShortText());
	}

	@Override
	public <T> Attribute<T> attr(ChannelId id, AttributeKey<T> key) {
		// TODO Auto-generated method stub
		
		return mapsById.get(id).attr(key);
	}

	@Override
	public <T> boolean hasAttr(ChannelId id, AttributeKey<T> key) {
		// TODO Auto-generated method stub
		return mapsById.get(id).hasAttr(key);
	}

}
