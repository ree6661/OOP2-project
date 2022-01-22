package bg.tu_varna.sit.group17.application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import bg.tu_varna.sit.group17.database.Create;
import bg.tu_varna.sit.group17.database.TableQuery;

public final class Property {
	private Property() {
		// utility
	}

	public static HashMap<String, Integer> citiesMap;
	public static HashMap<String, Integer> companiesMap;

	// public static String username = "";
	// public static User user = User.Guest;//1 customer 2 courier 3 admin

	public static final String ginko = "https://medpedia.framar.bg/%D0%B0%D0%BB"
			+ "%D1%82%D0%B5%D1%80%D0%BD%D0%B0%D1%82%D0%B8%D0%B2%D0%BD%D0%B0"
			+ "-%D0%BC%D0%B5%D0%B4%D0%B8%D1%86%D0%B8%D0%BD%D0%B0/%D0%BA%D0%B0"
			+ "%D0%BA-%D0%B4%D0%B0-%D0%BF%D0%BE%D0%B4%D1%81%D0%B8%D0%BB%D0%B8%D0"
			+ "%BC-%D0%BF%D0%B0%D0%BC%D0%B5%D1%82%D1%82%D0%B0-%D1%81%D0%B8";

	public static void initAll() throws SQLException {
		initCityMap();
		initCompaniesMap();
	}

	public static void initCityMap() throws SQLException {

		ResultSet rs = TableQuery.execute("select * from cities");

		Property.citiesMap = new HashMap<String, Integer>();
		if (rs == null)
			return;
		do {
			Property.citiesMap.put(rs.getString("city"), Integer.parseInt(rs.getString("id_city")));
		} while (rs.next());
	}

	public static void initCompaniesMap() throws SQLException {

		Connection con = Create.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from companies");

		companiesMap = new HashMap<String, Integer>();

		while (rs.next())
			companiesMap.put(rs.getString(2), Integer.parseInt(rs.getString(1)));
	}
}
