package quiz;

import java.sql.*;
import java.util.*;
import java.sql.Connection;

public class QuizTakenDao {
	
	private static Connection connection = Database.connect();
	
	public static void addQuizTaken(QuizTaken quizTaken) {
		try {
<<<<<<< HEAD
			PreparedStatement prepStmt = connection.prepareStatement("INSERT INTO quizzes_taken(user_id, quiz_id, timeTakingQuiz, scoreOnQuiz, timeElapsed) VALUES (?,?,?,?)");
			prepStmt.setInt(1, quizTaken.getUserID());
			prepStmt.setInt(2, quizTaken.getQuizID());
			prepStmt.setLong(3, quizTaken.getTimeTakingQuiz());
			prepStmt.setInt(4, quizTaken.getScore());
			prepStmt.setLong(5, quizTaken.getTimeElapsed());
=======
			PreparedStatement prepStmt = connection.prepareStatement("INSERT INTO quizzes(id, score, name, user_id) VALUES (?,?,?,?)");
			//prepStmt.setInt(1, quizTaken.getID());
			prepStmt.setInt(2, quizTaken.getScore());
			//prepStmt.setString(3, quizTaken.getName());
			prepStmt.setInt(4, quizTaken.getUserID());
>>>>>>> c851686834e59179541009bad960bbf1f6df2404

			prepStmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//gets quizzes taken within the last x minutes
	public static void getUserRecentQuizzesTaken(int user_id, int min) {
		try {
			long currentTime = System.currentTimeMillis();
			long milliseconds = min * 60000; // 60000 equals number of milliseconds in a minute.
			String command = "SELECT * FROM quizzes_taken WHERE user_id = " + user_id + " AND timeTaken + " + milliseconds + " >= " + currentTime;
			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
