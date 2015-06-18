package com.victor.commandserver.services.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.victor.commandserver.services.FortuneService;

@Service
public class FortuneServiceImpl implements FortuneService {

	private static final Logger LOG = Logger.getLogger(FortuneServiceImpl.class.getName());
	@Value("file:classpath*:/com/victor/commandserver/services/impl/fortunes.txt")
	private Resource fortunesFile;

	private List<String> fortunes;
	private Iterator<String> fortunesIterator;


//	public FortuneServiceImpl() {
//		//fortunesFile = new ClassPathResource(
//			//	"classpath*:/com/victor/commandserver/services/impl/fortunes.txt");
//	//	init();
//	}

	public void init() {
		InputStream is;
		try {
			is = fortunesFile.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			String reduce = in.lines().reduce("", (a, b) -> a + "\n" + b);
			fortunes = Arrays.asList(reduce.split("%"));
			Collections.shuffle(fortunes);
			fortunesIterator = fortunes.iterator();
			LOG.info("Fortunes initialized");
			LOG.info("Count: " + fortunes.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public String getCookie() {
		String res = fortunesIterator.next();
		LOG.info("Returning cookie: " + res);
		return res;
	}

	public Resource getFortunesFile() {
		return fortunesFile;
	}

	public void setFortunesFile(Resource fortunesFile) {
		this.fortunesFile = fortunesFile;
	}
}
