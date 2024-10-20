package appmain;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import config.DbConfig;
import view.AppConsole;

public class AppMainJavaJdbc {

	public static void main(String[] args) {
		System.out.println("Making connection to DB...");
		Connection connection = DbConfig.getConnection();
		try {
			if (connection.isClosed()) {
				System.out.println("Connection closed.");
				return;
			}
		} catch (SQLException e) {
			System.out.println("Connection couldn't be made!");
			e.printStackTrace();
			return;
		}
		executeCreateQueries(connection);
		new AppConsole().start();
	}

	private static void executeCreateQueries(Connection connection) {
		for(String query:DbConfig.getCreateQueriesForAllTables()) {
			Statement statement;
			try {
				statement = connection.createStatement();
				int i = statement.executeUpdate(query);
				System.out.println("create status: "+i);
			} catch (SQLException e) {
				System.out.println("Create table query failed!");
				System.out.println(query);
				e.printStackTrace();
			}
		}
	}

}
