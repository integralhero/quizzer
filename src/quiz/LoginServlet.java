package quiz;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AccountManager manager = (AccountManager) request.getServletContext().getAttribute("manager");
		String username = request.getParameter("username");
		String pass = request.getParameter("password");
		if(manager.authenticate(username, pass)) { //correct
			PrintWriter out = response.getWriter();
			out.println("<!DOCTYPE html>");
			out.println("<head>");
			out.println("<meta charset=\"UTF-8\" />");
			out.println("<title>Welcome!</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h2>Welcome " + username + "!</h2>");
			out.println("</body>");
			out.println("</html>");
		}
		else {
			RequestDispatcher dispatch = request.getRequestDispatcher("badpassword.html");
			dispatch.forward(request, response);
		}
	}

}
