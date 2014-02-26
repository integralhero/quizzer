package quiz;


import java.sql.*;

public class Database {
	
	public static Connection connect() {
		String host = MyDBInfo.MYSQL_DATABASE_SERVER;
		String user = MyDBInfo.MYSQL_USERNAME;
		String pass = MyDBInfo.MYSQL_PASSWORD;
		String dbname = MyDBInfo.MYSQL_DATABASE_NAME;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://" + host + "/" + dbname, user, pass);
			//System.out.println("Works");
			return connect;
		}
		catch (SQLException err ) {
			System.out.println( err.getMessage( ) );
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null; 
	}
	
	public static ResultSet query(String query) {
		Connection connect = connect();
		try {
			Statement stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
