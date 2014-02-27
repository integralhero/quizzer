package quiz;

public class Message {
	public int messageID;
	public int senderID;
	public int recipientID;
	public int type;
	
	
	public int getMessageID() {
		return this.messageID;
	}
	public void setMessageID(int id) {
		this.messageID = id;
	}
	public int getSenderID() {
		return this.senderID;
	}
	public void setSenderID(int id) {
		this.senderID = id;
	}
	public int getRecipientID() {
		return this.recipientID;
	}
	public void setRecipientID(int id) {
		this.recipientID = id;
	}
}
