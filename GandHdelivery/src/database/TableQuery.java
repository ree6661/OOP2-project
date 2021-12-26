package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import com.mysql.cj.xdevapi.Table;

public class TableQuery {
	
	private Connection DB;
	private String table;
	public static final String[] users = 
		{"customers", "couriers", "admins"};
	
	public TableQuery(String tableName) {
		try {
			DB = Create.getConnection();
			table = tableName;
			DatabaseMetaData md = DB.getMetaData();
			ResultSet rs = md.getTables(null, null, table, null);
			if(!rs.next()) 
				throw new IllegalArgumentException(
						"Table " + tableName + " not exists");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ResultSet execute(String sql) throws SQLException {
		Connection DB = Create.getConnection();
		PreparedStatement ps = DB.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();
		
		
		if(!rs.next()) return null;
		
		return rs;
	}
	
	public boolean contains(String column, String contains) {
		try {
			String sql = "select " + column + " from " + table + 
					" where " + column + " = '" + contains + "'";
			PreparedStatement ps = DB.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			return rs.next();
			
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static <T> ResultSet getRecordFromTable(
			String valueColumnName, T value, String table) throws SQLException {
		
		
		Connection DB = Create.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "";
		
		sql = "select * from " + table + 
				" where " + valueColumnName + " = '" + value + "'";
		ps = DB.prepareStatement(sql);
		rs = ps.executeQuery();
		if(!rs.next()) return null;
		
		return rs;
	}
	
	public static String getRecordFromTables(
			String recordToReturn, String valueColumnName, 
			String value, String... tables) {
		// select password from customer where phone = phoneNumber
		try {
			Connection DB = Create.getConnection();
			PreparedStatement ps;
			String sql = "";
			ResultSet rs;
			
			
			for(int i = 0; i < tables.length; ++i) {
				sql = "select *" /*+ recordToReturn */+ " from " + tables[i] + 
						" where " + valueColumnName + " = '" + value + "'";
				 ps = DB.prepareStatement(sql);
				 rs = ps.executeQuery();
				 
				 if(rs.next()) return rs.getString(recordToReturn);
					 //return rs.getString(1);
				 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	public static String searchInTables(String find, String columnName, String... toSearch) {
		
		TableQuery query;
		for(int i = 0; i < toSearch.length; ++i) {
			query = new TableQuery(toSearch[i]);
			
			if(query.contains(columnName, find)) 
				return toSearch[i];
		}
		
		return "";
	}
	
	public static String cityIdToName(int id) throws SQLException {
		final String sql = "select city from cities where id_city='" + id + "'";
		ResultSet officeCity = TableQuery.execute(sql);
		
		return officeCity.getString(1);
	}
	
	public static boolean phoneExists(String phone) throws SQLException {
		
		ResultSet record = null;
		for(int i = 0; i < TableQuery.users.length; ++i) {
    		record = TableQuery.getRecordFromTable(
    					"phone", phone, users[i]); 
    		if(record != null) return true;
		}
		return false;
	}
	public static int getCompanyId(String companyName) throws SQLException {
		String sql = "select * from companies where company='" + companyName + "'";
		ResultSet rs = TableQuery.execute(sql);
		
		if(rs == null) return 0;
		return rs.getInt("id_company");
	}
	
	public static LinkedList<Company> allCompanies() throws SQLException {
		LinkedList<Company> companies = new LinkedList<>();
		
		String sql = "select * from companies";
		
		ResultSet rs = TableQuery.execute(sql);

		do companies.add(new Company(rs.getInt("id_company"), rs.getString("company")));
		while(rs.next());
		
		for(int i = 0; i < companies.size(); ++i) {
		
			sql = "select * from office where id_company='" + companies.get(i).getId() + "'";
			rs = TableQuery.execute(sql);
			if(rs == null) continue;
			do companies.get(i).offices.add(
					new Office(rs.getInt("id_office"), rs.getInt("id_company"),
							rs.getInt("id_city"), rs.getString("address")));
			while(rs.next());
		}
		
		return companies;
	}
	
	public static int getCategoryId(String category) throws SQLException {
		String sql = "select id_category from categories where category='" + category + "'";
		
		ResultSet rs = TableQuery.execute(sql);
		
		if(rs == null) return 0;
		
		return rs.getInt("id_category");
	}
	
	public static int getOfficeId(String fullAddress) throws SQLException {
		String sql = "select * from office";
		
		ResultSet rs = TableQuery.execute(sql);
		
		if(rs == null) return 0;
		Company c = new Company();
		c.setOffices(rs);
		for(int i = 0; i < c.offices.size(); ++i) {
			Office o = c.offices.get(i);
			if(o.getFullAddress().equals(fullAddress))
				return o.getId_office();
		}
		return 0;
	}
	
	public static int getCustomerId(String phone) throws SQLException {
		String sql = "select id_customer from customers where phone='" + phone + "'";
		
		ResultSet rs = TableQuery.execute(sql);
		
		if(rs == null) return 0;
		
		return rs.getInt("id_customer");
	}
}
