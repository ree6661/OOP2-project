package bg.tu_varna.sit.group17.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import bg.tu_varna.sit.group17.database.users.Admin;
import bg.tu_varna.sit.group17.database.users.Consumer;
import bg.tu_varna.sit.group17.database.users.Courier;
import bg.tu_varna.sit.group17.database.users.Customer;
import bg.tu_varna.sit.group17.database.users.User;

/**
 * Class for creating user objects.
 */
public final class UserCreator {

	/**
	 * Creates user object from data about him.
	 * 
	 * @param user     type of user to be created.
	 * @param userData ResultSet containing data for creating the user.
	 * @return new user.
	 * @throws SQLException if a problem with the database occurs.
	 */
	public Consumer create(User user, ResultSet userData) throws SQLException {
		if (userData == null)
			throw new SQLException("User data is null");

		return switch (user) {
		case Admin -> Admin.create(userData);
		case Courier -> Courier.create(userData);
		case Customer -> Customer.create(userData);
		default -> throw new SQLException("Consumer not found: ".concat(user.toString()));
		};
	}
}
