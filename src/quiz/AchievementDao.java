package quiz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AchievementDao {
	
	private static Connection connection = Database.connect();

	/*public static void addAchievement(Achievement temp){
		
		try {
			String image = temp.getImage();
			String name = temp.getName();
			String description = temp.getDescription();
			
			String command = "INSERT INTO achievements(image, name, description, 0) VALUES (" 
					+ image + "," + name + "," + description + ")";
			
			Statement statement = connection.createStatement();
			statement.executeUpdate(command);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	public static void giveAchievement(int userID, int achievementID){
		try {

			String command = "INSERT INTO achievement_user_index(userID, achievementID) VALUES (" 
					+ userID + "," + achievementID + ")";
			
			Statement statement = connection.createStatement();
			statement.executeUpdate(command);
			
			updateNumUsers(achievementID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void updateNumUsers(int achievementID){
		try {
			String command = "UPDATE achievements SET numUsers = numUsers + 1 WHERE ID = " + achievementID; 
			
			Statement statement = connection.createStatement();
			statement.executeUpdate(command);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static ArrayList<Achievement> getAchievements(int userID){
		ArrayList<Achievement> temp = new ArrayList<Achievement>();
		
		try {

			String command = "SELECT * FROM achievement_user_index WHERE userID = " + userID;
			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			
			while(rs.next()){
				String command2 = "SELECT * FROM achievements WHERE ID = " + rs.getInt("achievementID");
			
				Statement statement2 = connection.createStatement();
				ResultSet rs2 = statement2.executeQuery(command2);
				while(rs2.next()){
					temp.add(getAchievementByID(rs2.getInt("ID")));
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return temp;
	}
	
	private static Achievement getAchievementByID(int ID){
		Achievement temp = new Achievement("","","");
		try {

			String command = "SELECT * FROM achievement WHERE ID = " + ID;
			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			if(rs.next()){
				String image = rs.getString("image");
				String name = rs.getString("name");
				String description = rs.getString("description");
				temp = new Achievement(image, name, description);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return temp;
	}
	
	
}
