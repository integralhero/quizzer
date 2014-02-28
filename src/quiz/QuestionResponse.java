package quiz;

public class QuestionResponse extends Question{
	
	public String question;
	
	public QuestionResponse(String question, String answer) {
		super();
		this.question = question;
		answers.add(answer);
	}
	
	public QuestionResponse(int score, String question, String answer) {
		super(score);
		this.question = question;
		answers.add(answer);
	}
	
	public String getQuestion() {
		return question;
	}
	
	public void resetQuestion(String newQuestion) {
		this.question = newQuestion;
	}
	
	public void resetAnswer(String newAnswer) {
		answers.clear();
		addAnswer(newAnswer);
	}

}
