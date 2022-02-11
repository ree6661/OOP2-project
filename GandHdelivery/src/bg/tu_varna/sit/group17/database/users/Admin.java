package bg.tu_varna.sit.group17.database.users;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class represents the administrator from the database.
 */
public final class Admin extends Consumer {

	public Admin() {
		super();
	}

	public Admin(int id, String name, String phone, String password) {
		super(id, name, phone, password, User.Admin);
	}

	public static Admin create(ResultSet rs) throws SQLException {
		Consumer c = Consumer.create(rs);

		return new Admin(c.getId(), c.getName(), c.getPhone(), c.getPassword());
	}
}
