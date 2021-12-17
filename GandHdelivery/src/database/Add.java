package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class Add {
	
	public static void customer(String name, String phone, int city, String address, String password) {
		
		try {
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
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
