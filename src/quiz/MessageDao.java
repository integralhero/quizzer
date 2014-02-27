package quiz;

import java.sql.*;
import java.util.*;

public class MessageDao {
	private static Connection connection = Database.connect();
	
	public ArrayList<FriendRequest> getAllFriendReqsToUser(int userid) {
		ArrayList<FriendRequest> msgs = new ArrayList<FriendRequest>();
		
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM friend_requests WHERE recipientid = " + "\"" + userid + "\"");
			while(rs.next()) {
				FriendRequest tmpMsg = new FriendRequest();
				tmpMsg.setSenderID(rs.getInt("senderid"));
				tmpMsg.setRecipientID(rs.getInt("recipientid"));
				tmpMsg.setMessageID(rs.getInt("ref"));
				tmpMsg.setStatus(rs.getBoolean("isConfirmed"));
				msgs.add(tmpMsg);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msgs;
	}
	
	public void sendFriendRequest(int senderid, int recipientid) {
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("INSERT INTO friend_requests (senderid, recipientid, isConfirmed) VALUES (\""+ senderid + "\",\""+ recipientid + "\", FALSE)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
