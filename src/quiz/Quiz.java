package quiz;
import java.util.*;

public class Quiz {

	private int id;
	private int score;
	private String name;
	private int user_id;
	public ArrayList<Question> questions;
	public boolean randomizeQuestions;
	public boolean multiplePages;
	public boolean immediateCorrect;

	
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
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public int getUserID(){
		return this.user_id;
	}
	
	public void setUserID(int user_id){
		this.user_id = user_id;
	}

}
