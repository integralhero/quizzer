package quiz;

import java.security.*;

public class Hasher {
	
    public static final int SALT_SIZE = 24;
    
	public static String generateSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_SIZE];
        random.nextBytes(salt);
        
        return hexToString(salt);
	}
	public static String generateSaltedHash(String password, String salt){
		String temp = "";
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			String saltedPassword = password + salt;
			byte[] bytes = saltedPassword.getBytes();
			temp = hexToString(md.digest(bytes));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//generate hash
		return temp;
	}
	
	public static boolean compareHash(String enteredPassword, String DBHash, String salt){
		String newHash = generateSaltedHash(enteredPassword, salt);
		if(newHash.equals(DBHash)){
			return true;
		}
		
		return false;
	}
	
	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	*/
	public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
}