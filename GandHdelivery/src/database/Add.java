package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class Add {
	
	public static void company(String name) throws SQLException {
		Connection conn = Create.getConnection();
		
		final String sql = "insert into companies(company) values(?)";
		
		PreparedStatement st = conn.prepareStatement(sql);
		st.setString(1, name);
		
		st.execute();
		st.close();
	}
	
	public static void customer(String name, String phone, int city, String address, String password) throws SQLException {
		Connection conn = Create.getConnection();
			
		final String sql = 
				"insert into customers(customer, phone, "
				+ "id_city, address, password)"
		        + " values (?, ?, ?, ?, ?)";
		
		PreparedStatement st = conn.prepareStatement(sql);
		st.setString(1, name);
		st.setString(2, phone);
		st.setInt(3, city);
		st.setString(4, address);
		st.setString(5, password);
		
		st.execute();
		st.close();
	}
	
	public static void office(int id_company, int id_city, String address) throws SQLException {
		Connection conn = Create.getConnection();
		
		final String sql = "insert into office(id_company,"
						+ " id_city, address) values(?, ?, ?)";
		
		PreparedStatement st = conn.prepareStatement(sql);
		st.setInt(1, id_company);
		st.setInt(2, id_city);
		st.setString(3, address);
		
		st.execute();
		st.close();
	}
	
	public static void courier(String name, String phone, String password, int id_office) throws SQLException {
		Connection conn = Create.getConnection();
		
		final String sql = "insert into couriers(name, phone,"
						+ " password, id_office) values(?, ?, ?, ?)";
		
		PreparedStatement st = conn.prepareStatement(sql);
		st.setString(1, name);
		st.setString(2, phone);
		st.setString(3, password);
		st.setInt(4, id_office);
		
		st.execute();
		st.close();
	}
	
	public static void order(int id_category, 
			int id_office_sender, int id_office_recipient, 
			int id_customer_sender, int id_customer_recipient, 
			int id_courier, int id_status, boolean fragile,
			boolean paid, double cash_on_delivery, boolean delivery_to_address, String address,
			Date acceptance_by_sender, Date customer_delivery) throws SQLException {
		
		Connection conn = Create.getConnection();
		
		final String sql = "insert into orders(id_category,"
				+ " id_office_sender, id_office_recipient,"
				+ " id_customer_sender, id_customer_recipient,"
				+ " id_courier, id_status, fragile,"
				+ " paid, cash_on_delivery, delivery_to_address, address,"
				+ " acceptance_by_sender, customer_delivery) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		PreparedStatement st = conn.prepareStatement(sql);
		st.setInt(1, id_category);
		st.setInt(2, id_office_sender);
		st.setInt(3, id_office_recipient);
		st.setInt(4, id_customer_sender);
		st.setInt(5, id_customer_recipient);
		st.setInt(6, id_courier);
		st.setInt(7, id_status);
		st.setBoolean(8, fragile);
		st.setBoolean(9, paid);
		st.setDouble(10, cash_on_delivery);
		st.setBoolean(11, delivery_to_address);
		st.setString(12, address);
		st.setDate(13, acceptance_by_sender);
		st.setDate(14, customer_delivery);
		
		st.execute();
		st.close();
	}
}
