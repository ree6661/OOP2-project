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
}
