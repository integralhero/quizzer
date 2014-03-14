package quiz;

import java.sql.*;
import java.util.*;
import java.sql.Connection;

public class TagDao {
	
	private static Connection connection = Database.connect();
	
	public static ArrayList<Quiz> getQuizzesFromTag(String tag) {
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
		try {
			
			String command = "SELECT * FROM tags WHERE tag = " + tag;
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			ArrayList<Integer> quizIDs = new ArrayList<Integer>();
			
			while(rs.next()){
				quizIDs.add(rs.getInt("quizID"));
			}
			
			if(quizIDs.size() == 0) return null;
			
			for (int i = 0; i < quizIDs.size(); i++) {
				quizzes.add(QuizDao.getQuizByID(quizIDs.get(i)));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return quizzes;
	}
}
