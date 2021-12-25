package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Update {
	
	public static void office(int id_office, int id_city, String address) throws SQLException {
		Connection conn = Create.getConnection();
		
		final String sql = "update office set id_city=?, address=? where id_office=?";
		
		PreparedStatement st = conn.prepareStatement(sql);
		st.setInt(1, id_city);
		st.setString(2, address);
		st.setInt(3, id_office);
		
		st.executeUpdate();
		st.close();
	}
	
	public static void courier(int id_courier, String name, String phone, String password) throws SQLException {
		Connection conn = Create.getConnection();
		
		final String sql = "update couriers set name=?, phone=?, password=? where id_courier=?";
		
		PreparedStatement st = conn.prepareStatement(sql);
		st.setString(1, name);
		st.setString(2, phone);
		st.setString(3, password);
		st.setInt(4, id_courier);
		
		st.executeUpdate();
		st.close();
	}
}