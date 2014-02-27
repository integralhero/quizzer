package quiz;

import java.util.ArrayList;

public class Quiz {
	
	public String quizID;
	public String quizName;
	public int maxScore;
	public String userOwns; // the username of the user who owns the quiz
	public ArrayList<Question> questions;
	public boolean randomizeQuestions;
	public boolean multiplePages;
	public boolean immediateCorrect;
	
	public Quiz() {
		
	}
	
	
}
