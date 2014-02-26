package quiz;

import java.io.*;
import java.text.*;
import javax.servlet.*;

import quiz.UserDao;
import quiz.User;

public class UserController {
	private static final long serialVersionUID = 1L;
	private static String INSERT_OR_EDIT = "/user.jsp";
	private UserDao dao;
	
	public UserController() {
		super();
		dao = new UserDao();
	}
	
	/* add doGet and doPost
	 * use dao.deleteUser(userId) <--- get parameter from req.getParameter
	 * 
	 */
}
