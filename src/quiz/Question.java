package quiz;

import java.util.ArrayList;

public class Question {

	public ArrayList<String> answers; //there is potentially more than 1 answer per question
	public int score;
	public static int DEFAULT_SCORE = 1;
	
	public Question() {
		this.score = DEFAULT_SCORE;
		answers = new ArrayList<String>();
	}
	
	public Question(int score) {
		this.score = score;
		answers = new ArrayList<String>();
	}
	
	public void addAnswer(String answer) {
		answers.add(answer);
	}
	
	public ArrayList<String> getAnswers() {
		return answers;
	}
	
	public int getScore() {
		return score;
	}
	
	public void resetScore(int newScore) {
		this.score = newScore;
	}
	
}
