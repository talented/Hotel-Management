package dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	
private static final String SQCONN = "jdbc:sqlite:HotelVerwaltung.db";
	
	public static Connection getConnection() throws SQLException {
		
		try {
			Class.forName("org.sqlite.JDBC");
			return DriverManager.getConnection(SQCONN);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
		
	}

}
