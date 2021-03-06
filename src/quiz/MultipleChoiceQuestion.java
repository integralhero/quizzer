package quiz;

import java.util.ArrayList;
import java.util.Arrays;

public class MultipleChoiceQuestion extends Question{
	
	public ArrayList<String> choices;
	public String question;
	
	public MultipleChoiceQuestion(String question, ArrayList<String> choices, ArrayList<String> answer) {
		super();
		this.type = "q_multiple_choice";
		this.question = question;
		this.choices = choices;
		this.answers = answer;
	}
	
	public MultipleChoiceQuestion(int score, String question, ArrayList<String> choices, ArrayList<String> answer) {
		super(score);
		this.type = "q_multiple_choice";
		this.question = question;
		this.choices = choices;
		this.answers = answer;
	}
	
	public MultipleChoiceQuestion(int score, String question, ArrayList<String> choices) {
		super(score);
		this.type = "q_multiple_choice";
		this.question = question;
		this.choices = choices;
	}
	
	public ArrayList<String> getChoices() {
		return choices;
	}
	
	public String getQuestion() {
		return this.question;
	}
	
	public void resetChoices(ArrayList<String> choices) {
		this.choices = choices;
	}
	
	public void resetQuestion(String question) {
		this.question = question;
	}
	
	public void resetAnswer(String answer) {
		answers.clear();
		addAnswer(answer);
	}
	
	public String parseChoices() {
		String parsed = "";
		for (int i = 0; i < choices.size(); i++) {
			if (i == choices.size() - 1) {
				parsed += choices.get(i);
			} else {
				parsed += choices.get(i) + ",";
			}
		}
		return parsed;
	}
	public static ArrayList<String> unParseChoice(String str) {
		return new ArrayList<String>(Arrays.asList(str.split("\\s*,\\s*")));
	}
}
