package validation;

import java.util.regex.Pattern;

public final class Valid {
	
	public static boolean username(String username) {
		if(username == null) return false;
		
		final String regex = "1234567890!@№%€§*()-–=+`~_#^&[]{};:\'\"$\\/,<.>|?";
        for(char ch : regex.toCharArray()) 
        	if(username.contains(Character.toString(ch))) 
        		return false;
        return true;
	}
	
	public static boolean phoneNumber(String phone) {
		if(phone == null || phone.isBlank() || phone.length() != 12) 
			return false;
		
		final String regex = "[0-9]+";
		final Pattern p = Pattern.compile(regex);
		
		return p.matcher(phone).matches();
	}
	
	public static boolean password(String password) {
		return password.length() > 4 && password.length() < 31;
	}
}