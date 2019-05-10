package com.utility.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.LoggerFactory;
import com.nvidia.server.catalog.Utility;

/**
 * This class is used for connection to DB
 * 
 * @author VINOD KUMAR KASHYAP
 * @since 1.0
 * 
 */
public class DBConnection {

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(DBConnection.class);

	private DBConnection() {

	}

	/**
	 * Used to get Connection
	 * 
	 * @return Connection object
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(Utility.getProperties().getProperty("jdbc.driver.url"),
					Utility.getProperties().getProperty("jdbc.username"),
					Utility.getProperties().getProperty("jdbc.password"));
			logger.info("Connection Successful...");
		} catch (SQLException e) {
			logger.error(Utility.getExceptionString(e));
		}
		return conn;
	}

	/**
	 * Used to close Connection
	 * 
	 * @param conn
	 *            Connection to be closed
	 */
	public static void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			logger.error(Utility.getExceptionString(e));
		}
	}

}
