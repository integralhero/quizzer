package quiz;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User user = new User();
		String enteredUser = request.getParameter("username");
		user.setUsername(enteredUser);
		String passHash = Hasher.generateSaltedHash(request.getParameter("password"), UserDao.getSalt(enteredUser));
		user.setPassword(passHash);
		
		int getID = UserDao.validateUser(user);
		
		if(getID != -1) { 
			boolean status = UserDao.setStatus(getID);
			user.setAdminStatus(status);
			user.setUserid(getID);
			HttpSession session = request.getSession(true); 
			session.setAttribute("currentUser",user); 
			response.sendRedirect("/Quizzer/"); //logged-in page 
			
		}
		else {

			RequestDispatcher dispatch = request.getRequestDispatcher("badpassword.html");
			dispatch.forward(request, response);
		}
	}

}
