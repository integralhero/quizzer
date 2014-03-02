package quiz;

import java.sql.*;
import java.util.*;

public class MessageDao {
	private static Connection connection = Database.connect();
	
	public static boolean checkIfFriendsExist(int senderid, int recipientid) {
		boolean exists = false;
		try {
			System.out.println("Message.Dao: now checking.... senderid="+senderid + " and recipientid= " + recipientid);
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM friendships WHERE user_id = " + "\"" + senderid + "\" AND friend_id=\"" + recipientid + "\"");
			exists = rs.next();
			System.out.println("Result: "+ exists);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exists;
	}
	
	public static boolean checkIfRequestExist(int senderid, int recipientid) {
		boolean exists = false;
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM friend_requests WHERE senderid = " + "\"" + senderid + "\" AND recipientid=\"" + recipientid + "\"");
			exists = rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exists;
	}
	
	public static ArrayList<FriendRequest> getAllFriendReqsToUser(int userid) {
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
	
	public static void sendChallengeRequest(int senderid, int recipientid, int quizid){
		try {
			Statement stmt = connection.createStatement();
			stmt.execute("INSERT INTO challenge_requests (senderid, recipientid, quizid) VALUES (\""+ senderid + "\",\""+ recipientid + "\",\"" + quizid + "\")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void sendNote(int senderid, int recipientid, String message){
		try {
			Statement stmt = connection.createStatement();
			stmt.execute("INSERT INTO notes (senderid, recipientid, message) VALUES (\""+ senderid + "\",\""+ recipientid + "\",\"" + message + "\")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void sendFriendRequest(int senderid, int recipientid) {
		try {
			Statement stmt = connection.createStatement();
			stmt.execute("INSERT INTO friend_requests (senderid, recipientid, isConfirmed) VALUES (\""+ senderid + "\",\""+ recipientid + "\", FALSE)");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void confirmFriendRequest(int senderid, int recipientid, int messageID) {
		try {
			Statement stmt = connection.createStatement();
			stmt.execute("DELETE FROM friend_requests WHERE ref=\"" + messageID + "\"");
			//add both friends to friendshiptable
			Statement f1 = connection.createStatement();
			f1.execute("INSERT INTO friendships (user_id, friend_id) VALUES (\""+ senderid +"\",\"" + recipientid + "\")");
			Statement f2 = connection.createStatement();
			f2.execute("INSERT INTO friendships (user_id, friend_id) VALUES (\""+ recipientid +"\",\"" + senderid + "\")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void denyFriendRequest(int senderid, int recipientid, int messageID) {
		try {
			Statement stmt = connection.createStatement();
			stmt.execute("DELETE FROM friend_requests WHERE ref=\"" + messageID + "\"");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void deleteFriendship(int senderid, int recipientid) {
		try {
			System.out.println("Deleting " + senderid + " AND " + recipientid);
			Statement stmt = connection.createStatement();
			stmt.execute("DELETE FROM friendships WHERE user_id=\"" + senderid + "\" AND friend_id=\"" + recipientid + "\"");
			Statement stmt2 = connection.createStatement();
			stmt2.execute("DELETE FROM friendships WHERE user_id=\"" + recipientid + "\" AND friend_id=\"" + senderid + "\"");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
