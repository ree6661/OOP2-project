package database.users;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer extends Consumer {
	
	private int id_city;
	private String address;
	
	public Customer(int id, String name, String phone, String password, int id_city, String address) {
		super(id, name, phone, password);
		this.id_city = id_city;
		this.address = address;
	}
	
	public static Customer create(ResultSet rs) throws SQLException {
		Consumer c = Consumer.create(rs);
		
		int id_city = 0;
		String address = null;
		try {
			id_city = rs.getInt(5);
			address = rs.getString(6);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return new Customer(c.getId(), c.getName(), c.getPhone(), c.getPassword(), id_city, address);
	}
	
	public int getId_city() {
		return id_city;
	}

	public void setId_city(int id_city) {
		this.id_city = id_city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
