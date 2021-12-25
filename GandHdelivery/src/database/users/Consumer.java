package database.users;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Consumer {
	
	protected int id;
	protected String name;
	protected String phone;
	protected String password;
	
	public Consumer() {
		this.id = 0;
		this.name = "";
		this.phone = "";
		this.password = "";
	}
	
	public Consumer(int id, String name, String phone, String password) {
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.password = password;
	}
	
	public static Consumer create(ResultSet rs) throws SQLException {
		int id = 0;
		String name = null, phone = null, password = null;
		
		id = rs.getInt(1);
		name = rs.getString(2); 
		phone = rs.getString(3);
		password = rs.getString(4);		
		
		return new Consumer(id, name, phone, password);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
