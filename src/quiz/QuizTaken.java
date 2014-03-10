package quiz;

public class QuizTaken {

	private int quizID;
	private int score;
	private int timeElapsed;
	private int userID;
	private String timeTaken;
	
	public QuizTaken(int userID, int quizID, String timeTaken, int score, int timeElapsed){
		this.quizID = quizID;
		this.score = score;
		this.timeElapsed = timeElapsed;
		this.timeTaken = timeTaken;
		this.userID = userID;
	}
	
	public int getQuizID(){
		return quizID;
	}
	
	public int getScore(){
		return score;
	}
	
	public String getTimeTakingQuiz(){
		return timeTaken;
	}
	
	public int getUserID(){
		return userID;
	}
	
	public long getTimeElapsed() {
		return this.timeElapsed;
	}
	
	public void setTimeTaken(String currentTime) {
		this.timeTaken = currentTime;
	}
	
	public void setTimeElapsed(int time) {
		this.timeElapsed = time;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
}

