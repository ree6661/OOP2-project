package bg.tu_varna.sit.group17.application;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import bg.tu_varna.sit.group17.database.Create;
import bg.tu_varna.sit.group17.database.TableQuery;

/**
 * This class stores information fields from the database which will most likely
 * not change during the application lifecycle.
 */
public final class Property {
	private final LoggerApp logger = new LoggerApp(getClass().getName());
	private Map<String, Integer> citiesMap;
	private Map<String, Integer> companiesMap;

	/**
	 * Loads from the database all cities and companies.
	 */
	public void initCitiesAndCompanies() {
		initCities();
		initCompanies();
	}

	/**
	 * Loads from the database all cities.
	 */
	public void initCities() {
		try {
			ResultSet rs = TableQuery.execute("select * from cities");

			citiesMap = new HashMap<>();
			if (rs == null)
				return;
			do {
				citiesMap.put(rs.getString("city"), Integer.parseInt(rs.getString("id_city")));
			} while (rs.next());
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * Loads from the database all companies.
	 */
	public void initCompanies() {
		try {
			Connection con = Create.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from companies");

			companiesMap = new HashMap<>();

			while (rs.next())
				companiesMap.put(rs.getString(2), Integer.parseInt(rs.getString(1)));
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * @return map of all cities and their ids.
	 */
	public Map<String, Integer> getCities() {
		return citiesMap;
	}

	/**
	 * @return map of all companies and their ids.
	 */
	public Map<String, Integer> getCompanies() {
		return companiesMap;
	}
}
