package bg.tu_varna.sit.group17.database;

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
	
	public static void company(int id_company, String company) throws SQLException {
		Connection conn = Create.getConnection();
		
		final String sql = "update companies set company=? where id_company=?";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, company);
		ps.setInt(2, id_company);
		
		ps.executeUpdate();
		ps.close();
	}
	
	public static void companyCategory(int id_company, String company, double plik, double kolet, double paket, double tovar) throws SQLException {
		Connection conn = Create.getConnection();
		
		String sql = "update companies set company=? where id_company=?";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, company);
		ps.setInt(2, id_company);
		
		ps.executeUpdate();
		
		sql = "update price_list set price=? where id_company=? and id_category=?";
		double[] categories = {plik, kolet, paket, tovar};
		for(int i = 1; i < 5; ++i) {			
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, categories[i-1]);
			ps.setInt(2, id_company);
			ps.setInt(3, i);
			ps.executeUpdate();
		}
		
		ps.close();
	}
	
	public static void changeOrderStatus(int id_order, int id_status) throws SQLException {
		Connection conn = Create.getConnection();
		
		String sql = "update orders set id_status=? where id_order=?";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, id_status);
		ps.setInt(2, id_order);
		
		ps.executeUpdate();
		
		ps.close();
	}
}