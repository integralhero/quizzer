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
    	if(me != null) {
    		ArrayList<FriendRequest> frqs = MessageDao.getAllFriendReqsToUser(me.getUserid());
    		ArrayList<Note> allmessages = MessageDao.getAllNotesToUser(me.getUserid());
        	ArrayList<Announcement> allAnnouncements = AnnouncementDao.getAllAccouncements();
        	ArrayList<ChallengeRequest> crqs = MessageDao.getAllChallengesToUser(me.getUserid());
        	req.setAttribute("announcements", allAnnouncements);
        	req.setAttribute("notes", allmessages);
        	req.setAttribute("allRequests", frqs);
        	req.setAttribute("challenges", crqs);
        	try {
    			req.getRequestDispatcher("/index.jsp").forward(req, res);
    		} catch (ServletException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	} 
    	else {
    		res.sendRedirect("index.jsp");
    	}
    	
    }

}
