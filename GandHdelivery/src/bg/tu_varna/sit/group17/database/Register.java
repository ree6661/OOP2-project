package bg.tu_varna.sit.group17.database;

import java.sql.SQLException;

import bg.tu_varna.sit.group17.application.Load;
import bg.tu_varna.sit.group17.application.LoggerApp;
import bg.tu_varna.sit.group17.application.MessageBox;
import bg.tu_varna.sit.group17.application.Property;
import bg.tu_varna.sit.group17.controllers.RegisterController;
import bg.tu_varna.sit.group17.database.users.Consumer;
import bg.tu_varna.sit.group17.validation.Valid;
import javafx.scene.control.Button;

public final class Register {
	private final LoggerApp logger = new LoggerApp(getClass().getName());
	private final MessageBox message = new MessageBox(logger);
	private Load load;
	private Consumer consumer;
	private Button notificationBell;
	
	public Register(RegisterController reg) {
		this.load = reg.load;
		this.consumer = reg.consumer;
		this.notificationBell = reg.notificationBell;
	}
	
	public void prepareForm() {
		courierNotification();
	}
	
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
			Add.customer(username, phoneNumber, Property.citiesMap.get(city), location, password);

			message.alert("Успешна регистрация.");
			logger.info("Successful register");
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}
	
	private void courierNotification() {
		try {
			load.notification.courier(consumer, notificationBell);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}
}
