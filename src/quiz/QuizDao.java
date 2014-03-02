package quiz;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.sql.Connection;
import java.text.*;

public class QuizDao {
	private static Connection connection = Database.connect();

	public static void deleteQuiz(Quiz quiz) {
				
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
			PreparedStatement prepStmt = connection.prepareStatement("INSERT INTO quizzes(score, name, user_id, numTimesTaken, timeCreated, description, category) VALUES (?,?,?,?,?,?,?)");
			prepStmt.setInt(1, quiz.getScore());
			prepStmt.setString(2, quiz.getName());
			prepStmt.setInt(3, quiz.getUserID());
			prepStmt.setInt(4, 0);
			prepStmt.setString(5, getCurrentTimeStamp());
			prepStmt.setString(6,  quiz.getDescription());
			prepStmt.setString(7, quiz.getCategory());

			prepStmt.executeUpdate();
			
			updateUserTable(quiz, UserDao.getUserById(quiz.getUserID()));
			for(int i = 0; i < quiz.questions.size(); i++){
				updateQuestionTables(quiz, quiz.questions.get(i));
				updateQuizQuestionIndexTable(quiz, quiz.questions.get(i));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private static void updateUserTable(Quiz quiz, User user) {
		try {
			
			String command = "SELECT numQuizzesTaken FROM users WHERE id = " + user.getUserid();

			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			int numQuizzes = rs.getInt("numQuizzesTaken");
			
			String command2 = "UPDATE users SET numQuizzesTaken = " + numQuizzes + 1 + "WHERE id = " + user.getUserid();
			Statement statement2 = connection.createStatement();
			statement2.executeUpdate(command2);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}

	
	private static void updateQuestionTables(Quiz quiz, Question question) {
		try {
			switch (question.type) {
			case "QuestionResponse":
				PreparedStatement prepStmt = connection.prepareStatement(
						"INSERT INTO" + question.type + "(question, answers, quiz_id) VALUES (?,?,?)");
				prepStmt.setString(1, ((QuestionResponse)question).question);
				prepStmt.setString(2, ParseAnswers.getString(question.answers));
				prepStmt.setInt(3, quiz.getID());

				prepStmt.executeUpdate();
				break;
			case "PictureResponse":
				PreparedStatement prepStmt2 = connection.prepareStatement(
						"INSERT INTO" + question.type + "(pictureURL, answers, quiz_id) VALUES (?,?,?)");
				prepStmt2.setString(1, ((PictureResponseQuestion)question).imageURL);
				prepStmt2.setString(2, ParseAnswers.getString(question.answers));
				prepStmt2.setInt(3, quiz.getID());

				prepStmt2.executeUpdate();
				break;
				
			case "MultipleChoice":
				PreparedStatement prepStmt3 = connection.prepareStatement(
						"INSERT INTO" + question.type + "(question, choices, answers, quiz_id) VALUES (?,?,?,?)");
				prepStmt3.setString(1, ((MultipleChoiceQuestion)question).question);
				prepStmt3.setString(2, ParseAnswers.getString(((MultipleChoiceQuestion)question).choices));
				prepStmt3.setString(3, ParseAnswers.getString(question.answers));
				prepStmt3.setInt(4, quiz.getID());

				prepStmt3.executeUpdate();
				break;
				
			case "FillInBlank":
				PreparedStatement prepStmt4 = connection.prepareStatement(
						"INSERT INTO" + question.type + "(question, answers, quiz_id) VALUES (?,?,?)");
				prepStmt4.setString(1, ((FillBlankQuestion)question).question);
				prepStmt4.setString(2, ParseAnswers.getString(question.answers));
				prepStmt4.setInt(3, quiz.getID());

				prepStmt4.executeUpdate();
				break;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private static void updateQuizQuestionIndexTable(Quiz quiz, Question question) {
		try {
			PreparedStatement prepStmt = connection.prepareStatement("INSERT INTO QuestionQuizIndex(question_id, quiz_id, question_type) VALUES (?,?,?)");
			prepStmt.setInt(1,QuestionDao.getID(question));
			prepStmt.setInt(2, quiz.getID());
			prepStmt.setString(3, question.type);

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
				
				command = "SELECT * FROM quizzes WHERE id = " + quizIDs.get(i);
				Statement statement2 = connection.createStatement();
				ResultSet rs2 = statement2.executeQuery(command);
				
				rs2.next();
				
				temp.setScore(rs2.getInt("score"));
				temp.setName(rs2.getString("name"));
				temp.setUserID(rs2.getInt("user_id"));
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
			String command = "SELECT * FROM quizzes WHERE name = \"" + quizName + "\"";
			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			
			if(rs.next()) {
			
				quiz.setScore(rs.getInt("score"));
				quiz.setName(rs.getString("name"));
				quiz.setUserID(rs.getInt("user_id"));
				quiz.setID(rs.getInt("id"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return quiz;

	}
	
	public static boolean checkName(String quizName){
		boolean nameFree = true;
		try {
			String command = "SELECT * FROM quizzes WHERE name = \"" + quizName + " \"";
			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			
			if(rs.next()) nameFree = false;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return nameFree;
	}
	
	public static String getCurrentTimeStamp() {
	    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	}
}
