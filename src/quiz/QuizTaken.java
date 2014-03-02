package quiz;

public class QuizTaken {

	private int quizID;
	private int score;
	private long timeTaken;
	private int userID;
	
	public QuizTaken(int quizID, int score, long timeTaken, int userID){
		this.quizID = quizID;
		this.score = score;
		this.timeTaken = timeTaken;
		this.userID = userID;
	}
	
	public int getQuizID(){
		return quizID;
	}
	
	public int getScore(){
		return score;
	}
	
	public long getTimeTaken(){
		return timeTaken;
	}
	
	public int getUserID(){
		return userID;
	}
	
	public void setTimeTaken(long time) {
		timeTaken = time;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
}

