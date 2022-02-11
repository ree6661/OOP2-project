package bg.tu_varna.sit.group17.validation;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

import bg.tu_varna.sit.group17.application.LoggerApp;
import bg.tu_varna.sit.group17.database.TableQuery;

/**
 * This class is used for validating fields.
 */
public final class Valid {
	private static final LoggerApp logger = new LoggerApp(Valid.class.getName());

	private Valid() {
		// utility
	}

	/**
	 * Checks if a username is valid.
	 * 
	 * @param username usename to be validated.
	 * @return if username is valid.
	 */
	public static boolean username(String username) {
		if (username == null || username.equals("") || username.length() > 45)
			return false;

		final String regex = "1234567890!@№%€§*()-–=+`~_#^&[]{};:\'\"$\\/,<.>|?";
		for (char ch : regex.toCharArray())
			if (username.contains(Character.toString(ch)))
				return false;
		return true;
	}

	/**
	 * Checks if a phone number is valid.
	 * 
	 * @param phone phone number to be validated.
	 * @return if the phone number is valid.
	 */
	public static boolean phoneNumber(String phone) {
		if (phone == null || phone.isBlank() || phone.length() != 12)
			return false;

		final String regex = "[0-9]+";
		final Pattern p = Pattern.compile(regex);

		return p.matcher(phone).matches();
	}

	/**
	 * Checks if a password is valid.
	 * 
	 * @param password password to be validated.
	 * @return if the password is valid.
	 */
	public static boolean password(String password) {
		if (password == null)
			return false;
		return password.length() > 4 && password.length() < 31;
	}

	/**
	 * Checks if user is valid.
	 * 
	 * @param name           name of the user.
	 * @param phone          phone number of the user.
	 * @param password       user's password.
	 * @param repeatPassword user's repeated password.
	 * @return error message if user is not valid or empty String if the user is
	 *         valid.
	 */
	public static String user(String name, String phone, String password, String repeatPassword) {

		if (!Valid.username(name))
			return "Името не трябва да съдържа символи и да е твърде кратко";

		if (!Valid.phoneNumber(phone))
			return "Телефона трябва да съдържа 12 цифри";

		if (!Valid.password(password))
			return "Паролата трябва да е между 5 и 30 символа";

		if (!password.equals(repeatPassword))
			return "Паролите не съвпадат";

		try {
			if (TableQuery.phoneExists(phone))
				return "Вече съществува такъв телефон в базата данни";
		} catch (SQLException e) {
			logger.error(e.getMessage());
			return "Вече съществува такъв телефон в базата данни";
		}

		return "";
	}

	/**
	 * Checks if a date is valid.
	 * 
	 * @param date date to be validated.
	 * @return if the date is valid.
	 */
	public static boolean date(String date) {
		try {
			LocalDate.parse(date);
		} catch (DateTimeParseException e) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if an order is valid.
	 * 
	 * @param phoneSender sender of the order.
	 * @param phoneReceiver receiver of the order.
	 * @param date1 creation date of the order.
	 * @param date2 delivery date of the order.
	 * @return if the order is valid.
	 */
	public static boolean order(String phoneSender, String phoneReceiver, String date1, String date2) {

		if (phoneSender == null || phoneReceiver == null || date1 == null || date2 == null)
			return false;
		if (!Valid.date(date1) || !Valid.date(date2)) {
			return false;
		}

		if (phoneSender.equals("") || phoneReceiver.equals("") || date1.equals("") || date2.equals("")) {
			return false;
		}
		if (phoneSender.equals(phoneReceiver)) {
			return false;
		}

		if (!Valid.phoneNumber(phoneSender) || !Valid.phoneNumber(phoneReceiver)) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if a price is valid.
	 * 
	 * @param price price to be validated.
	 * @return if the price is valid.
	 */
	public static boolean price(double price) {
		return price >= 0;
	}
}