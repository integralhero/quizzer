package quiz;

import java.sql.*;
import java.util.*;

public class UserDao {
	private static Connection connection = Database.connect();
	
	
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
			PreparedStatement prepStmt = connection.prepareStatement("insert into users(username, password, email) values (?,?,?)");
			prepStmt.setString(1, user.getUsername());
			prepStmt.setString(2, user.getPassword());
			prepStmt.setString(3, user.getEmail());
			prepStmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteUser(int userId) {
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
	
	public void updateUser(User user) {
		try {
			PreparedStatement prepStmt = connection.prepareStatement("update users set username=?, password=?, email=? "+ "where userid=?" );
			prepStmt.setString(1, user.getUsername());
			prepStmt.setString(2, user.getPassword());
			prepStmt.setString(4, user.getEmail());
			prepStmt.setInt(3, user.getUserid());
			prepStmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<User> getAllUser() {
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
				users.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}
	
	public User getUserById(int userId) {
		User user = new User();
		try {
			PreparedStatement prepStmt = connection.prepareStatement("select * from users where userid=?");
			prepStmt.setInt(1, userId);
			ResultSet rs = prepStmt.executeQuery();
			
			if(rs.next()) {
				user.setUserid(rs.getInt("userid"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
}
