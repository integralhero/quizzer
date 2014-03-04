package quiz;

public class QuestionTypes {
	
	private static final int QUESTION_RESPONSE = 1;
	private static final int FILL_BLANK = 2;
	private static final int MULT_CHOICE = 3;
	private static final int PIC_RES = 4;
	
	public static int getType(String type){
		if (type.equals("QuestionResponse")) return QUESTION_RESPONSE;
		else if (type.equals("FillInBlank")) return FILL_BLANK;
		else if (type.equals("MultipleChoie")) return MULT_CHOICE;
		else if (type.equals("PictureResponse")) return PIC_RES;
		return -1;
	}
}
