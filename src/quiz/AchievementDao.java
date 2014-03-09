package quiz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
