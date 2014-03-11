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
	
	public static Quiz getQuizByID(int quiz_id) {
		try {
			String command = "SELECT * FROM quizzes WHERE ID=" + quiz_id;
			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);	
			ArrayList<Question> list = getAllQuestionsFrom(quiz_id);
			Quiz tmp = new Quiz();
			tmp.setQuestions(list);
			tmp.setID(quiz_id);
			if(rs.next()) {
				tmp.setID(rs.getInt("ID"));
				tmp.setScore(rs.getInt("score"));;
				tmp.setName(rs.getString("name"));
				tmp.setUserID(rs.getInt("userID"));
				tmp.setNumTimesTaken(rs.getInt("numTimesTaken"));

				tmp.setTimeCreated(rs.getString("timeCreated"));
				tmp.setDescription(rs.getString("description"));
				tmp.setCategory(rs.getString("category"));
				tmp.setRandomQuestions(rs.getBoolean("randomizeQuestions"));
				tmp.setMultiplePages(rs.getBoolean("multiplePages"));
				tmp.setImmediateCorrect(rs.getBoolean("immediateCorrection"));
				tmp.setPracticeModeAvailable(rs.getBoolean("practiceModeAvailable"));
			}
			
			return tmp;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	public static void addQuiz(Quiz quiz) {
		try {
			
			PreparedStatement prepStmt = connection.prepareStatement("INSERT INTO quizzes(score, name, userID, numTimesTaken, timeCreated, description, category, randomizeQuestions, multiplePages, immediateCorrection, practiceModeAvailable) "
					+ "VALUES (?,?,?,?,?,?,?,?,?,?,?)");
			prepStmt.setInt(1, quiz.getScore());
			prepStmt.setString(2, quiz.getName());
			prepStmt.setInt(3, quiz.getUserID());
			prepStmt.setInt(4, 0);
			prepStmt.setString(5, getCurrentTimeStamp());
			prepStmt.setString(6,  quiz.getDescription());
			prepStmt.setString(7, quiz.getCategory());
			prepStmt.setBoolean(8, quiz.getRandomizeQuestions());
			prepStmt.setBoolean(9, quiz.getMultiplePages());
			prepStmt.setBoolean(10, quiz.getImmediateCorrection());
			prepStmt.setBoolean(11, quiz.getPracticeModeAvailable());


			prepStmt.executeUpdate();
			
			setQuizID(quiz);
			
			updateUserTable(quiz, UserDao.getUserById(quiz.getUserID()));
			for(int i = 0; i < quiz.questions.size(); i++){
				updateQuestionTables(quiz, quiz.questions.get(i));
				quiz.questions.get(i).ID = getLastInsertID(quiz.questions.get(i).type);
				updateQuizQuestionIndexTable(quiz, quiz.questions.get(i));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static int getLastInsertID(String type){
		try {
			String command = "SELECT id FROM " + type + " WHERE id = LAST_INSERT_ID();";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);

			if(rs.next()) return rs.getInt("id");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	private static void setQuizID(Quiz quiz){
		try {
			String command = "SELECT * FROM quizzes WHERE name = \"" + quiz.getName() + "\"";
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);

			if(rs.next()){
				quiz.setID(rs.getInt("id"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void updateUserTable(Quiz quiz, User user) {
		try {
			
			String command = "SELECT numQuizzesCreated FROM users WHERE ID = " + user.getUserid();

			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			if(rs.next()){
				int numQuizzes = rs.getInt("numQuizzesCreated");
				numQuizzes++;
				System.out.print(numQuizzes);
				String command2 = "UPDATE users SET numQuizzesCreated = " + numQuizzes + " WHERE ID = " + user.getUserid();
				Statement statement2 = connection.createStatement();
				statement2.executeUpdate(command2);
			}
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}
	
	public void deleteQuizHistory(int quiz_id){
		try {
			
			String command = "DELETE * FROM quizzes_taken WHERE ID = " + quiz_id;
			Statement statement = connection.createStatement();
			statement.executeUpdate(command);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
					
		}
	}
	
	private static ArrayList<Question> getAllQuestionsFrom(int quiz_id) {
		ArrayList<Question> allQuestions = new ArrayList<Question>();
		try {
			String command = "SELECT * FROM question_quiz_index WHERE quizID = " + quiz_id;
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			
			while(rs.next()) {
				String type = rs.getString("questionType");
				int questionID = rs.getInt("questionID");
				if(QuestionTypes.getType(type) == 1) {//QR
					String answer = QuestionDao.getAnswers(questionID, type);
					String question = QuestionDao.getQuestion(questionID, type);
					QuestionResponse tmp = new QuestionResponse(question, ParseAnswers.getArrayList(answer));
					tmp.setType(type);
					tmp.setID(questionID);
					allQuestions.add(tmp);
					
				}
				else if(QuestionTypes.getType(type) == 2) {//FB
					String answer = QuestionDao.getAnswers(questionID, type);
					String question = QuestionDao.getQuestion(questionID, type);
					FillBlankQuestion tmp = new FillBlankQuestion(question, ParseAnswers.getArrayList(answer));
					tmp.setType(type);
					tmp.setID(questionID);
					allQuestions.add(tmp);
				}
				
				else if(QuestionTypes.getType(type) == 3) {//MC
					String answer = QuestionDao.getAnswers(questionID, type);
					String question = QuestionDao.getQuestion(questionID, type);
					String choices = QuestionDao.getChoices(questionID);
					ArrayList<String> choicesAL = MultipleChoiceQuestion.unParseChoice(choices);
					MultipleChoiceQuestion tmp = new MultipleChoiceQuestion(question, choicesAL, ParseAnswers.getArrayList(answer));
					allQuestions.add(tmp);
				}
				
				else {//PR
					String answer = QuestionDao.getAnswers(questionID, type);
					String url = QuestionDao.getImageURL(questionID);
					PictureResponseQuestion tmp = new PictureResponseQuestion(url, ParseAnswers.getArrayList(answer));
					tmp.setType(type);
					tmp.setID(questionID);
					allQuestions.add(tmp);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allQuestions;
	}
	
	private static void updateQuestionTables(Quiz quiz, Question question) {
		try {
			switch (QuestionTypes.getType(question.type)) {
			case 1:
				PreparedStatement prepStmt = connection.prepareStatement(
						"INSERT INTO " + question.type + " (question, answers, quizID) VALUES (?,?,?)");
				prepStmt.setString(1, ((QuestionResponse)question).question);
				prepStmt.setString(2, question.parseAnswers());
				//prepStmt.setString(2, ParseAnswers.getString(question.answers));
				prepStmt.setInt(3, quiz.getID());

				prepStmt.executeUpdate();
				break;
				
			case 2:
				PreparedStatement prepStmt4 = connection.prepareStatement(
						"INSERT INTO " + question.type + " (question, answers, quizID) VALUES (?,?,?)");
				prepStmt4.setString(1, ((FillBlankQuestion)question).question);
				prepStmt4.setString(2, question.parseAnswers());
				prepStmt4.setInt(3, quiz.getID());

				prepStmt4.executeUpdate();
				break;
				
			case 4:
				PreparedStatement prepStmt2 = connection.prepareStatement(
						"INSERT INTO " + question.type + " (pictureURL, answers, quizID) VALUES (?,?,?)");
				prepStmt2.setString(1, ((PictureResponseQuestion)question).imageURL);
				prepStmt2.setString(2, question.parseAnswers());
				prepStmt2.setInt(3, quiz.getID());

				prepStmt2.executeUpdate();
				break;
				
			case 3:
				PreparedStatement prepStmt3 = connection.prepareStatement(
						"INSERT INTO " + question.type + " (question, choices, answers, quizID) VALUES (?,?,?,?)");
				prepStmt3.setString(1, ((MultipleChoiceQuestion)question).question);
				prepStmt3.setString(2, ((MultipleChoiceQuestion)question).parseChoices());
				prepStmt3.setString(3, question.parseAnswers());
				prepStmt3.setInt(4, quiz.getID());

				prepStmt3.executeUpdate();
				break;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private static void updateQuizQuestionIndexTable(Quiz quiz, Question question) {
		try {
			PreparedStatement prepStmt = connection.prepareStatement("INSERT INTO question_quiz_index (questionID, quizID, questionType) VALUES (?,?,?)");
			prepStmt.setInt(1,question.ID);
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
			String command = "SELECT * FROM quizzes WHERE userID =" + user_id;
			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			
			ArrayList<Integer> quizIDs = new ArrayList<Integer>();
			
			while(rs.next()){
				int temp = rs.getInt("id");
				quizIDs.add(temp);
			}
			
			
			for(int i = 0; i < quizIDs.size(); i++){
				Quiz temp = new Quiz();
				
				command = "SELECT * FROM quizzes WHERE ID = " + quizIDs.get(i);
				Statement statement2 = connection.createStatement();
				ResultSet rs2 = statement2.executeQuery(command);
				
				rs2.next();
				
				temp.setScore(rs2.getInt("score"));
				temp.setName(rs2.getString("name"));
				temp.setUserID(rs2.getInt("userID"));
				temp.setID(quizIDs.get(i));
			
				
				quizzes.add(temp);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return quizzes;
	}
	
	// gets the most recently created quizzes without specific user 
	
	public static ArrayList<Quiz> getRecentCreatedQuizzes() {
		try {
			ArrayList<Quiz> recentQuizzes = new ArrayList<Quiz> ();
			String command = "SELECT * FROM quizzes";
			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			rs.afterLast();
			int quizCount = 0;
			while (rs.previous() && quizCount < 10) {
				quizCount++;
				int quiz_id = rs.getInt("ID");
				Quiz recentQuiz = QuizDao.getQuizByID(quiz_id);
				recentQuizzes.add(recentQuiz);
			}
			
			return recentQuizzes;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	//gets the most recently created quizzes created by user 
	
	public static ArrayList<Quiz> getRecentUserCreatedQuizzes(int user_id) {
		try {
			ArrayList<Quiz> recentQuizzes = new ArrayList<Quiz> ();
			String command = "SELECT * FROM quizzes WHERE userID = " 
					+ user_id;
			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			rs.afterLast();
			int quizCount = 0;
			
			while (rs.previous() && quizCount < 2) {
				quizCount++;
				int quiz_id = rs.getInt("ID");
				Quiz recentQuiz = QuizDao.getQuizByID(quiz_id);
				recentQuizzes.add(recentQuiz);
			}
			
			return recentQuizzes;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
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
				quiz.setUserID(rs.getInt("userID"));
				quiz.setID(rs.getInt("ID"));
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
	
	// returns the five most popular quizzes. if less than five quizzes have been created, then just return the 
	//
	
	public static ArrayList<Quiz> getMostPopularQuizzes() {
		
		try {
			ArrayList<Quiz> popularQuizzes = new ArrayList<Quiz> ();
			String command = "SELECT * FROM quizzes";
			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			
			while (rs.next()) {
				Quiz tmp = new Quiz();
				tmp.setID(rs.getInt("ID"));
				tmp.setScore(rs.getInt("score"));
				tmp.setName(rs.getString("name"));
				tmp.setUserID(rs.getInt("userID"));
				tmp.setNumTimesTaken(rs.getInt("numTimesTaken"));
				tmp.setTimeCreated(rs.getString("timeCreated"));
				tmp.setDescription(rs.getString("description"));
				tmp.setCategory(rs.getString("category"));
				tmp.setRandomQuestions(rs.getBoolean("randomizeQuestions"));
				tmp.setMultiplePages(rs.getBoolean("multiplePages"));
				tmp.setImmediateCorrect(rs.getBoolean("immediateCorrection"));
				tmp.setPracticeModeAvailable(rs.getBoolean("practiceModeAvailable"));
				popularQuizzes.add(tmp);
			}
			
			Collections.sort(popularQuizzes);
			if (popularQuizzes.size() <= 5) return popularQuizzes;
			else {
				while(popularQuizzes.size() > 5) {
					popularQuizzes.remove(0);
				}
				return popularQuizzes;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getCurrentTimeStamp() {
	    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	}
}
