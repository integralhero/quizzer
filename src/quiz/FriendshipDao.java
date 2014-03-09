package quiz;

import java.sql.*;
import java.util.*;
import java.sql.Connection;

public class FriendshipDao {
	
	private static Connection connection = Database.connect();
	
	public static ArrayList<User> getFriends(int user_id) {
		try {
			
			ArrayList<User> friends = new ArrayList<User> ();
			String command = "SELECT * FROM friendships WHERE userID = " + "\"" + user_id + "\"";
			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			
			
			while (rs.next()) {
				
				int friend_id = rs.getInt("friendID");
				User friend = UserDao.getUserById(friend_id);
				friends.add(friend);
			}
			
			return friends;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/*public static User getUserByID(int user_id) {
		
		try {
			String command = "SELECT * FROM users WHERE ID = " + user_id;
			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);	
			
			User user = new User();
			
			if(rs.next()) {
				user.setUserid(rs.getInt("ID"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				int cond = rs.getInt("admin");
				if (cond == 1) user.setAdminStatus(true);
				else user.setAdminStatus(false);
				user.setNumQuizzesCreated(rs.getInt("numQuizzesCreated"));
				user.setNumQuizzesTaken(rs.getInt("numQuizzesTaken"));
			}
			
			return user;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
		
	}*/
}
