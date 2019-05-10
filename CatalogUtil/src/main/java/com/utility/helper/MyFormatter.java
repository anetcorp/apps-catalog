package com.utility.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Formatting the output for logging
 * 
 * @author VINOD KUMAR KASHYAP
 * @since 1.0
 */
class MyFormatter extends Formatter {
	@Override
	public String format(LogRecord rec) {
		StringBuilder buf = new StringBuilder(1000);

		buf.append(rec.getLevel() + " ");
		buf.append(calcDate(rec.getMillis()) + " ");
		buf.append(formatMessage(rec) + "\n");

		return buf.toString();
	}

	private static String calcDate(long millisecs) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(Utility.DATE_FORMAT);
		Date resultdate = new Date(millisecs);
		return dateFormat.format(resultdate);
	}

}
