package quiz;

import java.sql.*;
import java.util.*;
import java.sql.Connection;

public class QuizTakenDao {
	
	private static Connection connection = Database.connect();
	
	public static void addQuizTaken(QuizTaken quizTaken) {
		try {
			PreparedStatement prepStmt = connection.prepareStatement("INSERT INTO quizzes_taken(userID, quizID, timeTaken, score, timeElapsed) VALUES (?,?,?,?,?)");
			prepStmt.setInt(1, quizTaken.getUserID());
			prepStmt.setInt(2, quizTaken.getQuizID());
			prepStmt.setString(3, quizTaken.getTimeTakingQuiz());
			prepStmt.setInt(4, quizTaken.getScore());
			prepStmt.setLong(5, quizTaken.getTimeElapsed());
			
			prepStmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ArrayList<QuizTaken> getQuizTakenByUser(int userID, int quizID){
		ArrayList<QuizTaken> quizzes = new ArrayList<QuizTaken> ();

		try {
			
			String command = "SELECT * FROM quizzes_taken WHERE userID = " + userID + " AND quizID = " + quizID;
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			
			while(rs.next()){
				String timeTaken = rs.getString("timeTaken");
				int score = rs.getInt("score");
				int timeElapsed = rs.getInt("timeElapsed");
				QuizTaken temp = new QuizTaken(userID, quizID, timeTaken, score, timeElapsed);
				quizzes.add(temp);
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return quizzes;
	}
	
	//gets 10 most recent quizzes taken by user 
	public static ArrayList<Quiz> getUserRecentQuizzesTaken(int userID) {
		try {
			ArrayList<Quiz> recentQuizzes = new ArrayList<Quiz> ();
			
			String command = "SELECT * FROM quizzes_taken WHERE userID = " 
					+ userID;
			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			rs.afterLast();
			int quizCount = 0;
			while (rs.previous() && quizCount < 10) {
				quizCount++;
				int quizID = rs.getInt("quizID");
				Quiz recentQuiz = QuizDao.getQuizByID(quizID);
				recentQuizzes.add(recentQuiz);
			}
			
			return recentQuizzes;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
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
