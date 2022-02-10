package bg.tu_varna.sit.group17.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is used for adding records to the database.
 */
public final class Add {

	private Add() {
		// utility
	}

	/**
	 * Adds prices for company's package types.
	 * 
	 * @param companyName the name of the company.
	 * @param plik        the price for an envelope.
	 * @param kolet       - the price for a package.
	 * @param paket       - the price for a big package.
	 * @param tovar       - the price for a cargo.
	 * @throws SQLException if a problem with the database occurs.
	 */
	public static void companyPrice(String companyName, double plik, double kolet, double paket, double tovar)
			throws SQLException {
		Connection conn = Create.getConnection();

		String sql = "insert into companies(company) values(?)";

		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, companyName);

		ps.execute();

		sql = "SELECT id_company FROM companies ORDER BY id_company DESC LIMIT 1";
		ResultSet rs = TableQuery.execute(sql);
		if (rs == null) {
			ps.close();
			return;
		}
		double[] categories = { plik, kolet, paket, tovar };
		int id_company = rs.getInt("id_company");
		sql = "insert into price_list(id_company, id_category, price) values(?, ?, ?)";

		for (int i = 1; i < 5; ++i) {
			ps = conn.prepareStatement(sql);

			ps.setInt(1, id_company);
			ps.setInt(2, i);
			ps.setDouble(3, categories[i - 1]);

			ps.execute();
		}

		ps.close();
	}

	/**
	 * Adds new customer to the database.
	 * 
	 * @param name     the name of the customer.
	 * @param phone    the customer's phone number.
	 * @param city     - the customer's current city.
	 * @param address  - the customer's current addres.
	 * @param password the password for customer's account.
	 * @throws SQLException if a problem with the database occurs.
	 */
	public static void customer(String name, String phone, int city, String address, String password)
			throws SQLException {
		Connection conn = Create.getConnection();

		final String sql = "insert into customers(name, phone, " + "id_city, address, password)"
				+ " values (?, ?, ?, ?, ?)";

		PreparedStatement st = conn.prepareStatement(sql);
		st.setString(1, name);
		st.setString(2, phone);
		st.setInt(3, city);
		st.setString(4, address);
		st.setString(5, password);

		st.execute();
		st.close();
	}

	/**
	 * Adds new company office to the database.
	 * 
	 * @param id_company id number of the company in which the office to be added.
	 * @param id_city    id number of the city in which the office is placed.
	 * @param address    the new office's addres.
	 * @throws SQLException if a problem with the database occurs.
	 */
	public static void office(int id_company, int id_city, String address) throws SQLException {
		Connection conn = Create.getConnection();

		final String sql = "insert into office(id_company," + " id_city, address) values(?, ?, ?)";

		PreparedStatement st = conn.prepareStatement(sql);
		st.setInt(1, id_company);
		st.setInt(2, id_city);
		st.setString(3, address);

		st.execute();
		st.close();
	}

	/**
	 * Adds new courier to the database.
	 * 
	 * @param name      name of the courier.
	 * @param phone     phone number of the courier.
	 * @param password  password for courier's account.
	 * @param id_office id number of the courier's working office.
	 * @throws SQLException if a problem with the database occurs.
	 */
	public static void courier(String name, String phone, String password, int id_office) throws SQLException {
		Connection conn = Create.getConnection();

		final String sql = "insert into couriers(name, phone," + " password, id_office) values(?, ?, ?, ?)";

		PreparedStatement st = conn.prepareStatement(sql);
		st.setString(1, name);
		st.setString(2, phone);
		st.setString(3, password);
		st.setInt(4, id_office);

		st.execute();
		st.close();
	}

	/**
	 * Adds new order to the database.
	 * 
	 * @param id_category           id number of the order's type package.
	 * @param id_office_sender      id number of the order's sender office.
	 * @param id_office_recipient   id number of the order's recipient office.
	 * @param id_customer_sender    id number of the order's customer sender.
	 * @param id_customer_recipient id number of the orders's customer recipient.
	 * @param id_courier            id number of the courider making the order.
	 * @param id_status             id number of the current order status.
	 * @param fragile               inidicates if the package contains fragile
	 *                              items.
	 * @param paid                  indicates if the order is paid by the customer
	 *                              sender.
	 * @param cash_on_delivery      indicates if the order if the customer recipient
	 *                              will pay for the delivery.
	 * @param delivery_to_address   indicates if the order is going to be delivered
	 *                              to specific addres.
	 * @param address               specific addres to which the order is going ot
	 *                              be delivered.
	 * @param acceptance_by_sender  the date of making the order.
	 * @param customer_delivery     the date of order's delivery.
	 * @throws SQLException if a problem with the database occurs.
	 */
	public static void order(int id_category, int id_office_sender, int id_office_recipient, int id_customer_sender,
			int id_customer_recipient, int id_courier, int id_status, boolean fragile, boolean paid,
			double cash_on_delivery, boolean delivery_to_address, String address, Date acceptance_by_sender,
			Date customer_delivery) throws SQLException {

		Connection conn = Create.getConnection();

		final String sql = "insert into orders(id_category," + " id_office_sender, id_office_recipient,"
				+ " id_customer_sender, id_customer_recipient," + " id_courier, id_status, fragile,"
				+ " paid, cash_on_delivery, delivery_to_address, address,"
				+ " acceptance_by_sender, customer_delivery) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement st = conn.prepareStatement(sql);
		st.setInt(1, id_category);
		st.setInt(2, id_office_sender);
		st.setInt(3, id_office_recipient);
		st.setInt(4, id_customer_sender);
		st.setInt(5, id_customer_recipient);
		st.setInt(6, id_courier);
		st.setInt(7, id_status);
		st.setBoolean(8, fragile);
		st.setBoolean(9, paid);
		st.setDouble(10, cash_on_delivery);
		st.setBoolean(11, delivery_to_address);
		st.setString(12, address);
		st.setDate(13, acceptance_by_sender);
		st.setDate(14, customer_delivery);

		st.execute();
		st.close();
	}
}
