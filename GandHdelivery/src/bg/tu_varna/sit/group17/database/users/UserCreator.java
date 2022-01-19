package bg.tu_varna.sit.group17.database.users;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class UserCreator {
	private UserCreator() {
		//utility
	}
	public static Consumer create(User user, ResultSet userData) throws SQLException {
		if(userData == null) throw new SQLException("User data is null");
		
		return switch(user) {
		case Admin -> Admin.create(userData);
		case Courier -> Courier.create(userData);
		case Customer -> Customer.create(userData);
		default->
			throw new SQLException("Consumer not found: ".concat(user.toString()));
		};
	}
}
