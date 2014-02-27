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
	
	public void confirmFriendRequest(int senderid, int recipientid, int messageID) {
		try {
			Statement stmt = connection.createStatement();
			stmt.executeQuery("UPDATE friend_requests SET isConfirmed = TRUE WHERE ref=\"" + messageID + "\"");
			//add both friends to friendshiptable
			Statement f1 = connection.createStatement();
			f1.executeQuery("INSERT INTO friendships (user_id, friend_id) VALUES (\""+ senderid +"\",\"" + recipientid + "\")");
			Statement f2 = connection.createStatement();
			f1.executeQuery("INSERT INTO friendships (user_id, friend_id) VALUES (\""+ recipientid +"\",\"" + senderid + "\")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
