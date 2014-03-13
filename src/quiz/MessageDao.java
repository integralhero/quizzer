package quiz;

import java.sql.*;
import java.util.*;

public class MessageDao {
	private static Connection connection = Database.connect();
	
	public static boolean checkIfFriendsExist(int senderid, int recipientid) {
		if(connection == null) {
			connection = Database.connect();
		}
		boolean exists = false;
		try {
			System.out.println("Message.Dao: now checking.... senderID = "+senderid + " and recipientID= " + recipientid);
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM friendships WHERE userID = " + "\"" + senderid + "\" AND friendID=\"" + recipientid + "\"");
			exists = rs.next();
			System.out.println("Result: "+ exists);
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exists;
	}
	
	public static boolean checkIfRequestExist(int senderid, int recipientid) {
		if(connection == null) {
			connection = Database.connect();
		}
		boolean exists = false;
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM friend_requests WHERE senderID = " + "\"" + senderid + "\" AND recipientID =\"" + recipientid + "\"");
			exists = rs.next();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return exists;
	}
	
	public static ArrayList<FriendRequest> getAllFriendReqsToUser(int userid) {
		ArrayList<FriendRequest> msgs = new ArrayList<FriendRequest>();
		if(connection == null) {
			connection = Database.connect();
		}
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM friend_requests WHERE recipientID = " + "\"" + userid + "\"");
			while(rs.next()) {
				FriendRequest tmpMsg = new FriendRequest();
				tmpMsg.setSenderID(rs.getInt("senderID"));
				tmpMsg.setRecipientID(rs.getInt("recipientID"));
				tmpMsg.setMessageID(rs.getInt("ref"));
				tmpMsg.setStatus(rs.getBoolean("isConfirmed"));
				msgs.add(tmpMsg);
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msgs;
	}
	
	public static ArrayList<Note> getAllNotesToUser(int userid) {
		ArrayList<Note> msgs = new ArrayList<Note>();
		if(connection == null) {
			connection = Database.connect();
		}
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM notes WHERE recipientID = " + "\"" + userid + "\"");
			while(rs.next()) {
				Note tmpMsg = new Note();
				tmpMsg.setSenderID(rs.getInt("senderID"));
				tmpMsg.setRecipientID(rs.getInt("recipientID"));
				tmpMsg.setMessage(rs.getString("message"));
				msgs.add(tmpMsg);
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msgs;
	}
	
	public static ArrayList<ChallengeRequest> getAllChallengesToUser(int userid) {
		ArrayList<ChallengeRequest> msgs = new ArrayList<ChallengeRequest>();
		if(connection == null) {
			connection = Database.connect();
		}
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM challenge_requests WHERE recipientID = " + "\"" + userid + "\"");
			while(rs.next()) {
				ChallengeRequest tmpMsg = new ChallengeRequest();
				tmpMsg.setSenderID(rs.getInt("senderID"));
				tmpMsg.setRecipientID(rs.getInt("recipientID"));
				tmpMsg.setQuizID(rs.getInt("quizID"));
				msgs.add(tmpMsg);
			}
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msgs;
	}
	
	public static void sendChallengeRequest(int senderid, int recipientid, int quizid){
		if(connection == null) {
			connection = Database.connect();
		}
		try {
			Statement stmt = connection.createStatement();
			stmt.execute("INSERT INTO challenge_requests (senderID, recipientID, quizID) VALUES (\""+ senderid + "\",\""+ recipientid + "\",\"" + quizid + "\")");
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void sendNote(int senderid, int recipientid, String message){
		if(connection == null) {
			connection = Database.connect();
		}
		try {
			Statement stmt = connection.createStatement();
			stmt.execute("INSERT INTO notes (senderID, recipientID, message) VALUES (\""+ senderid + "\",\""+ recipientid + "\",\"" + message + "\")");
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void sendFriendRequest(int senderid, int recipientid) {
		if(connection == null) {
			connection = Database.connect();
		}
		try {
			Statement stmt = connection.createStatement();
			stmt.execute("INSERT INTO friend_requests (senderID, recipientID, isConfirmed) VALUES (\""+ senderid + "\",\""+ recipientid + "\", FALSE)");
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void confirmFriendRequest(int senderid, int recipientid, int messageID) {
		if(connection == null) {
			connection = Database.connect();
		}
		try {
			Statement stmt = connection.createStatement();
			stmt.execute("DELETE FROM friend_requests WHERE ref=\"" + messageID + "\"");
			//add both friends to friendshiptable
			Statement f1 = connection.createStatement();
			f1.execute("INSERT INTO friendships (userID, friendID) VALUES (\""+ senderid +"\",\"" + recipientid + "\")");
			Statement f2 = connection.createStatement();
			f2.execute("INSERT INTO friendships (userID, friendID) VALUES (\""+ recipientid +"\",\"" + senderid + "\")");
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void denyFriendRequest(int senderid, int recipientid, int messageID) {
		if(connection == null) {
			connection = Database.connect();
		}
		try {
			Statement stmt = connection.createStatement();
			stmt.execute("DELETE FROM friend_requests WHERE ref=\"" + messageID + "\"");
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void deleteFriendship(int senderid, int recipientid) {
		if(connection == null) {
			connection = Database.connect();
		}
		try {
			System.out.println("Deleting " + senderid + " AND " + recipientid);
			Statement stmt = connection.createStatement();
			stmt.execute("DELETE FROM friendships WHERE userID=\"" + senderid + "\" AND friendID=\"" + recipientid + "\"");
			Statement stmt2 = connection.createStatement();
			stmt2.execute("DELETE FROM friendships WHERE userID=\"" + recipientid + "\" AND friendID=\"" + senderid + "\"");
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
