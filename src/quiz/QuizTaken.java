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
}
