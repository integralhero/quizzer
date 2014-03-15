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
	
	public static int getScore(int questionID, String type) {
		try {
			String command = "SELECT * FROM "  + type +  " WHERE ID=" + questionID;
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(command);
			if(rs.next()) return rs.getInt("score");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
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
	public static void removeByQuizID(int id) {
		try {
			String command = "DELETE FROM q_fill_in_blank WHERE quizID=" + id;
			Statement statement = connection.createStatement();
			statement.executeUpdate(command);	
			String command2 = "DELETE FROM q_multiple_choice WHERE quizID=" + id;
			Statement statement2 = connection.createStatement();
			statement2.executeUpdate(command2);
			String command3 = "DELETE FROM q_picture_response WHERE quizID=" + id;
			Statement statement3 = connection.createStatement();
			statement3.executeUpdate(command3);
			String command4 = "DELETE FROM q_question_response WHERE quizID=" + id;
			Statement statement4 = connection.createStatement();
			statement4.executeUpdate(command4);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void updateQuestionsID(int oldID, int newID) {
		
		try {
			String	command = "UPDATE question_quiz_index SET quizID="+ newID  +" WHERE quizID=" + oldID;
			Statement statement = connection.createStatement();
			statement.execute(command);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}



}
