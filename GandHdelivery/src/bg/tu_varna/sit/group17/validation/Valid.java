package bg.tu_varna.sit.group17.validation;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

import bg.tu_varna.sit.group17.database.TableQuery;

public final class Valid {
	private Valid() {
		//utility
	}
	
	public static boolean username(String username) {
		if(username == null || username.equals("")) return false;
		if(username.length() > 45) return false;
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
		if(password == null) return false;
		return password.length() > 4 && password.length() < 31;
	}
	
	public static String user(String name, String phone, String password, String repeatPassword) throws SQLException {
		
		if(!Valid.username(name)) 
    		return "Името не трябва да съдържа символи и да е твърде кратко";
    	
    	if(!Valid.phoneNumber(phone)) 
    		return "Телефона трябва да съдържа 12 цифри";
    		
    	if(!Valid.password(password)) 
    		return "Паролата трябва да е между 5 и 30 символа";
    		
    	if(!password.equals(repeatPassword)) 
    		return "Паролите не съвпадат";
    	
    	if(TableQuery.phoneExists(phone)) 
    		return "Вече съществува такъв телефон в базата данни";
    	
		return "";
	}
	
	public static boolean date(String date) {
		try {
			LocalDate.parse(date);
		}catch(DateTimeParseException e) {return false;}
		return true;
	}
	
	public static boolean order(String phoneSender, String phoneReceiver, String date1, String date2) {
		
		if(phoneSender == null || phoneReceiver == null || date1 == null || date2 == null) return false;
		if(!Valid.date(date1) || !Valid.date(date2)) {
			//Launch.alert("Невалидни дати");
			return false;
		}
		
		if(phoneSender.equals("") || phoneReceiver.equals("") || date1.equals("") || date2.equals("")) {
			//Launch.alert("Всички полета трябва да са попълнени");
			return false;
		}
		if(phoneSender.equals(phoneReceiver)) {
			//Launch.alert("Телефоните не може да са еднакви");
			return false;
		}
		
		if(!Valid.phoneNumber(phoneSender) || ! Valid.phoneNumber(phoneReceiver)) {
			//Launch.alert("Невалиден телефонен номер");
			return false;
		}
		return true;
	}
	
	public static boolean price(double price) {
		if(price < 0) return false;
		
		return true;
	}
}