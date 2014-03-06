package quiz;

import java.sql.*;

public class QuestionDao {
	private static Connection connection = Database.connect();

	public static int getID(Question question){
		int id = 0;
		try {
			String command = "SELECT * FROM " + question.type + " WHERE answers = \"" + ParseAnswers.getString(question.answers) + "\"";

			Statement statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(command);	
			if(rs.next()) id = rs.getInt("id");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
	
	public static String getTypeFromID(int question_id) {
		try {
			String command = "SELECT * FROM question_quiz_index WHERE questionID="+question_id;

			Statement statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(command);	
			if(rs.next()) return rs.getString("questionType");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	/*
	 * Question Response
	 */
	public static String getQuestion(int questionID, String type) {
		try {
			String command = "SELECT * FROM "  + type +  " WHERE ID=" + questionID;
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(command);
			if(rs.next()) return rs.getString("question");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public static String getAnswers(int questionID, String type) {
		try {
			String command = "SELECT * FROM "  + type +  " WHERE ID=" + questionID;
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(command);
			if(rs.next()) return rs.getString("answers");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public static String getChoices(int questionID) {
		try {
			String command = "SELECT * FROM q_multiple_choice WHERE ID=" + questionID;
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(command);
			if(rs.next()) return rs.getString("choices");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	
	/*
	 * Picture Response
	 */
	public static String getImageURL(int questionID) {
		try {
			String command = "SELECT * FROM q_picture_response WHERE ID=" + questionID;
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(command);
			if(rs.next()) return rs.getString("pictureURL");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	

}
