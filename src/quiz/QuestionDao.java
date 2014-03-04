package quiz;

import java.sql.*;

public class QuestionDao {
	private static Connection connection = Database.connect();

	public static int getID(Question question){
		int id = 0;
		try {
			String command = "SELECT * FROM " + question.type + " WHERE answers = \"" + ParseAnswers.getString(question.answers) + "\"";

			Statement statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(command);	
			if(rs.next()) id = rs.getInt("id");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}
}
