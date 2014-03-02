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
				tmpUser.setUserid(rs.getInt("id"));
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
		System.out.println("Username: "+username);
		try {
			System.out.println("in checkusernameexists");
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT 1 FROM users WHERE username = " + "\"" + username + "\"");
			//boolean a = rs.next();
			//while (rs.next()) System.out.println(rs.getString("username"));
//			System.out.println(rs == null);
			boolean more = rs.next();

			System.out.println(more);
			if (!more) {
				System.out.println("User does not exist.");
				return false;
			} else {
				System.out.println("User does exist.");
				return true;
			}
		
		}

       catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("HAHAHAH");
		return false;
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
			prepStmt = connection.prepareStatement("delete from users where userid=?");
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
			statement.execute("UPDATE users SET admin = 1 WHERE id="+userId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void demoteUser(int userId) {
		try {
			Statement statement = connection.createStatement();
			statement.execute("UPDATE users SET admin = 0 WHERE id="+userId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static public void updateUser(User user) {
		try {
			PreparedStatement prepStmt = connection.prepareStatement("update users set username=?, password=?, email=? "+ "where userid=?" );
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
			String command = "SELECT * FROM users WHERE id =" + userid;
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
	
	static public int validateUser(User user) {
		
		int id = -1;
		try {
			String username = user.getUsername();
			String password = user.getPassword();
			String command = "SELECT * FROM users WHERE username =\"" + username + "\" AND password = \"" + password  + "\"";
			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			if(rs.next()) {
				id = rs.getInt("id");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return id;

	}

	static public List<User> getAllUser() {
		List<User> users = new ArrayList<User>();
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from users");
			while(rs.next()) {
				User user = new User();
				user.setUserid(rs.getInt("userid"));
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
	
	static public User getUserById(int userId) {
		User user = new User();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE id = " + "\"" + userId + "\"");
			
			if(rs.next()) {
				user.setUserid(rs.getInt("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setAdminStatus(rs.getBoolean("admin"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
}
