package quiz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AnnouncementDao {
	private static Connection connection = Database.connect();
	
	public static void addMessage(String message_cont) {
		try {
			Statement stmt = connection.createStatement();
			stmt.execute("INSERT INTO announcements (message) VALUES (\"" + message_cont + "\")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ArrayList<String> getAllAccouncements() {
		ArrayList<String> ret = new ArrayList<String>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM announcements");
			while(rs.next()) {
				ret.add(rs.getString("message"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
}
