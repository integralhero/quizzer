package quiz;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class QuizServlet
 */
@WebServlet("/QuizServlet")
public class QuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
    	String id = req.getPathInfo();
    	id = id.substring(1);
    	if(id.charAt(id.length() - 1) == '/')id = id.substring(0, id.length() - 1);
    	int parseInt = 0;
    	try {
    		parseInt = Integer.parseInt(id);
    	} catch(NumberFormatException e) {
    		
    	}
    	
    	Quiz tmp = QuizDao.getQuizByID(parseInt);
    	req.setAttribute("curQuiz", tmp);
    	try {
			req.getRequestDispatcher("/renderQuiz.jsp").forward(req, res);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
