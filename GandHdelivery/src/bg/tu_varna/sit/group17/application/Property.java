package bg.tu_varna.sit.group17.application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import bg.tu_varna.sit.group17.database.Create;

public final class Property {
	public static HashMap<String, Integer> citiesMap;
	public static HashMap<String, Integer> companiesMap;
	public static int[] statusesId = {1, 2, 3, 4};
	//1 неполучен 2 отказана 3 получена 4 взета
	public static void initAll() throws SQLException {
		initCityMap();
		initCompaniesMap();
	}
	
	public static void initCityMap() throws SQLException {
		
		Connection con = Create.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from cities");
		
		Property.citiesMap = new HashMap<String, Integer>();
		
		while(rs.next()) 
			Property.citiesMap.put(rs.getString("city"),
						Integer.parseInt(rs.getString("id_city")));

	}
	
	public static void initCompaniesMap() throws SQLException {
		
		Connection con = Create.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from companies");
		
		companiesMap = new HashMap<String, Integer>();
		
		while(rs.next()) 
			companiesMap.put(rs.getString(2),
						Integer.parseInt(rs.getString(1)));
	}
}
