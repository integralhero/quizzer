package quiz;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ParseDateString {
	
	public static long getMilliseconds(String date){
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		try {
			d = sdfDate.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long milliseconds = d.getTime();
		return milliseconds;
	}
}
