package dbUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class DBHelper {
	
	private Connection connection;
	protected static String testingP;

	public DBHelper() {

		try {
//			this.connection = Database.getConnection();
			this.setConnection(Database.getConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (this.getConnection() == null) {
			System.exit(1);
		}
	}

	public final boolean isDatabaseConnected() {
		return this.getConnection() != null;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}
