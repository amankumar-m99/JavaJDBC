package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import dao.BookDao;

public class DbConfig {

	private static Connection connection = null;

	public static Connection getConnection() {
		if(connection == null) {
			makeConnection();
		}
		return connection;
	}

	private static void makeConnection() {
		try {
			ConfigLoader configLoader = new ConfigLoader();
			Properties properties = configLoader.getProperties();
			String driver = properties.get("dbDriver").toString();
			//load the driver
			Class.forName(driver);
			String dbUrl = properties.get("dbUrl").toString();
			String dbUser= properties.get("dbUser").toString();
			String dbPass = properties.get("dbPass").toString();
			connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
			if(connection.isClosed()) {
				System.out.println("Connection is closed");
				return;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static List<String> getCreateQueriesForAllTables() {
		List<String> createTableCommands = new ArrayList<>();
		createTableCommands.add(BookDao.CREATE_QUERY);
		return createTableCommands;
	}

}
