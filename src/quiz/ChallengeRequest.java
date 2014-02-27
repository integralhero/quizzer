package quiz;

public class ChallengeRequest extends Message {
	public int quizID;
	public int challengerHighScore;
	
	public int getQuizID() {
		return this.quizID;
	}
	public void setQuizID(int id) {
		this.quizID = id;
	}
	public int getHighScore() {
		return this.challengerHighScore;
	}
	public void setHighScore(int score) {
		this.challengerHighScore = score;
	}
}
