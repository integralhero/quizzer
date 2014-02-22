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
		AccountManager manager = (AccountManager) request.getServletContext().getAttribute("manager");
		String username = request.getParameter("username");
		String pass = request.getParameter("password");
		//manager.printAccounts();
		//System.out.println(username+ "---------------");
		if(manager.accountExists(username)) {
			RequestDispatcher dispatch = request.getRequestDispatcher("userexists.jsp");
			dispatch.forward(request, response);
		}
		else {
			manager.makeAccount(username, pass);
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
		

	}

}
