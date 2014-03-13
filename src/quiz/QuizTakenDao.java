package quiz;

import java.sql.*;
import java.util.*;
import java.sql.Connection;

public class QuizTakenDao {
	
	private static Connection connection = Database.connect();
	
	public static void addQuizTaken(QuizTaken quizTaken) {
		if(connection == null){
			connection = Database.connect();
		}
		try {
			PreparedStatement prepStmt = connection.prepareStatement("INSERT INTO quizzes_taken(userID, quizID, timeTaken, score, timeElapsed) VALUES (?,?,?,?,?)");
			prepStmt.setInt(1, quizTaken.getUserID());
			prepStmt.setInt(2, quizTaken.getQuizID());
			prepStmt.setString(3, quizTaken.getTimeTakingQuiz());
			prepStmt.setInt(4, quizTaken.getScore());
			prepStmt.setLong(5, quizTaken.getTimeElapsed());
			
			prepStmt.executeUpdate();
			
			incrementNumberOfTimesTaken(quizTaken.getQuizID());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void deleteQuizTakenByID(int id) {
		if(connection == null){
			connection = Database.connect();
		}
		try {
			String command = "DELETE FROM quizzes_taken WHERE quizID =" + id;
			
			Statement statement = connection.createStatement();
			statement.executeUpdate(command);	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public static void incrementNumberOfTimesTaken(int quizID){
		if(connection == null){
			connection = Database.connect();
		}
		try {
			
			String command = "UPDATE quizzes SET numTimesTaken = numTimesTaken + 1 WHERE quizID =" + quizID;
			Statement statement = connection.createStatement();
			statement.executeUpdate(command);
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ArrayList<QuizTaken> getQuizTakenByUser(int userID, int quizID){
		if(connection == null){
			connection = Database.connect();
		}
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
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return quizzes;
	}
	
	//gets 10 most recent quizzes taken by user 
	public static ArrayList<QuizTaken> getRecentQuizzesByUserID(int userID) {
		if(connection == null){
			connection = Database.connect();
		}
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
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return recentQuizzes;
	}

	public static ArrayList<QuizTaken> getRecentQuizzesByUser(int userid, int quizID) {
		if(connection == null){
			connection = Database.connect();
		}
		
		ArrayList<QuizTaken> recentQuizzes = new ArrayList<QuizTaken> ();

		try {
			
			String command = "SELECT * FROM quizzes_taken WHERE quizID = " 
					+ quizID + " AND userID=" + userid;
			
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
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return recentQuizzes;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//returns list of top 5 scorers for a quiz. rank top scores first by number of questions correct, then
	// by time taken if needed.
	public static ArrayList<QuizTaken> getHighScores(int quizID){
		
		if(connection == null){
			connection = Database.connect();
		}
		
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
			
			Collections.sort(quizzes);
			if (quizzes.size() <= 5) return quizzes;
			else {
				while(quizzes.size() > 5) {
					quizzes.remove(0);
				}
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return quizzes;
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return quizzes;
	}
	
	public static double getAverageScore(int quizID){
		
		if(connection == null){
			connection = Database.connect();
		}
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
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return average;
	}
	
	public static double getAverageTimeElapsed(int quizID){
		
		if(connection == null){
			connection = Database.connect();
		}
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
		
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return average;
	}
	
	private static final int FIFTEEN_MINUTES_MS = 900000;
	private static final int ONE_DAY_MS = 864 * (10 ^ 5);
	
	public static ArrayList<QuizTaken> getTodaysHighScores(int quizID){
		if(connection == null){
			connection = Database.connect();
		}
			
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
		
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return quizzes;
		
	}
	
	public static ArrayList<QuizTaken> getFriendsTakenQuizzes(int userID){
		if(connection == null){
			connection = Database.connect();
		}
		ArrayList<QuizTaken> recentQuizzes = new ArrayList<QuizTaken> ();

		try {
			String command = "SELECT * FROM friendships WHERE userID = " 
					+ userID;
			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			
			ArrayList<Integer> friendIDs = new ArrayList<Integer>();
			while(rs.next()){
				friendIDs.add(rs.getInt("friendID"));
			}
			
			if(friendIDs.size() == 0) return null;
			String command2 = "SELECT * FROM quizzes_taken WHERE userID = " + friendIDs.get(0);
			
			for(int i = 1; i < friendIDs.size(); i++){
				command2 += " OR userID = " + friendIDs.get(i);
			}
			
			Statement statement2 = connection.createStatement();
			ResultSet rs2 = statement2.executeQuery(command2);
			
			rs2.afterLast();
			
			while (rs2.previous() && recentQuizzes.size() < 10) {
				String timeTaken = rs.getString("timeTaken");
				int score = rs.getInt("score");
				int timeElapsed = rs.getInt("timeElapsed");
				int quizID = rs.getInt("quizID");
				QuizTaken temp = new QuizTaken(userID, quizID, timeTaken, score, timeElapsed);				
				recentQuizzes.add(temp);
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return recentQuizzes;
	}
	
	public static int getNumberOfQuizzesTaken(){
		if(connection == null){
			connection = Database.connect();
		}
		int numTaken = 0;
		try {
			String command = "SELECT COUNT(*) AS number FROM quizzes_taken";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			if(rs.next()) numTaken = rs.getInt("number");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return numTaken;
	}
}
