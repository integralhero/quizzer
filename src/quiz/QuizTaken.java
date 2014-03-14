package quiz;

public class QuizTaken implements Comparable<QuizTaken>{

	private int quizID;
	private int score;
	private long timeElapsed = 0;
	private int userID;
	private String timeTaken;
	
	public QuizTaken(int userID, int quizID, String timeTaken, int score, long timeElapsed){
		this.quizID = quizID;
		this.score = score;
		this.timeElapsed = timeElapsed;
		this.timeTaken = timeTaken;
		this.userID = userID;
	}
	
	public int compareTo(QuizTaken quizTaken) {
		if (this.score > quizTaken.getScore()) return 1;
		if (this.score < quizTaken.getScore()) return -1;
		else {
			if (this.timeElapsed < quizTaken.getTimeElapsed()) return 1;
			if (this.timeElapsed > quizTaken.getTimeElapsed()) return -1;
			else return 0;
		}
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

