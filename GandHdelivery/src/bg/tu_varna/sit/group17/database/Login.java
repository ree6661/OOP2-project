package bg.tu_varna.sit.group17.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import bg.tu_varna.sit.group17.application.Load;
import bg.tu_varna.sit.group17.application.LoggerApp;
import bg.tu_varna.sit.group17.application.MessageBox;
import bg.tu_varna.sit.group17.database.users.Consumer;
import bg.tu_varna.sit.group17.database.users.User;
import bg.tu_varna.sit.group17.database.users.UserCreator;
import bg.tu_varna.sit.group17.validation.Valid;

/**
 * This class manages the functionality of the login form.
 */
public final class Login {
	private final LoggerApp logger = new LoggerApp(getClass().getName());
	private final MessageBox message = new MessageBox(logger);
	private Load load;

	/**
	 * @param load the load object with information of the current application lifecycle.
	 */
	public Login(Load load) {
		this.load = load;
	}

	/**
	 * Logs a user into the application.
	 * 
	 * @param phoneNumber user's phone number.
	 * @param password user's password.
	 */
	public void user(String phoneNumber, String password) {
		ResultSet record = null;
		try {
			if (!Valid.phoneNumber(phoneNumber) || !Valid.password(password)) {
				throw new IllegalArgumentException("Invalid phone or password " + phoneNumber + " " + password);
			}

			for (User user : User.values()) {
				if (user == User.Guest)
					continue;
				String tableName = user.getTableName();
				record = TableQuery.execute("select * from " + tableName + " where phone='" + phoneNumber
						+ "' and password='" + password + "'");

				if (record != null) {
					loadForm(user, record);
					return;
				}
			}
			if (record == null)
				throw new IllegalArgumentException("Invalid phone or password " + phoneNumber + " " + password);

		} catch (SQLException | IllegalArgumentException e) {
			message.alert("Невалидни телефон и/или парола");
			logger.error(e.getMessage());
		}
	}

	private void loadForm(User user, ResultSet userResult) throws SQLException {
		final Consumer consumer = UserCreator.create(user, userResult);
		load.form(user.getFormName(), consumer);
	}
}
