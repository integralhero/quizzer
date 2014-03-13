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
	
	public static void removeMessage(int id) {
		try {
			Statement stmt = connection.createStatement();
			stmt.execute("DELETE FROM announcements WHERE ID=" + id );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Announcement> getAllAccouncements() {
		ArrayList<Announcement> ret = new ArrayList<Announcement>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM announcements");
			while(rs.next()) {
				Announcement tmp = new Announcement();
				tmp.setMessage(rs.getString("message"));
				tmp.setMessage_id(rs.getInt("ID"));
				ret.add(tmp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
}
