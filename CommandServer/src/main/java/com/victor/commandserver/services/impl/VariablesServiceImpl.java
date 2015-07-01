package com.victor.commandserver.services.impl;

import io.netty.channel.ChannelId;
import io.netty.util.AttributeKey;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.victor.commandserver.services.AttributesService;
import com.victor.commandserver.services.VariablesService;

@Service
public class VariablesServiceImpl implements VariablesService {

	private static final Logger LOGGER = Logger.getLogger(VariablesServiceImpl.class);
	private static final AttributeKey<Map<String, String>> channelVarsKey = AttributeKey
			.valueOf("channel.vars.key");

	@Autowired
	private AttributesService attributesService;

	@Override
	public String getVariable(ChannelId id, String varName) {
		// TODO Auto-generated method stub
		return attributesService.attr(id, channelVarsKey).get().get(varName);
	}

	@Override
	public void setVariable(ChannelId id, String varName, String value) {
		// TODO Auto-generated method stub
		attributesService.attr(id, channelVarsKey).get().put(varName, value);

	}

	@Override
	public void deleteVariable(ChannelId id, String varName) {
		// TODO Auto-generated method stub
		attributesService.attr(id, channelVarsKey).get().remove(varName);
	}

	public AttributesService getAttributesService() {
		return attributesService;
	}

	public void setAttributesService(AttributesService attributesService) {
		this.attributesService = attributesService;
	}

	@Override
	public void register(ChannelId id) {
		// TODO Auto-generated method stub
		LOGGER.info("Registering Channel ID " + id.asShortText());
		attributesService.attr(id, channelVarsKey).set(new ConcurrentHashMap<String, String>());
		
	}

	@Override
	public void unregister(ChannelId id) {
		// TODO Auto-generated method stub
		LOGGER.info("Unregistering Channel ID " + id.asShortText());
		attributesService.attr(id, channelVarsKey).remove();
	}

}
