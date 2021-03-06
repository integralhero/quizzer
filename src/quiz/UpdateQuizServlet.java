package quiz;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UpdateQuizServlet
 */
@WebServlet("/UpdateQuizServlet")
public class UpdateQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateQuizServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	private static final int QUESTION_RESPONSE = 1;
	private static final int FILL_BLANK = 2;
	private static final int MULT_CHOICE = 3;
	private static final int PIC_RES = 4;
	private static final int MULT_CHOICE_MULT_ANS = 5;


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String quizName = request.getParameter("quiz_name_field");
		ArrayList<Question> questions = getQuestionsFromForm(request);
		Quiz quiz = new Quiz();
		HttpSession session = request.getSession();
		User currUser = (User)session.getAttribute("currentUser");
		quiz.setName(quizName);
		quiz.setQuestions(questions);
		quiz.setUserID(currUser.getUserid());
		quiz.calculateAndSetScore();
		quiz.setDescription(request.getParameter("category").toLowerCase());
		quiz.setDescription(request.getParameter("description"));
		quiz.setImmediateCorrect(request.getParameter("feedback") != null);
		quiz.setPracticeModeAvailable(request.getParameter("practice") != null);
		quiz.setMultiplePages(request.getParameter("mult_pages") != null);
		quiz.setRandomQuestions(request.getParameter("randomize") != null);
		quiz.setNumFlags(0);
		
		int newQuizID = QuizDao.addQuiz(quiz);
		int oldQuizID = Integer.parseInt(request.getParameter("quizID"));
		QuizDao.removeLinkByID(oldQuizID);
		QuizTakenDao.updateQuizesTakenID(oldQuizID, newQuizID);
		QuestionDao.updateQuestionsID(oldQuizID, newQuizID);
		TagDao.updateTagsWith(oldQuizID, newQuizID);
		QuizDao.deleteQuizByID(oldQuizID);
		
		int numQtns = Integer.parseInt(request.getParameter("question_count_field"));
		
		currUser.addQuizMade(quiz);
		
		AchievementListener.createQuiz(currUser);
		
		RequestDispatcher dispatch = request.getRequestDispatcher("ql/" + quiz.getID());
		dispatch.forward(request, response);
	}
	
	private ArrayList<Question> getQuestionsFromForm(HttpServletRequest request) {
		ArrayList<Question> questions = new ArrayList<Question>();
		
		int numQuestions =  Integer.parseInt(request.getParameter("question_count_field"));
	
		for (int qtnNum = 1; qtnNum <= numQuestions; qtnNum++) {
			
			
			if (request.getParameter("question" + qtnNum) != null) {
				
				int questionType = Integer.parseInt(request.getParameter("question_type_" + qtnNum));
				String question = "";
				String answer = "";
				Question quizQtn;
				switch (questionType) {
					case QUESTION_RESPONSE:
						question = request.getParameter("question" + qtnNum);
					
						answer = request.getParameter("answer" + qtnNum);
						
						quizQtn = new QuestionResponse(question, ParseAnswers.getArrayList(answer));
						questions.add(quizQtn);
						break;
					case FILL_BLANK:
						question = request.getParameter("question" + qtnNum);
						answer = request.getParameter("answer" + qtnNum);
	
						quizQtn = new FillBlankQuestion(question, ParseAnswers.getArrayList(answer));
						questions.add(quizQtn);
						break;
					case MULT_CHOICE:
						question = request.getParameter("question" + qtnNum);

						int numMultChoiceAnswers = Integer.parseInt(request.getParameter("mult_choice_answer_count_" + qtnNum));
						ArrayList<String> choices = new ArrayList<String>();
						ArrayList<String> answers = new ArrayList<String>();
						for (int choiceNum = 1; choiceNum <= numMultChoiceAnswers; choiceNum++) {
							String choice = request.getParameter("mult_choice_answer_" + choiceNum);
							if (request.getParameter("mult_choice_checkbox_" + choiceNum) != null) {
								answers.add(choice);
							}
							choices.add(choice);
						}
						int score = answers.size();
						quizQtn = new MultipleChoiceQuestion(score, question, choices);					
						for (String correctAnswer : answers) {
							quizQtn.addAnswer(correctAnswer);
						}
						questions.add(quizQtn);
						break;
					case PIC_RES:
						question = request.getParameter("question" + qtnNum);
					
						answer = request.getParameter("answer" + qtnNum);
				
						quizQtn = new PictureResponseQuestion(question, ParseAnswers.getArrayList(answer));
						questions.add(quizQtn);
						break;
					default: break;

//					
				}
			}
		}
		
		return questions;
	}

}
