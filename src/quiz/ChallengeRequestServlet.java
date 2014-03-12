package quiz;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ChallengeRequestServlet
 */
@WebServlet("/ChallengeRequestServlet")
public class ChallengeRequestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChallengeRequestServlet() {
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
		int myID = Integer.parseInt(request.getParameter("myID"));
		int friendID = Integer.parseInt(request.getParameter("friendID"));
		int quizID = Integer.parseInt(request.getParameter("quizID"));
		
		//System.out.println("myID: " + myID + "---yourID: " + friendID);
		MessageDao.sendChallengeRequest(myID, friendID, quizID);
		
		response.sendRedirect("/Quizzer/qz/"+quizID);
	}

}
