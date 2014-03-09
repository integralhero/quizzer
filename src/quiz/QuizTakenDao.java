package quiz;

import java.sql.*;
import java.util.*;
import java.sql.Connection;

public class QuizTakenDao {
	
	private static Connection connection = Database.connect();
	
	public static void addQuizTaken(QuizTaken quizTaken) {
		try {
			PreparedStatement prepStmt = connection.prepareStatement("INSERT INTO quizzes_taken(user_id, quiz_id, timeTakingQuiz, scoreOnQuiz, timeElapsed) VALUES (?,?,?,?)");
			prepStmt.setInt(1, quizTaken.getUserID());
			prepStmt.setInt(2, quizTaken.getQuizID());
			prepStmt.setLong(3, quizTaken.getTimeTakingQuiz());
			prepStmt.setInt(4, quizTaken.getScore());
			prepStmt.setLong(5, quizTaken.getTimeElapsed());
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
	
	public static int getNumberOfQuizzesTaken(){
		int numTaken = 0;
		try {
			String command = "SELECT COUNT(*) AS number FROM quizzes_taken";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			if(rs.next()) numTaken = rs.getInt("number");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return numTaken;
	}
}
