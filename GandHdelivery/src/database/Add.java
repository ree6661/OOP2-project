package database;

import java.sql.Connection;
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
}
