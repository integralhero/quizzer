package quiz;

public class QuestionResponse extends Question{
	
	public String question;
	
	public QuestionResponse(String question, String answer) {
		super();
		this.type = "q_question_response";
		this.question = question;
		answers.add(answer);
	}
	
	public QuestionResponse(int score, String question, String answer) {
		super(score);
		this.type = "q_question_response";
		this.question = question;
		answers.add(answer);
	}
	
	public String getQuestion() {
		return question;
	}
	public String getString() {
		return "test String";
	}
	
	public void resetQuestion(String newQuestion) {
		this.question = newQuestion;
	}
	
	public void resetAnswer(String newAnswer) {
		answers.clear();
		addAnswer(newAnswer);
	}

}
