package quiz;

import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TakeQuizServlet
 */
@WebServlet("/TakeQuizServlet")
public class TakeQuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TakeQuizServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("in take quiz servlet");
		int numQuestions = Integer.parseInt(request.getParameter("num_questions"));
		int score = 0;
		for(int i = 0; i < numQuestions; i++) {
			String[] userAnswers = request.getParameterValues("answerField" + i);
			String[] correctAnswers = request.getParameterValues("hiddenAnswer" + i);
			HashSet<String> userAnswersSet = new HashSet<String>();
			System.out.println("answer: " + userAnswers[0]);
			Collections.addAll(userAnswersSet, userAnswers);
			HashSet<String> correctAnswersSet = new HashSet<String>();
			Collections.addAll(correctAnswersSet, correctAnswers);

			score += checkAnswers(userAnswersSet, correctAnswersSet);
			System.out.println("Score: " + score);
		}
		User currUser = (User)request.getSession(false).getAttribute("currentUser");
		int userID = currUser.getUserid();
		int quizID = Integer.parseInt(request.getParameter("quiz_id"));
		QuizTaken quiz = new QuizTaken(userID, quizID, "10", score, 100);
	}

	
	protected int checkAnswers(HashSet<String> userAnswers, HashSet<String> correctAnswers) {
		System.out.println("in check Answers function");
		int score = 0;
		
		for (String userAnswer : userAnswers) {
			if (correctAnswers.contains(userAnswer)) {
				score++;
				correctAnswers.remove(userAnswer);
			}
		}
		
		return score;
	}

}