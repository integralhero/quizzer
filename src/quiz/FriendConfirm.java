package quiz;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FriendConfirm
 */
@WebServlet("/FriendConfirm")
public class FriendConfirm extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FriendConfirm() {
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
		int senderid = Integer.parseInt(request.getParameter("senderid"));
		int recipientid = Integer.parseInt(request.getParameter("recipientid"));
		int messageID = Integer.parseInt(request.getParameter("messageID"));
		String choice = request.getParameter("choice"); //"yes" or "no"
		if(choice.equals("yes")) { //confirm friend request
			MessageDao.confirmFriendRequest(senderid, recipientid, messageID);
			System.out.println("YES to : " + messageID);
		}
		else { //cancel request
			MessageDao.denyFriendRequest(senderid, recipientid, messageID);
			System.out.println("NO to: " + messageID);
		}
		response.sendRedirect("/Quizzer/");
	}

}
