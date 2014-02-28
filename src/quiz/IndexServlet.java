package quiz;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/IndexServlet")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
    	
    	User me = (User)req.getSession(false).getAttribute("currentUser");
    	ArrayList<FriendRequest> frqs = MessageDao.getAllFriendReqsToUser(me.getUserid());
    	req.setAttribute("allRequests", frqs);
    	try {
			req.getRequestDispatcher("/index.jsp").forward(req, res);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
