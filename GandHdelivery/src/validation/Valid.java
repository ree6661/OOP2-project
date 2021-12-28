package validation;

import java.sql.SQLException;
import java.util.regex.Pattern;

import application.Launch;
import database.TableQuery;

public final class Valid {
	
	public static boolean username(String username) {
		if(username == null || username.equals("")) return false;
		
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
	
	public static String user(String name, String phone, String password, String repeatPassword) throws SQLException {
		
		if(!Valid.username(name)) 
    		return "Името не трябва да съдържа символи и да е твърде кратко";
    	
    	if(!Valid.phoneNumber(phone)) 
    		return "Телефона трябва да съдържа 12 цифри";
    		
    	if(!Valid.password(password)) 
    		return "Паролата трябва да е между 5 и 30 символа";
    		
    	if(!repeatPassword.equals(password)) 
    		return "Паролите не съвпадат";
    	
    	if(TableQuery.phoneExists(phone)) 
    		return "Вече съществува такъв телефон в базата данни";
    	
		return "";
	}
	
	public static boolean order(String phoneSender, String phoneReceiver, String date1, String date2) {
		
		if(phoneSender.equals("") || phoneReceiver.equals("") || date1.equals("") || date2.equals("")) {
			Launch.alert("Всички полета трябва да са попълнени");
			return false;
		}
		if(phoneSender.equals(phoneReceiver)) {
			Launch.alert("Телефоните не може да са еднакви");
			return false;
		}
		
		if(!Valid.phoneNumber(phoneSender) || ! Valid.phoneNumber(phoneReceiver)) {
			Launch.alert("Невалиден телефонен номер");
			return false;
		}
		return true;
	}
	
	public static boolean price(double price) {
		if(price < 0) return false;
		
		return true;
	}
}