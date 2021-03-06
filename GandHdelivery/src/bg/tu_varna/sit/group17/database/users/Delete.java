package bg.tu_varna.sit.group17.database.users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bg.tu_varna.sit.group17.database.Create;
import bg.tu_varna.sit.group17.database.property.Company;
import bg.tu_varna.sit.group17.database.property.Office;

public class Delete {
	private Delete() {
		//utility
	}
	
	public static void company(Company company) throws SQLException {
		if(company == null) {
			return;
		}
		Connection conn = Create.getConnection();
		
		if(company.offices.size() != 0) 
			for(int i = 0; i < company.offices.size(); ++i) 
				Delete.office(company.offices.get(i));
		
		final String sql = "DELETE FROM companies WHERE id_company=?";
		
		PreparedStatement st = conn.prepareStatement(sql);
		st.setInt(1, company.getId());
		
		st.execute();
		st.close();
	}
	
	public static void companyCategory(Company company) throws SQLException {
		if(company == null) {
			return;
		}
		Connection conn = Create.getConnection();
		
		if(company.offices.size() != 0) 
			for(int i = 0; i < company.offices.size(); ++i) 
				Delete.office(company.offices.get(i));
		
		
		String sql = "delete from price_list where id_company=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setInt(1, company.getId());
		ps.execute();
		
		sql = "DELETE FROM companies WHERE id_company=?";
		
		ps = conn.prepareStatement(sql);
		ps.setInt(1, company.getId());
		ps.execute();
		
		ps.close();
	}
	
	public static void office(Office office) throws SQLException {
		if(office == null) {
			return;
		}
		Connection conn = Create.getConnection();
		
		if(office.couriers.size() != 0) 
			for(int i = 0; i < office.couriers.size(); ++i) 
				Delete.courier(office.couriers.get(i));
		
		final String sql = "DELETE FROM office WHERE id_office=?";
		
		PreparedStatement st = conn.prepareStatement(sql);
		st.setInt(1, office.getId_office());
		
		st.execute();
		st.close();
	}
	
	public static void courier(Courier courier) throws SQLException {
		if(courier == null) {
			return;
		}
		Connection conn = Create.getConnection();
		
		final String sql = "DELETE FROM couriers WHERE id_courier=?";
		
		PreparedStatement st = conn.prepareStatement(sql);
		st.setInt(1, courier.getId());
		
		st.execute();
		st.close();
	}
}
