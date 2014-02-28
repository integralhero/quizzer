package quiz;
import java.util.*;

public class Quiz {

	private int id;
	private int score;
	private String quizName;
	private int user_id;
	public ArrayList<Question> questions;
	public boolean randomizeQuestions = false;
	public boolean multiplePages = false;
	public boolean immediateCorrect = false;
	
	public Quiz(String quizName, int user_id, ArrayList<Question> questions) {
		this.quizName = quizName;
		this.user_id = user_id;
		this.questions = questions;
		
		int totalScore = 0;
		for (int i = 0; i < questions.size(); i++) {
			totalScore += questions.get(i).getScore();
		}
		this.score = totalScore;
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

	
	public int getID(){
		return this.id;
	}
	
	public void setID(int id){
		this.id = id;
	}
	
	public int getScore(){
		return this.score;
	}
	
	public void setScore(int score){
		this.score = score;
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

}
