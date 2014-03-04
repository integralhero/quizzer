package quiz;

import java.util.*;

public class ParseAnswers {

	public static String getString(ArrayList<String> answers){
		if (answers.size() == 1) return answers.get(0);
		String temp = "";
		for(int i = 0; i < answers.size() - 1; i++){
			temp += answers.get(i);
			temp += ",";
		}
		temp += answers.get(answers.size() - 1);
		return temp;
	}
	
	public static ArrayList<String> getArrayList(String answers){
		List<String> list = Arrays.asList(answers.split("\\s*,\\s*"));
		return new ArrayList<String>(list);
	}
}
