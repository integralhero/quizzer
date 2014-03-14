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
			
			incrementNumberOfTimesTaken(quizTaken.getQuizID());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void deleteQuizTakenByID(int id) {
		
		try {
			String command = "DELETE FROM quizzes_taken WHERE quizID =" + id;
			
			Statement statement = connection.createStatement();
			statement.executeUpdate(command);	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public static void incrementNumberOfTimesTaken(int quizID){
		try {
			String commandq = "SELECT * FROM quizzes WHERE ID =" + quizID;
			Statement statementq = connection.createStatement();
			ResultSet rs = statementq.executeQuery(commandq);
			int times = 0;
			if(rs.next()) times = rs.getInt("numTimesTaken") + 1;
			
			String command = "UPDATE quizzes SET numTimesTaken =" + times + " WHERE ID =" + quizID;
			Statement statement = connection.createStatement();
			statement.executeUpdate(command);
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<QuizTaken> getAllQuizzesTakenByUser(int userID) {
		ArrayList<QuizTaken> quizzes = new ArrayList<QuizTaken> ();

		try {
			
			String command = "SELECT * FROM quizzes_taken WHERE userID = " + userID;
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			
			while(rs.next()){
				String timeTaken = rs.getString("timeTaken");
				int score = rs.getInt("score");
				int timeElapsed = rs.getInt("timeElapsed");
				int quizID = rs.getInt("quizID");
				QuizTaken temp = new QuizTaken(userID, quizID, timeTaken, score, timeElapsed);
				quizzes.add(temp);
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return quizzes;
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

	public static ArrayList<QuizTaken> getRecentQuizzesByUser(int userid, int quizID) {
		
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
			
			return recentQuizzes;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//returns list of top 5 scorers for a quiz. rank top scores first by number of questions correct, then
	// by time taken if needed.
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
			
			Collections.sort(quizzes);
			if (quizzes.size() <= 5) return quizzes;
			else {
				while(quizzes.size() > 5) {
					quizzes.remove(0);
				}
				return quizzes;
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
	private static final int ONE_DAY_MS = 86400000;
	
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
				System.out.println("Time Taken is " + timeTakenMilliseconds);
				
				long currentTime = System.currentTimeMillis();
				System.out.println("Current time is " + currentTime);
				System.out.println();
				if(timeTakenMilliseconds > currentTime - ONE_DAY_MS){
					
					QuizTaken temp = new QuizTaken(userID, quizID, timeTaken, score, timeElapsed);
					quizzes.add(temp);
				}
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("quizzes size is " + quizzes.size());
		return quizzes;
		
	}
	
	public static ArrayList<QuizTaken> getFriendsTakenQuizzes(int userID){
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
				String timeTaken = rs2.getString("timeTaken");
				int score = rs2.getInt("score");
				int timeElapsed = rs2.getInt("timeElapsed");
				int quizID = rs2.getInt("quizID");
				QuizTaken temp = new QuizTaken(userID, quizID, timeTaken, score, timeElapsed);				
				recentQuizzes.add(temp);
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recentQuizzes;
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

	public static boolean checkIfHighScore(int quizID, int scoreToCheck){
		try {
			String command = "SELECT * FROM quizzes_taken WHERE quizID = " +  quizID + " ORDER BY score DESC";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			if(rs.next()){
				int score = rs.getInt("score");
				if(scoreToCheck > score) return true;
			} else {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static void updateQuizesTakenID(int oldID, int newID) {
		
		try {
			String command = "UPDATE quizzes_taken SET quizID = newID WHERE quizID = oldID";
			Statement statement = connection.createStatement();
			statement.execute(command);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
