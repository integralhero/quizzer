package quiz;

import java.util.ArrayList;

public class FillBlankQuestion extends Question{
	
	public String question;
	
	public FillBlankQuestion(String question, ArrayList<String> answer) {
		super();
		this.type = "q_fill_in_blank";
		for(int i = 0; i < answer.size(); i++)
			this.question = question.replaceAll(answer.get(i), getBlank(answer.get(i)));
		this.answers = answer;
	}
	
	public FillBlankQuestion(int score, String question, ArrayList<String> answer) {
		super(score);
		this.type = "q_fill_in_blank";
		for(int i = 0; i < answer.size(); i++)
			this.question = question.replaceAll(answer.get(i), getBlank(answer.get(i)));		
		this.answers = answer;
	}
	
	public String getBlank(String answer) {
		String blank = "";
		for (int i = 0; i < answer.length(); i++) {
			blank += " ";
		}
		return blank;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public void resetQuestion(String newQuestion, String newAnswer) {
		answers.clear();
		addAnswer(newAnswer);
		this.question = newQuestion.replaceAll(newAnswer, getBlank(newAnswer));
	}

}
