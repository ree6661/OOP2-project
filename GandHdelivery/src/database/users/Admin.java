package database.users;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin extends Consumer {
	
	public Admin() {
		super();
	}
	
	public Admin(int id, String name, String phone, String password) {
		super(id, name, phone, password);
	}
	
	public static Admin create(ResultSet rs) throws SQLException {
		Consumer c = Consumer.create(rs);
		
		return new Admin(c.getId(), c.getName(), c.getPhone(), c.getPassword());
	}
}
