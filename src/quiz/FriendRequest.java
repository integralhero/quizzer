package quiz;

public class FriendRequest extends Message {
	public boolean isConfirmed = false;
	
	public void setStatus(boolean status) {
		this.isConfirmed = status;
	}
	public boolean getConfirmedStatus() {
		return this.isConfirmed;
	}
}
