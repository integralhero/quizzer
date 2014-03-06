package quiz;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AchievementDao {
	
	private static Connection connection = Database.connect();

	public static void addAchievement(Achievement temp){
		
		try {
			String image = temp.getImage();
			String name = temp.getName();
			String description = temp.getDescription();
			
			String command = "INSERT INTO achievements(image, name, description) VALUES (" 
					+ image + "," + name + "," + description + ")";
			
			Statement statement = connection.createStatement();
			statement.executeUpdate(command);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void setAchievements(){
		Achievement firstQuizCreation  = new Achievement("image.jpg", "Amateur Author", "Created your first quiz.");
		Achievement fiveQuizCreation = new Achievement("image.jpg", "Prolific Author", "Created five quizzes.");
		Achievement tenQuizCreation = new Achievement("image.jpg", "Prodigious Author", "Created ten quizzes.");
		Achievement tenQuizTaken = new Achievement("image.jpg", "Quiz Machine", "Took ten quizzes.");
		Achievement highestScore = new Achievement("image.jpg", "I Am the Greatest", "Highest score on a quiz.");
		Achievement practiceMode = new Achievement("image.jpg", "Practice Makes Perfect", "Took a quiz in practice mode.");
		
		addAchievement(firstQuizCreation);
		addAchievement(fiveQuizCreation);
		addAchievement(tenQuizCreation);
		addAchievement(tenQuizTaken);
		addAchievement(highestScore);
		addAchievement(practiceMode);
	}
	
}
