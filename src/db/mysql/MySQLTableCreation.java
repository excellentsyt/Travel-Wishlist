package db.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLTableCreation {
	// Run this as Java application to reset db schema.
	public static void main(String[] args) {
		try {
			// Ensure the driver is imported.
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			// This is java.sql.Connection. Not com.mysql.jdbc.Connection.
			Connection conn = null;

			// Connect to MySQL.
			try {
				System.out.println("Connecting to \n" + MySQLDBUtil.URL);
				conn = DriverManager.getConnection(MySQLDBUtil.URL);
			} catch (SQLException e) {
				System.out.println("SQLException " + e.getMessage());
				System.out.println("SQLState " + e.getSQLState());
				System.out.println("VendorError " + e.getErrorCode());
			}
			if (conn == null) {
				return;
			}

			// Drop tables in case they exist.
			Statement stmt = conn.createStatement();

			String sql = "DROP TABLE IF EXISTS votes";
			stmt.executeUpdate(sql);

			sql = "DROP TABLE IF EXISTS locations";
			stmt.executeUpdate(sql);

			sql = "DROP TABLE IF EXISTS users";
			stmt.executeUpdate(sql);

			// Create new tables.
			sql = "CREATE TABLE locations " + "(location_id bigint(20) unsigned NOT NULL AUTO_INCREMENT, "
					+ "country VARCHAR(255), " + "latitude FLOAT, " + " longitude FLOAT, " + "description VARCHAR(255), "
					+ "count_of_votes int," + "geohash VARCHAR(255)," + " PRIMARY KEY (location_id))";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE users " + "(user_id VARCHAR(255) NOT NULL, " + " first_name VARCHAR(255), "
					+ "last_name VARCHAR(255), " + " PRIMARY KEY ( user_id ))";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE votes " + "(vote_id bigint(20) unsigned NOT NULL AUTO_INCREMENT, "
					+ " user_id VARCHAR(255) NOT NULL , " + " location_id bigint(20) unsigned NOT NULL, "
					+ " last_added_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP, " + " PRIMARY KEY (vote_id),"
					+ "FOREIGN KEY (location_id) REFERENCES locations(location_id),"
					+ "FOREIGN KEY (user_id) REFERENCES users(user_id))";
			stmt.executeUpdate(sql);

			// Insert data
			// Create a fake user
			sql = "INSERT INTO users VALUES (\"1111\", \"Elon\", \"Musk\");";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO users VALUES (\"2222\", \"Larry\", \"Page\");";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO users VALUES (\"3333\", \"Jack\", \"Ma\");";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO users VALUES (\"4444\", \"Bill\", \"Gates\");";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO users VALUES (\"5555\", \"Tim\", \"Cook\");";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO users VALUES (\"6666\", \"Mark\", \"Zuckerberg\");";
			stmt.executeUpdate(sql);

			System.out.println("Import is done successfully.");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
