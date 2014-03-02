package quiz;

public class QuizTaken {

	private int quizID;
	private int score;
	private int timeTaken;
	private int userID;
	
	public QuizTaken(int quizID, int score, int timeTaken, int userID){
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
	
	public int getTimeTaken(){
		return timeTaken;
	}
	
	public int getUserID(){
		return userID;
	}
}

