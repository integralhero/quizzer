package quiz;

public class Rate {
	
	private int quiz_id;
	private int user_id;
	private String review;
	private int rating;
	
	public Rate(int quiz_id, int user_id, String review, int rating) {
		this.quiz_id = quiz_id;
		this.user_id = user_id;
		this.review = review;
		this.rating = rating;
	}
	
	public int getQuizID() {
		return this.quiz_id;
	}
	
	public int getUserID() {
		return this.user_id;
	}
	
	public String getReview() {
		return this.review;
	}
	
	public void setReview(String review) {
		this.review = review;
	}
	
	public int getRating() {
		return this.rating;
	}
	
	public void setRating(int rating) {
		this.rating = rating;
	}
	
}
