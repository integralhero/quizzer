package quiz;

public class PictureResponseQuestion extends Question{
	
	public String imageURL;
	
	public PictureResponseQuestion(String imageURL, String answer) {
		super();
		this.type = "PictureResponse";
		this.imageURL = imageURL;
		answers.add(answer);
	}
	
	public PictureResponseQuestion(int score, String imageURL, String answer) {
		super(score);
		this.type = "PictureResponse";
		this.imageURL = imageURL;
		answers.add(answer);
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
