package bg.tu_varna.sit.group17.database.users;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class represents the customer from the database.
 */
public final class Customer extends Consumer {

	private int id_city;
	private String address, city_name = "";

	public Customer() {
		super();
		this.id_city = 0;
		this.address = "";
	}

	public Customer(int id, String name, String phone, String password, int id_city, String address) {
		super(id, name, phone, password, User.Customer);
		this.id_city = id_city;
		this.address = address;
	}

	public static Customer create(ResultSet rs) throws SQLException {
		Consumer c = Consumer.create(rs);

		int id_city = rs.getInt("id_city");
		String address = rs.getString("address");

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

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

}
