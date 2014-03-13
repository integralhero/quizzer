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
	
	public static ArrayList<Quiz> getAllFlaggedQuizzes() {
		ArrayList<Quiz> tmp = new ArrayList<Quiz>();
		try {
			String command = "SELECT * FROM quizzes WHERE flagNum > 0";
			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			while(rs.next()) {
				Quiz nextQ = getQuizByID(rs.getInt("ID"));
				tmp.add(nextQ);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tmp;
	}
	
	public static void addAnotherFlagTo(int quizid) {
		try {
			String commandGet = "SELECT * FROM quizzes WHERE ID=" + quizid;
			
			Statement statementG = connection.createStatement();
			ResultSet rs = statementG.executeQuery(commandGet);
			int curFlagVal = 0;
			if(rs.next()) {
				curFlagVal = rs.getInt("flagNum") + 1;
			}
			
			String command = "UPDATE quizzes SET flagNum=" + curFlagVal + " WHERE ID=" + quizid;
			
			Statement statement = connection.createStatement();
			statement.execute(command);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteQuizByID(int id) {
		
		try {
			String command = "DELETE FROM quizzes WHERE ID =" + id;
			
			Statement statement = connection.createStatement();
			statement.executeUpdate(command);	
			deleteQuizQuestionByID(id);
			QuizTakenDao.deleteQuizTakenByID(id);
			QuestionDao.removeByQuizID(id);
			removeChallengeReqByID(id);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void removeChallengeReqByID(int quizid) {
		try {
			String command = "DELETE FROM challenge_requests WHERE quizID=" + quizid;
			
			Statement statement = connection.createStatement();
			statement.executeUpdate(command);	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void deleteQuizQuestionByID(int id) {
		
		try {
			String command = "DELETE FROM question_quiz_index WHERE quizID =" + id;
			
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
			System.out.println("THIS" + quiz_id);
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);	
			ArrayList<Question> list = getAllQuestionsFrom(quiz_id);
			System.out.println("This is the question list size:---- " + list.size());
			Quiz tmp = null;

			if(rs.next()) {
				tmp = initializeQuiz(rs);
				tmp.setQuestions(list);
				tmp.setID(quiz_id);
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
			
			
			while(rs.next()){
				Quiz temp = new Quiz();
				temp = initializeQuiz(rs);
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
		ArrayList<Quiz> recentQuizzes = new ArrayList<Quiz> ();

		try {
			String command = "SELECT * FROM quizzes";
			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			rs.afterLast();

			while (rs.previous() && recentQuizzes.size() < 10) {
				Quiz recentQuiz = initializeQuiz(rs);
				recentQuizzes.add(recentQuiz);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return recentQuizzes;
	}
	
	public static ArrayList<Quiz> getAllCreatedQuizzes() {
		ArrayList<Quiz> recentQuizzes = new ArrayList<Quiz>();

		try {
			String command = "SELECT * FROM quizzes";
			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);

			while (rs.next()) {
				int id = rs.getInt("ID");
				Quiz recentQuiz = QuizDao.getQuizByID(id);
				recentQuizzes.add(recentQuiz);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return recentQuizzes;
	}
	
	//gets the most recently created quizzes created by user 
	
	public static ArrayList<Quiz> getRecentUserCreatedQuizzes(int user_id) {
		ArrayList<Quiz> recentQuizzes = new ArrayList<Quiz> ();

		try {
			String command = "SELECT * FROM quizzes WHERE userID = " 
					+ user_id;
			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			rs.afterLast();
			
			while (rs.previous() && recentQuizzes.size() < 10) {
				Quiz recentQuiz = initializeQuiz(rs);
				recentQuizzes.add(recentQuiz);
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recentQuizzes;
	}
	
	public static Quiz searchForQuiz(String quizName){
		Quiz quiz = new Quiz();

		try {
			String command = "SELECT * FROM quizzes WHERE name = \"" + quizName + "\"";
			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			
			if(rs.next()) {
				quiz = initializeQuiz(rs);
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
	
	
	/*public static ArrayList<Quiz> getHotQuizzes(){
		ArrayList<Quiz> quizzes = new ArrayList<Quiz> ();

		try {
			
			String command = "SELECT * FROM quizzes ORDER BY numTimesTaken DESC ";
				
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			
			while (rs.next() && quizzes.size() < 10) {
				Quiz temp = new Quiz();
				temp.setID(rs.getInt("ID"));
				temp.setScore(rs.getInt("score"));
				temp.setName(rs.getString("name"));
				temp.setUserID(rs.getInt("userID"));
				temp.setNumTimesTaken(rs.getInt("numTimesTaken"));
				temp.setTimeCreated(rs.getString("timeCreated"));
				temp.setDescription(rs.getString("description"));
				temp.setCategory(rs.getString("category"));
				quizzes.add(temp);
			}
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return quizzes;

	}*/
	
	public static ArrayList<Quiz> getMostPopularQuizzes() {
		ArrayList<Quiz> popularQuizzes = new ArrayList<Quiz> ();

		try {
			String command = "SELECT * FROM quizzes ORDER BY numTimesTaken DESC";
			
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			
			while (rs.next() && popularQuizzes.size() < 10) {
				Quiz tmp = initializeQuiz(rs);

				popularQuizzes.add(tmp);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return popularQuizzes;
	}
	
	public static ArrayList<Quiz> getFriendsCreatedQuizzes(int userID){
		ArrayList<Quiz> recentQuizzes = new ArrayList<Quiz> ();

		try {
			String command = "SELECT * FROM friendships WHERE userID = " 
					+ userID;
			System.out.print(command + "\n");
			

			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(command);
			
			ArrayList<Integer> friendIDs = new ArrayList<Integer>();
			while(rs.next()){
				friendIDs.add(rs.getInt("friendID"));
			}
			
			if(friendIDs.size() == 0) return null;
			String command2 = "SELECT * FROM quizzes WHERE userID = " + friendIDs.get(0);
			for(int i = 1; i < friendIDs.size(); i++){
				command2 += " OR userID = " + friendIDs.get(i);
			}
			
			System.out.print(command2 + "\n");
			
			Statement statement2 = connection.createStatement();
			ResultSet rs2 = statement2.executeQuery(command2);
			
			rs2.afterLast();
			
			while (rs2.previous() && recentQuizzes.size() < 10) {
				Quiz temp = initializeQuiz(rs2);
				recentQuizzes.add(temp);
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recentQuizzes;
	}
	
	public static String getCurrentTimeStamp() {
	    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	}
	
	public static Quiz initializeQuiz(ResultSet rs){
		Quiz tmp = new Quiz();

		try {
			tmp.setID(rs.getInt("ID"));
			tmp.setScore(rs.getInt("score"));
			String str = rs.getString("name");
			//System.out.println("Retrieved name: " + str);
			tmp.setName(str);
			tmp.setUserID(rs.getInt("userID"));
			tmp.setNumTimesTaken(rs.getInt("numTimesTaken"));
			tmp.setTimeCreated(rs.getString("timeCreated"));
			tmp.setDescription(rs.getString("description"));
			tmp.setCategory(rs.getString("category"));
			tmp.setNumFlags(rs.getInt("flagNum"));
			tmp.setRandomQuestions(rs.getBoolean("randomizeQuestions"));
			tmp.setMultiplePages(rs.getBoolean("multiplePages"));
			tmp.setImmediateCorrect(rs.getBoolean("immediateCorrection"));
			tmp.setPracticeModeAvailable(rs.getBoolean("practiceModeAvailable"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tmp;
	}
}
