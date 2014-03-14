package quiz;

import java.sql.*;
import java.util.*;

public class RatingsDao {
	
	private static Connection connection = Database.connect();
	
	public static void addRate(Rate rate) {
		if(connection == null) {
			connection = Database.connect();
		}
		try {
			PreparedStatement prepStmt = connection.prepareStatement("INSERT INTO ratings(userID, quizID, rating, review) VALUES (?,?,?,?)");
			prepStmt.setInt(1, rate.getUserID());
			prepStmt.setInt(2, rate.getQuizID());
			prepStmt.setInt(3, rate.getRating());
			prepStmt.setString(4, rate.getReview());
			prepStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<String> getReviewsOfQuiz(int quiz_id) {
		ArrayList<String> reviews = new ArrayList<String>();
		if(connection == null) {
			connection = Database.connect();
		}
		try {
			
			String command = "SELECT * FROM ratings WHERE quizID=" + quiz_id;
			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);	
			while (rs.next()) {
				String review = rs.getString("review");
				reviews.add(review);
			}
			return reviews;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//returns the average rating of a quiz.
	public static int getRating(int quiz_id) {
		if(connection == null) {
			connection = Database.connect();
		}
		try {
			String command = "SELECT * FROM ratings WHERE quizID=" + quiz_id;
			int totalRating = 0;
			int totalUsers = 0;
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);	
			while (rs.next()) {
				int rating = rs.getInt("rating");
				totalUsers++;
				totalRating += rating;
			}
			
			if (totalRating == 0 && totalUsers == 0) return 0;
			return totalRating/totalUsers;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	
}
