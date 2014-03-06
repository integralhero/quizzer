package quiz;

import java.sql.*;
import java.util.*;
import java.sql.Connection;

public class QuizTakenDao {
	
	private static Connection connection = Database.connect();
	
	public static void addQuiz(QuizTaken quizTaken) {
		try {
			PreparedStatement prepStmt = connection.prepareStatement("INSERT INTO quizzes(id, score, name, user_id) VALUES (?,?,?,?)");
			prepStmt.setInt(1, quizTaken.getQuizID());
			prepStmt.setInt(2, quizTaken.getScore());
			prepStmt.setString(3, quizTaken.getQuizName());
			prepStmt.setInt(4, quizTaken.getUserID());

			prepStmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
