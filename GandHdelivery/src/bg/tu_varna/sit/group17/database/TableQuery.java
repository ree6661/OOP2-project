package bg.tu_varna.sit.group17.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import bg.tu_varna.sit.group17.application.Notification;
import bg.tu_varna.sit.group17.database.property.Company;
import bg.tu_varna.sit.group17.database.property.Office;

/**
 * This class is used for extracting information from the application's
 * database.
 */
public final class TableQuery {
	private static final String[] users = { "customers", "couriers", "admins" };
	private Connection DB;
	private String table;

	/**
	 * Used for selecting information only from a specific database table.
	 * 
	 * @param tableName the name of a database table.
	 */
	public TableQuery(String tableName) {
		try {
			DB = Create.getConnection();
			table = tableName;
			DatabaseMetaData md = DB.getMetaData();
			ResultSet rs = md.getTables(null, null, table, null);
			if (!rs.next())
				throw new IllegalArgumentException("Table " + tableName + " not exists");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks if table's column contains a specific field.
	 * 
	 * @param column   column name in which the field to be searched for.
	 * @param contains the field which will be searched in the table's column.
	 * @return if the field exists in the current table's column.
	 */
	public boolean contains(String column, String contains) {
		try {
			String sql = "select " + column + " from " + table + " where " + column + " = '" + contains + "'";
			PreparedStatement ps = DB.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			return rs.next();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Executes an selective query to the database.
	 * 
	 * @param sql select query to be executed.
	 * @return a ResultSet with information about the select fields.
	 * @throws SQLException if a problem with the database occurs.
	 */
	public static ResultSet execute(String sql) throws SQLException {
		Connection DB = Create.getConnection();
		PreparedStatement ps = DB.prepareStatement(sql);

		ResultSet rs = ps.executeQuery();

		if (!rs.next())
			return null;

		return rs;
	}

	/**
	 * Converts city id to it's name by searching it from the database.
	 * 
	 * @param id city's id number.
	 * @return name of a city found by it's id number.
	 * @throws SQLException if a problem with the database occurs.
	 */
	public static String cityIdToName(int id) throws SQLException {
		final String sql = "select city from cities where id_city='" + id + "'";
		ResultSet officeCity = TableQuery.execute(sql);

		return officeCity.getString(1);
	}

	/**
	 * Checks if a phone number already exists in the database.
	 * 
	 * @param phone phone number to be searched for.
	 * @return if phone number exists in the database.
	 * @throws SQLException if a problem with the database occurs.
	 */
	public static boolean phoneExists(String phone) throws SQLException {

		ResultSet record = null;
		for (int i = 0; i < TableQuery.users.length; ++i) {
			record = TableQuery.getRecordFromTable("phone", phone, users[i]);
			if (record != null)
				return true;
		}
		return false;
	}

	/**
	 * Retrieves a record from table.
	 * 
	 * @param <Value>         the type of the record's value.
	 * @param valueColumnName value's column name.
	 * @param value           value to be searched for.
	 * @param table           table in which the value to be searched.
	 * @return ResultSet containing an record/s responding to the condition.
	 * @throws SQLException if a problem with the database occurs.
	 */
	public static <Value> ResultSet getRecordFromTable(String valueColumnName, Value value, String table)
			throws SQLException {
		Connection DB = Create.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		String sql = "";

		sql = "select * from " + table + " where " + valueColumnName + " = '" + value + "'";
		ps = DB.prepareStatement(sql);
		rs = ps.executeQuery();
		if (!rs.next())
			return null;

		return rs;
	}

	/**
	 * Get's company's id number from company name.
	 * 
	 * @param companyName company name.
	 * @return id of a company.
	 * @throws SQLException if a problem with the database occurs.
	 */
	public static int getCompanyId(String companyName) throws SQLException {
		String sql = "select * from companies where company='" + companyName + "'";
		ResultSet rs = TableQuery.execute(sql);

		if (rs == null)
			return 0;
		return rs.getInt("id_company");
	}

	/**
	 * @return list of all companies form the database.
	 * @throws SQLException if a problem with the database occurs.
	 */
	public static List<Company> allCompanies() throws SQLException {
		LinkedList<Company> companies = new LinkedList<>();

		String sql = "select * from companies";

		ResultSet rs = TableQuery.execute(sql);

		do {
			companies.add(new Company(rs.getInt("id_company"), rs.getString("company")));
		} while (rs.next());

		for (int i = 0; i < companies.size(); ++i) {

			sql = "select * from office where id_company='" + companies.get(i).getId() + "'";
			rs = TableQuery.execute(sql);
			if (rs == null)
				continue;
			do {
				companies.get(i).getOffices().add(new Office(rs.getInt("id_office"), rs.getInt("id_company"),
						rs.getInt("id_city"), rs.getString("address")));
			} while (rs.next());
		}

		return companies;
	}

	/**
	 * Get's category's id number from category name.
	 * 
	 * @param category category name.
	 * @return id number of the category.
	 * @throws SQLException if a problem with the database occurs.
	 */
	public static int getCategoryId(String category) throws SQLException {
		String sql = "select id_category from categories where category='" + category + "'";

		ResultSet rs = TableQuery.execute(sql);

		if (rs == null)
			return 0;

		return rs.getInt("id_category");
	}

	/**
	 * Get's office's id number from office name.
	 * 
	 * @param fullAddress office's city name and address.
	 * @return id number of the office.
	 * @throws SQLException if a problem with the database occurs.
	 */
	public static int getOfficeId(String fullAddress) throws SQLException {
		String sql = "select * from office";

		ResultSet rs = TableQuery.execute(sql);

		if (rs == null)
			return 0;
		Company c = new Company();
		c.setOffices(rs);
		for (int i = 0; i < c.getOffices().size(); ++i) {
			Office o = c.getOffices().get(i);
			if (o.getFullAddress().equals(fullAddress))
				return o.getId_office();
		}
		return 0;
	}

	/**
	 * Get's customer's id number from customer phone number.
	 * 
	 * @param phone customer's phone number.
	 * @return id of the customer.
	 * @throws SQLException if a problem with the database occurs.
	 */
	public static int getCustomerId(String phone) throws SQLException {
		String sql = "select id_customer from customers where phone='" + phone + "'";

		ResultSet rs = TableQuery.execute(sql);

		if (rs == null)
			return 0;

		return rs.getInt("id_customer");
	}

	/**
	 * Get's category's name from it's id number.
	 * 
	 * @param id_category category's id number.
	 * @return category's name.
	 * @throws SQLException if a problem with the database occurs.
	 */
	public static String categoryIdToString(int id_category) throws SQLException {
		String sql = "select category from categories where id_category='" + id_category + "'";

		ResultSet rs = TableQuery.execute(sql);

		if (rs == null)
			return null;

		return rs.getString("category");
	}

	/**
	 * Get's office name from it's id number.
	 * 
	 * @param id_office id of the office.
	 * @return the name of the office.
	 * @throws SQLException if a problem with the database occurs.
	 */
	public static String getOffice(int id_office) throws SQLException {
		String sql = "select * from office where id_office='" + id_office + "'";
		ResultSet rs = TableQuery.execute(sql);

		if (rs == null)
			return null;

		return TableQuery.cityIdToName(rs.getInt("id_city")) + " " + rs.getString("address");
	}

	/**
	 * Get's customer name from it's id number.
	 * 
	 * @param id_customer customer's id number.
	 * @return customer's name.
	 * @throws SQLException if a problem with the database occurs.
	 */
	public static String getCustomer(int id_customer) throws SQLException {
		String sql = "select name from customers where id_customer='" + id_customer + "'";
		ResultSet rs = TableQuery.execute(sql);

		if (rs == null)
			return null;

		return rs.getString("name");
	}

	/**
	 * Get's courier's name from it's id number.
	 * 
	 * @param id_courier courier's id number.
	 * @return courier's name.
	 * @throws SQLException if a problem with the database occurs.
	 */
	public static String getCourier(int id_courier) throws SQLException {
		String sql = "select name from couriers where id_courier='" + id_courier + "'";
		ResultSet rs = TableQuery.execute(sql);

		if (rs == null)
			return null;

		return rs.getString("name");
	}

	/**
	 * Get's status name from it's id number.
	 * 
	 * @param id_status status's id number.
	 * @return status's name.
	 * @throws SQLException if a problem with the database occurs.
	 */
	public static String getStatus(int id_status) throws SQLException {
		String sql = "select status_name from statuses where id_status='" + id_status + "'";
		ResultSet rs = TableQuery.execute(sql);

		if (rs == null)
			return null;
		return rs.getString("status_name");
	}

	/**
	 * Checks order for update by customer's id number.
	 * 
	 * @param id_customer customer's id number.
	 * @throws SQLException if a problem with the database occurs.
	 */
	public static void checkOrderUpdate(int id_customer) throws SQLException {
		String sql = "select id_order, customer_delivery from orders where id_customer_recipient='" + id_customer
				+ "' and id_status='" + Notification.getStatus(0) + "' and customer_delivery <= CURRENT_DATE()";
		ResultSet rs = TableQuery.execute(sql);

		if (rs == null)
			return;
		do {
			Update.changeOrderStatus(rs.getInt("id_order"), Notification.getStatus(2));

		} while (rs.next());

	}
}