package quiz;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BenTestServlet
 */
@WebServlet("/BenTestServlet")
public class BenTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BenTestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	public void printQuiz(Quiz quiz) {
		System.out.println("QuizID is " + quiz.getID());
		System.out.println("Description is" + quiz.getDescription());
		System.out.println("Category is " + quiz.getCategory());
		System.out.println("Quiz Name is " + quiz.getName());
		System.out.println("numTimesTaken is " + quiz.getNumTimesTaken());
		System.out.println("Score is " + quiz.getScore());
		System.out.println("UserID is " + quiz.getUserID());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<Quiz> list = QuizDao.getRecentUserCreatedQuizzes(1);
		if (list == null) System.out.println("list is null");
		else {
			System.out.println("not null");
			System.out.println("size is " + list.size());
			for (int i = 0; i < list.size(); i++) {
				printQuiz(list.get(i));
				System.out.println();
			}
		}
	}

}
