package quiz;

public class QuestionTypes {
	
	private static final int QUESTION_RESPONSE = 1;
	private static final int FILL_BLANK = 2;
	private static final int MULT_CHOICE = 3;
	private static final int PIC_RES = 4;
	
	public static int getType(String type){
		if (type.equals("q_question_response")) return QUESTION_RESPONSE;
		else if (type.equals("q_fill_in_blank")) return FILL_BLANK;
		else if (type.equals("q_multiple_choice")) return MULT_CHOICE;
		else if (type.equals("q_picture_response")) return PIC_RES;
		return -1;
	}
}
