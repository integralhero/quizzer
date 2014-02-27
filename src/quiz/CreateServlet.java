package quiz;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CreateServlet
 */
@WebServlet("/login/CreateServlet")
public class CreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateServlet() {
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

		ServletContext context = request.getServletContext();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		boolean userNameExists = UserDao.checkUserNameExists(username);
//		System.out.println("userNameExists");
//		System.out.println(userNameExists);

		if (userNameExists) { // when the user name is already in the database
//			System.out.println("userNameExists");
			context.setAttribute("isUsernameTaken", userNameExists);
			
			RequestDispatcher dispatch = request.getRequestDispatcher("userexists.jsp");
			dispatch.forward(request, response);
			
		} else { // when the user name is not in the database...need to create user and add to database
			
			User user = new User();
			user.setUsername(username);
			user.setPassword(password);
			user.setEmail(email);
			UserDao.addUser(user);
			int idnum = UserDao.validateUser(user);
			user.setUserid(idnum);
			HttpSession session = request.getSession(true); 
			session.setAttribute("currentUser",user); 
			//get id and set it
			response.sendRedirect("/Quizzer/");
			
		}
	
	}

}
