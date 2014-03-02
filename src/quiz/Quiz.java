package quiz;
import java.util.*;

public class Quiz {

	private int quiz_id;
	private int totalScore;
	private String quizName;
	private int user_id; // user who made the quiz
	private int numTimesTaken;
	private String timeCreated;
	private String description;
	private String category;
	public ArrayList<Question> questions;
	
	public boolean randomizeQuestions = false;
	public boolean multiplePages = false;
	public boolean immediateCorrect = false;
	
	public Quiz() { 
		
	}
	
	public Quiz(String quizName, int quiz_id, int user_id, ArrayList<Question> questions) {
		this.quiz_id = quiz_id;
		this.quizName = quizName;
		this.user_id = user_id;
		this.questions = questions;
		
		int totalScore = 0;
		for (int i = 0; i < questions.size(); i++) {
			totalScore += questions.get(i).getScore();
		}
		this.totalScore = totalScore;
	}
	
	public void setMultiplePages() {
		multiplePages = true;
	}
	
	public void setImmediateCorrect() {
		immediateCorrect = true;
	}
	
	public void setRandomizeQuestions() {
		randomizeQuestions = true;
	}
	
	public void addQuestion(Question question) {
		questions.add(question);
	}
	
	public int getID(){
		return this.quiz_id;
	}
	
	public void setID(int id){
		this.quiz_id = id;
	}
	
	public int getScore(){
		return this.totalScore;
	}
	
	public void setScore(int score){
		this.totalScore = score;
	}
	
	public String getName(){
		return this.quizName;
	}
	
	public void setName(String name){
		this.quizName = name;
	}
	
	public int getUserID(){
		return this.user_id;
	}
	
	public void setUserID(int user_id){
		this.user_id = user_id;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	public String getDescription(){
		return this.description;
	}
	
	public void setCategory(String category){
		this.category = category;
	}
	public String getCategory(){
		return this.category;
	}

}
