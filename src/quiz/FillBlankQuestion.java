package quiz;

public class FillBlankQuestion extends Question{
	
	public String question;
	
	public FillBlankQuestion(String question, String answer) {
		super();
		this.type = "FillInBlank";
		this.question = question.replaceAll(answer, getBlank(answer));
		answers.add(answer);
	}
	
	public FillBlankQuestion(int score, String question, String answer) {
		super(score);
		this.type = "FillInBlank";
		this.question = question.replaceAll(answer, "");
		answers.add(answer);
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
