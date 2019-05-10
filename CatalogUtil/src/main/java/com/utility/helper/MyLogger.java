package com.utility.helper;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Configuring the Logger
 * 
 * @author VINOD KUMAR KASHYAP
 *
 * @since 1.0
 * 
 */
public class MyLogger {
	private static FileHandler fileTxt;
	private static MyFormatter formatterTxt;

	private MyLogger() {
	}

	/**
	 * Setup Logger
	 * 
	 * @throws IOException
	 */
	public static void setup() throws IOException {

		// get the global logger to configure it
		final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

		// suppress the logging output to the console
		final Logger LOGROOT = Logger.getLogger("");
		Handler[] handlers = LOGROOT.getHandlers();
		if (handlers[0] instanceof ConsoleHandler) {
			LOGROOT.removeHandler(handlers[0]);
		}

		LOGGER.setLevel(Level.INFO);
		fileTxt = new FileHandler("Logging.txt");

		// create a TXT formatter
		formatterTxt = new MyFormatter();
		fileTxt.setFormatter(formatterTxt);
		LOGGER.addHandler(fileTxt);
	}
}