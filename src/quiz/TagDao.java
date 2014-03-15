package quiz;

import java.sql.*;
import java.util.*;
import java.sql.Connection;

public class TagDao {
	
	private static Connection connection = Database.connect();
	
	public static ArrayList<Quiz> getQuizzesFromTag(String tag) {
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
		try {
			
			String command = "SELECT * FROM tags WHERE tag = '" + tag + "'";
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
	
	public static void updateTagsWith(int oldID, int newID) {
		try {
			
			String command = "UPDATE tags SET quizID=" + newID + " WHERE quizID=" + oldID;
			Statement statement = connection.createStatement();
			statement.executeUpdate(command);
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String[] getTagsFromQuizID(int quizID) {
		ArrayList<String> tags = new ArrayList<String>();
		try {
			
			String command = "SELECT * FROM tags WHERE quizID=" + quizID;
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			
			while(rs.next()){
				tags.add(rs.getString("tag"));
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tags.toArray(new String[tags.size()]);
	}
}
