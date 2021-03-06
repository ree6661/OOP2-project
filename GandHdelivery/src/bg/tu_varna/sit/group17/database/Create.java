package bg.tu_varna.sit.group17.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import bg.tu_varna.sit.group17.application.LoggerApp;

/**
 * This class is used for connecting to the database to the database.
 */
public final class Create {
	private static final String database = "deliverydb";
	private static final String url = "jdbc:mysql://localhost/" + database + "?useUnicode=true&characterEncoding=UTF-8";
	private static final String admin = "root";
	private static final String adminPass = "Mysql@localhost:3306";

	private static final LoggerApp logger = new LoggerApp(Create.class.getClass().getName());

	private Create() {
		// utility
	}

	/**
	 * Tries connection to the application's database.
	 * 
	 * @return a connection object with an information for the connected database.
	 */
	public static Connection getConnection() {
		try {
			Properties info = new Properties();
			info.put("user", admin);
			info.put("password", adminPass);
			info.put("userUnicode", "true");

			return DriverManager.getConnection(url, info);
		} catch (SQLException e) {
			logger.error("Cannot connect to the DB " + e.getMessage());
		}
		return null;
	}
}
