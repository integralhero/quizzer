package quiz;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CreateQuizServlet
 */
@WebServlet("/CreateQuizServlet")
public class CreateQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQuizServlet() {
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
		String[] tags = request.getParameterValues("tag");
		
		quiz.setTags(request.getParameterValues("tag"));
		quiz.setDescription(request.getParameter("description"));
		quiz.setCategory(request.getParameter("category").toLowerCase());
		quiz.setImmediateCorrect(request.getParameter("feedback") != null);
		quiz.setPracticeModeAvailable(request.getParameter("practice") != null);
		quiz.setMultiplePages(request.getParameter("mult_pages") != null);
		quiz.setRandomQuestions(request.getParameter("randomize") != null);
		quiz.setNumFlags(0);
		QuizDao.addQuiz(quiz);
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
				ArrayList<String> answers = new ArrayList<String>();
				Question quizQtn;
				switch (questionType) {
					case QUESTION_RESPONSE:
						question = request.getParameter("question" + qtnNum);
						
						Collections.addAll(answers, request.getParameterValues("answer" + qtnNum)); 
						
						quizQtn = new QuestionResponse(Integer.parseInt(request.getParameter("maxScore" + qtnNum)), question, answers);
						questions.add(quizQtn);
						break;
					case FILL_BLANK:
						question = request.getParameter("question" + qtnNum);
						
						Collections.addAll(answers, request.getParameterValues("answer" + qtnNum)); 
						quizQtn = new FillBlankQuestion(Integer.parseInt(request.getParameter("maxScore" + qtnNum)), question, answers);
						questions.add(quizQtn);
						break;
					case MULT_CHOICE:
						question = request.getParameter("question" + qtnNum);
						
						int numMultChoiceAnswers = Integer.parseInt(request.getParameter("mult_choice_answer_count_" + qtnNum));
						ArrayList<String> choices = new ArrayList<String>();
						ArrayList<String> multChoiceAnswers = new ArrayList<String>();
						for (int choiceNum = 1; choiceNum <= numMultChoiceAnswers; choiceNum++) {
							String choice = request.getParameter("mult_choice_answer_" + choiceNum);
							if (request.getParameter("mult_choice_checkbox_" + choiceNum) != null) {
								multChoiceAnswers.add(choice);
							}
							choices.add(choice);
						}
						int score = multChoiceAnswers.size();
						quizQtn = new MultipleChoiceQuestion(score, question, choices);					
						for (String correctAnswer : multChoiceAnswers) {
							quizQtn.addAnswer(correctAnswer);
						}
						

						questions.add(quizQtn);
						break;
					case PIC_RES:
						question = request.getParameter("question" + qtnNum);
						
						Collections.addAll(answers, request.getParameterValues("answer" + qtnNum)); 
						quizQtn = new PictureResponseQuestion(Integer.parseInt(request.getParameter("maxScore" + qtnNum)), question, answers);
						questions.add(quizQtn);
						break;
					default: break;


				
				}
			}
		}
		
		return questions;
	}

}
