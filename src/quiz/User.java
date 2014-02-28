package quiz;

import java.util.ArrayList;

public class User {

	private int userid;
	private String username;
	private String password;
	private String email;
	private ArrayList<QuizTaken> quizzesTaken;
	private ArrayList<Quiz> quizzesMade;
	
	public User(){
		quizzesTaken = new ArrayList<QuizTaken>();
		quizzesMade = new ArrayList<Quiz>();
	}
	
	public void addQuizTaken(QuizTaken quiz){
		quizzesTaken.add(quiz);
	}
	
	public ArrayList<QuizTaken> getQuizzesTaken(){
		return quizzesTaken;
	}
	
	public void addQuizMade(Quiz quiz){
		quizzesMade.add(quiz);
	}
	
	public ArrayList<Quiz> getQuizzesMade(){
		return quizzesMade;
	}
	
	public int getUserid() {
		return this.userid;
	}
	
	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}



