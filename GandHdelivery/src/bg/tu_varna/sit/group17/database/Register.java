package bg.tu_varna.sit.group17.database;

import java.sql.SQLException;

import bg.tu_varna.sit.group17.application.Load;
import bg.tu_varna.sit.group17.application.LoggerApp;
import bg.tu_varna.sit.group17.application.MessageBox;
import bg.tu_varna.sit.group17.controllers.RegisterController;
import bg.tu_varna.sit.group17.database.users.Consumer;
import bg.tu_varna.sit.group17.validation.Valid;
import javafx.scene.control.Button;

/**
 * This class manages the functionality of the register form.
 */
public final class Register {
	private final LoggerApp logger = new LoggerApp(getClass().getName());
	private final MessageBox message = new MessageBox(logger);
	private Load load;
	private Consumer consumer;
	private Button notificationBell;

	/**
	 * @param load
	 * @param consumer
	 * @param notificationBell
	 */
	public Register(Load load, Consumer consumer, Button notificationBell) {
		this.load = load;
		this.consumer = consumer;
		this.notificationBell = notificationBell;
	}

	/**
	 * Loads the object's fields and prepares them for usage.
	 */
	public void prepareForm() {
		courierNotification();
	}

	/**
	 * Adds new customer from the fields of the register form.
	 * 
	 * @param username       the customer's username.
	 * @param phoneNumber    the customer's phone number.
	 * @param city           the customer's current city.
	 * @param location       the customer's current addres.
	 * @param password       the customer's account password.
	 * @param repeatPassword the customer's account repeated password.
	 */
	public void addCustomer(String username, String phoneNumber, String city, String location, String password,
			String repeatPassword) {
		try {
			logger.info("Clicked register");
			String err = "";
			err = Valid.user(username, phoneNumber, password, repeatPassword);
			if (!err.equals("")) {
				throw new IllegalArgumentException(err);
			}
		} catch (IllegalArgumentException e) {
			message.alert("Невалидни телефон и/или парола");
			logger.error(e.getMessage());
		}
		if (city.equals("град")) {
			city = "";
		}

		try {
			Add.customer(username, phoneNumber, load.getProperty().getCities().get(city), location, password);

			message.alert("Успешна регистрация.");
			logger.info("Successful register");
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}

	private void courierNotification() {
		try {
			load.getNotification().courier(consumer, notificationBell);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}
}
