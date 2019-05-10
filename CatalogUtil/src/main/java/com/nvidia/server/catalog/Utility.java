package com.nvidia.server.catalog;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utility {
	private static Logger logger = LoggerFactory.getLogger(Utility.class);

	private Utility() {
	}

	public static Properties getProperties() {
		Properties prop = new Properties();
		try (InputStream input = new FileInputStream(System.getenv("CATALOG_UTILITY_ENV") + "/conf/config.properties");) {
			prop.load(input);
		} catch (IOException e) {
			logger.error(getExceptionString(e));
		}
		return prop;
	}

	public static String getExceptionString(Throwable t) {
		StringWriter sw = new StringWriter();
		t.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}
}
