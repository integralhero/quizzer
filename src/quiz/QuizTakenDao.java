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
	public static ArrayList<QuizTaken> getRecentQuizzesByUserID(int userID) {
		ArrayList<QuizTaken> recentQuizzes = new ArrayList<QuizTaken> ();

		try {			
			String command = "SELECT * FROM quizzes_taken WHERE userID = " 
					+ userID;
			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			rs.afterLast();
			while (rs.previous() && recentQuizzes.size() < 10) {
				int quizID = rs.getInt("quizID");
				String timeTaken = rs.getString("timeTaken");
				int score = rs.getInt("score");
				int timeElapsed = rs.getInt("timeElapsed");
				QuizTaken temp = new QuizTaken(userID, quizID, timeTaken, score, timeElapsed);
				recentQuizzes.add(temp);
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recentQuizzes;
	}

	public static ArrayList<QuizTaken> getRecentQuizzesByQuizID(int quizID) {
		
		ArrayList<QuizTaken> recentQuizzes = new ArrayList<QuizTaken> ();

		try {
			
			String command = "SELECT * FROM quizzes_taken WHERE quizID = " 
					+ quizID;
			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			rs.afterLast();
			
			while (rs.previous() && recentQuizzes.size() < 10) {
				int userID = rs.getInt("userID");
				String timeTaken = rs.getString("timeTaken");
				int score = rs.getInt("score");
				int timeElapsed = rs.getInt("timeElapsed");;
				QuizTaken temp = new QuizTaken(userID, quizID, timeTaken, score, timeElapsed);
				recentQuizzes.add(temp);
			}
			
			return recentQuizzes;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList<QuizTaken> getHighScores(int quizID){
		
		ArrayList<QuizTaken> quizzes = new ArrayList<QuizTaken> ();

		try {
			
			String command = "SELECT * FROM quizzes_taken WHERE quizID = " + quizID + " ORDER BY score DESC";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			
			while(rs.next() && quizzes.size() < 10){
				String timeTaken = rs.getString("timeTaken");
				int score = rs.getInt("score");
				int timeElapsed = rs.getInt("timeElapsed");
				int userID = rs.getInt("userID");
				QuizTaken temp = new QuizTaken(userID, quizID, timeTaken, score, timeElapsed);
				quizzes.add(temp);
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return quizzes;
	}
	
	public static double getAverageScore(int quizID){
		double average = 0;
		try {
			
			String command = "SELECT * FROM quizzes_taken WHERE quizID = " + quizID;
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			ArrayList<Integer> results = new ArrayList<Integer>();
			while(rs.next()){
				int score = rs.getInt("score");
				results.add(score);
			}
			
			for(int i = 0; i < results.size(); i++){
				average += results.get(i);
			}
			
			average = average / results.size();
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return average;
	}
	
	public static double getAverageTimeElapsed(int quizID){
		double average = 0;
		
		try {
			
			String command = "SELECT * FROM quizzes_taken WHERE quizID = " + quizID;
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			ArrayList<Integer> results = new ArrayList<Integer>();
			while(rs.next()){
				int timeElapsed = rs.getInt("timeElapsed");
				results.add(timeElapsed);
			}
			
			for(int i = 0; i < results.size(); i++){
				average += results.get(i);
			}
			
			average = average / results.size();
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return average;
	}
	
	private static final int FIFTEEN_MINUTES_MS = 900000;
	private static final int ONE_DAY_MS = 864 * (10 ^ 5);
	
	public static ArrayList<QuizTaken> getTodaysHighScores(int quizID){
			
		ArrayList<QuizTaken> quizzes = new ArrayList<QuizTaken> ();

		try {
			
			String command = "SELECT * FROM quizzes_taken WHERE quizID = " + quizID + " ORDER BY score DESC";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			
			while(rs.next() && quizzes.size() < 10){
				String timeTaken = rs.getString("timeTaken");
				int score = rs.getInt("score");
				int timeElapsed = rs.getInt("timeElapsed");
				int userID = rs.getInt("userID");
				
				long timeTakenMilliseconds = ParseDateString.getMilliseconds(timeTaken);
				if(timeTakenMilliseconds > System.currentTimeMillis() - FIFTEEN_MINUTES_MS){
					QuizTaken temp = new QuizTaken(userID, quizID, timeTaken, score, timeElapsed);
					quizzes.add(temp);
				}
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return quizzes;
		
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
