package quiz;

import java.util.ArrayList;

public class QuestionResponse extends Question{
	
	public String question;
	
	public QuestionResponse(String question, ArrayList<String> answer) {
		super();
		this.type = "q_question_response";
		this.question = question;
		this.answers = answer;
	}
	
	public QuestionResponse(int score, String question, ArrayList<String> answer) {
		super(score);
		this.type = "q_question_response";
		this.question = question;
		this.answers = answer;
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
