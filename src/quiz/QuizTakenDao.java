package quiz;

import java.sql.*;
import java.util.*;
import java.sql.Connection;

public class QuizTakenDao {
	
	private static Connection connection = Database.connect();
	
	public static void addQuiz(Quiz quiz) {
		try {
			PreparedStatement prepStmt = connection.prepareStatement("INSERT INTO quizzes(id, score, name, user_id) VALUES (?,?,?,?)");
			prepStmt.setInt(1, quiz.getID());
			prepStmt.setInt(2, quiz.getScore());
			prepStmt.setString(3, quiz.getName());
			prepStmt.setInt(4, quiz.getUserID());

			prepStmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
