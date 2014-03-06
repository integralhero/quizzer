package quiz;

public class QuizTaken {

	private int quizID;
	private int score;
	private long timeElapsed;
	private int userID;
	private long timeTakingQuiz;
	
	public QuizTaken(int quizID, int score, long timeTakingQuiz, int userID){
		this.quizID = quizID;
		this.score = score;
		this.timeElapsed = timeTakingQuiz;
		this.timeTakingQuiz = timeTakingQuiz;
		this.userID = userID;
	}
	
	public int getQuizID(){
		return quizID;
	}
	
	public int getScore(){
		return score;
	}
	
	public long getTimeTakingQuiz(){
		return timeTakingQuiz;
	}
	
	public int getUserID(){
		return userID;
	}
	
	public long getTimeElapsed() {
		return this.timeElapsed;
	}
	
	public void setTimeElapsed(long currentTime) {
		this.timeElapsed = currentTime - this.timeElapsed;
	}
	
	public void setTimeTakingQuiz(long time) {
		timeTakingQuiz = time;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
}

