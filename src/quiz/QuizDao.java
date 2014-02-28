package quiz;

import java.sql.*;
import java.util.*;
import java.sql.Connection;

public class QuizDao {
	private static Connection connection = Database.connect();

	public static void deleteQuiz(Quiz quiz) {
		
		boolean check = false;
		
		try {
			int id = quiz.getID();
			String command = "DELETE FROM quizzes WHERE id =" + id;
			
			Statement statement = connection.createStatement();
			statement.executeUpdate(command);	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
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
	
	public static ArrayList<Quiz> getUserQuizzes(int user_id){
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
		
		try {
			String command = "SELECT * FROM quizzes WHERE user_id =" + user_id;
			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			
			ArrayList<Integer> quizIDs = new ArrayList<Integer>();
			
			while(rs.next()){
				int temp = rs.getInt("id");
				quizIDs.add(temp);
			}
			
			for(int i = 0; i < quizIDs.size(); i++){
				Quiz temp = new Quiz();
				
				command = "SELECT 1 FROM quizzes WHERE id = " + quizIDs.get(i);
				rs = statement.executeQuery(command);
				
				temp.setScore(rs.getInt("score"));
				temp.setName(rs.getString("name"));
				temp.setUserID(rs.getInt("user_id"));
				temp.setID(quizIDs.get(i));
				
				quizzes.add(temp);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return quizzes;
	}
	
	public static Quiz searchForQuiz(String quizName){
		Quiz quiz = new Quiz();

		try {
			String command = "SELECT 1 FROM quizzes WHERE name =" + quizName;
			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			
			quiz.setScore(rs.getInt("score"));
			quiz.setName(rs.getString("name"));
			quiz.setUserID(rs.getInt("user_id"));
			quiz.setID(rs.getInt("id"));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return quiz;

	}
	
	public static boolean checkName(String quizName){
		boolean nameFree = true;
		try {
			String command = "SELECT 1 FROM quizzes WHERE name =" + quizName;
			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			
			if(rs.next()) nameFree = false;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return nameFree;
	}
}
