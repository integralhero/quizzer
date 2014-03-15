package quiz;

import java.sql.*;
import java.util.*;

public class UserDao {
	private static Connection connection = Database.connect();
	
	public static ArrayList<User> getAllUsersMatching(String username) {
		ArrayList<User> al = new ArrayList<User>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE username = " + "\"" + username + "\"");
			while(rs.next()) {
				User tmpUser = new User();
				tmpUser.setUsername(rs.getString("username"));
				tmpUser.setPassword(rs.getString("password"));
				tmpUser.setEmail(rs.getString("email"));
				tmpUser.setUserid(rs.getInt("ID"));
				tmpUser.setAdminStatus(rs.getBoolean("admin"));
				tmpUser.setSalt(rs.getString("salt"));
				al.add(tmpUser);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return al;
	}
	
	public static boolean checkUserNameExists(String username) {
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT 1 FROM users WHERE username = " + "\"" + username + "\"");
			boolean more = rs.next();

			
			if (!more) {
				
				return false;
			} else {
				
				return true;
			}
		
		}

       catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static int getUserNum() {
		int count = 0;
		try {
			String command = "SELECT * FROM users";
			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			while(rs.next()) {
				count++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public static void addUser(User user) {
		try {
			PreparedStatement prepStmt = connection.prepareStatement("INSERT INTO users(username, password, email, salt) VALUES (?,?,?,?)");
			prepStmt.setString(1, user.getUsername());
			prepStmt.setString(2, user.getPassword());
			prepStmt.setString(3, user.getEmail());
			prepStmt.setString(4, user.getSalt());
			prepStmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static public void deleteUser(int userId) {
		PreparedStatement prepStmt;
		try {
			prepStmt = connection.prepareStatement("DELETE FROM users WHERE ID= ?");
			prepStmt.setInt(1, userId);
			prepStmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void promoteUser(int userId) {
		try {
			Statement statement = connection.createStatement();
			statement.execute("UPDATE users SET admin = 1 WHERE ID = " + userId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void demoteUser(int userId) {
		try {
			Statement statement = connection.createStatement();
			statement.execute("UPDATE users SET admin = 0 WHERE ID = " + userId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static public void updateUser(User user) {
		try {
			PreparedStatement prepStmt = connection.prepareStatement("UPDATE users SET username = ?, password = ?, email = ? "+ "WHERE ID = ?" );
			prepStmt.setString(1, user.getUsername());
			prepStmt.setString(2, user.getPassword());
			prepStmt.setString(3, user.getEmail());
			prepStmt.setInt(4, user.getUserid());
			prepStmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static public String getSalt(String username){
		String salt = "";
		try {
			String command = "SELECT * FROM users WHERE username =\"" + username + "\"";
			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			if(rs.next()) {
				salt = rs.getString("salt");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return salt;
	}
	
	public static boolean setStatus(int userid) {
		boolean ret = false;
		try {
			Statement statement = connection.createStatement();
			String command = "SELECT * FROM users WHERE ID =" + userid;
			ResultSet rs = statement.executeQuery(command);
			if(rs.next()) {
				ret = rs.getBoolean("admin");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	
	public static int validateUser(User user) {
		
		int id = -1;
		try {
			String username = user.getUsername();
			String password = user.getPassword();
			String command = "SELECT * FROM users WHERE username =\"" + username + "\" AND password = \"" + password  + "\"";
			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			if(rs.next()) {
				id = rs.getInt("ID");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return id;

	}

	public static List<User> getAllUser() {
		List<User> users = new ArrayList<User>();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM users");
			while(rs.next()) {
				User user = new User();
				user.setUserid(rs.getInt("ID"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setAdminStatus(rs.getBoolean("admin"));
				users.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}
	
	public static ArrayList<User> getAllFriendsOf(int userId) {
		ArrayList<Integer> friendIDs = new ArrayList<Integer>();
		ArrayList<User> allFriends = new ArrayList<User>();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM friendships WHERE userID = " + "\"" + userId + "\"");
			
			while(rs.next()) {
				friendIDs.add(rs.getInt("friendID"));
			}
			
			for(int j: friendIDs) {
				allFriends.add(getUserById(j));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allFriends;
	}
	
	static public User getUserById(int userId) {
		User user = new User();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE ID = " + "\"" + userId + "\"");
			
			if(rs.next()) {
				user.setUserid(rs.getInt("ID"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setAdminStatus(rs.getBoolean("admin"));
				user.setNumQuizzesCreated(rs.getInt("numQuizzesCreated"));
				user.setNumQuizzesTaken(rs.getInt("numQuizzesTaken"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	public static int getNumberOfUsers(){
		int numUsers = 0;
		try {
			String command = "SELECT COUNT(*) AS numUsers FROM users";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			if(rs.next()) numUsers = rs.getInt("numUsers");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return numUsers;
	}
	
}
