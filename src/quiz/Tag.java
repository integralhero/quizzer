package quiz;

public class Tag {
	
	private int tagID;
	private int quizID;
	private String tag;
	
	public Tag(int tagID, int quizID, String tag) {
		this.tagID = tagID;
		this.quizID = quizID;
		this.tag = tag;
	}
	
	public int getTagID() {
		return this.tagID;
	}
	
	public int getQuizID() {
		return this.quizID;
	}
	
	public String getTag() {
		return this.tag;
	}
	
	public void setTagID(int tagID) {
		this.tagID = tagID;
	}
	
	public void setQuizID(int quizID) {
		this.quizID = quizID;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
}
