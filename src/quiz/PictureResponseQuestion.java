package quiz;

import java.util.ArrayList;

public class PictureResponseQuestion extends Question{
	
	public String imageURL;
	
	public PictureResponseQuestion(String imageURL, ArrayList<String> answer) {
		super();
		this.type = "q_picture_response";
		this.imageURL = imageURL;
		this.answers = answer;
	}	
	
	public PictureResponseQuestion(int score, String imageURL, ArrayList<String> answer) {
		super(score);
		this.type = "q_picture_response";
		this.imageURL = imageURL;
		this.answers = answer;
	}
	
	public String getURL() {
		return imageURL;
	}
	
	public void resetImage(String imageURL) {
		this.imageURL = imageURL;
	}
	
	public void resetAnswer(String newAnswer) {
		answers.clear();
		answers.add(newAnswer);
	}
}
