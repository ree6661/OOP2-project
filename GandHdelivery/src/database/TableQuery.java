package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableQuery {
	
	private Connection DB;
	private String table;
	
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
	
}
