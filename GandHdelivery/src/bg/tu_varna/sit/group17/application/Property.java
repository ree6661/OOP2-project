package bg.tu_varna.sit.group17.application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import bg.tu_varna.sit.group17.database.Create;
import bg.tu_varna.sit.group17.database.TableQuery;

public final class Property {
	private final LoggerApp logger = new LoggerApp(getClass().getName());
	private Map<String, Integer> citiesMap;
	private Map<String, Integer> companiesMap;

	public void initAll() {
		initCities();
		initCompanies();
	}

	public void initCities() {
		try {
			ResultSet rs = TableQuery.execute("select * from cities");

			citiesMap = new HashMap<String, Integer>();
			if (rs == null)
				return;
			do {
				citiesMap.put(rs.getString("city"), Integer.parseInt(rs.getString("id_city")));
			} while (rs.next());
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}

	public void initCompanies() {
		try {
			Connection con = Create.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from companies");

			companiesMap = new HashMap<String, Integer>();

			while (rs.next())
				companiesMap.put(rs.getString(2), Integer.parseInt(rs.getString(1)));
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}

	public Map<String, Integer> getCities() {
		return this.citiesMap;
	}

	public Map<String, Integer> getCompanies() {
		return this.citiesMap;
	}
}
