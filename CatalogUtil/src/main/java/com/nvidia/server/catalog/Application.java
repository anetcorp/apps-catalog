package com.nvidia.server.catalog;

import java.util.Date;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {
	private static Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {

		PropertyConfigurator.configure(Utility.getProperties().getProperty("log4j.path"));
		logger.info("Utility Started at : {}", new Date());
		PartNumberService.postPartNumber();
		logger.info("Utility Finished at  : {}", new Date());
	}
}
