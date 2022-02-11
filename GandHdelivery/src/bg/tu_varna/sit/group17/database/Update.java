package bg.tu_varna.sit.group17.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class is used for updating database records.
 */
public final class Update {
	private Update() {
		// utility
	}

	/**
	 * Changes an exsitign courier.
	 * 
	 * @param id_courier courier's id number.
	 * @param name       courier's new name.
	 * @param phone      courier's new phone number.
	 * @param password   courier's new accounr passoword.
	 * @throws SQLException if a problem with the database occurs.
	 */
	public static void courier(int id_courier, String name, String phone, String password) throws SQLException {
		Connection conn = Create.getConnection();

		final String sql = "update couriers set name=?, phone=?, password=? where id_courier=?";

		PreparedStatement st = conn.prepareStatement(sql);
		st.setString(1, name);
		st.setString(2, phone);
		st.setString(3, password);
		st.setInt(4, id_courier);

		st.executeUpdate();
		st.close();
	}

	/**
	 * Changes an existing company.
	 * 
	 * @param id_company conmany's id number.
	 * @param company    company's new name.
	 * @throws SQLException if a problem with the database occurs.
	 */
	public static void company(int id_company, String company) throws SQLException {
		Connection conn = Create.getConnection();

		final String sql = "update companies set company=? where id_company=?";

		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, company);
		ps.setInt(2, id_company);

		ps.executeUpdate();
		ps.close();
	}

	/**
	 * Changes an existing company's price list.
	 * 
	 * @param id_company id of the company's price list.
	 * @param company    name of the company.
	 * @param plik       company's new price for an envelope.
	 * @param kolet      company's new price for a package.
	 * @param paket      company's new price for a big package.
	 * @param tovar      company's new price for a cargo.
	 * @throws SQLException if a problem with the database occurs.
	 */
	public static void companyCategory(int id_company, String company, double plik, double kolet, double paket,
			double tovar) throws SQLException {
		Connection conn = Create.getConnection();

		String sql = "update companies set company=? where id_company=?";

		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, company);
		ps.setInt(2, id_company);

		ps.executeUpdate();

		sql = "update price_list set price=? where id_company=? and id_category=?";
		double[] categories = { plik, kolet, paket, tovar };
		for (int i = 1; i < 5; ++i) {
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, categories[i - 1]);
			ps.setInt(2, id_company);
			ps.setInt(3, i);
			ps.executeUpdate();
		}

		ps.close();
	}

	/**
	 * Changes an existing office.
	 * 
	 * @param id_office office's id number.
	 * @param id_city   city's id number in which the office is.
	 * @param address   address of the office.
	 * @throws SQLException if a problem with the database occurs.
	 */
	public static void office(int id_office, int id_city, String address) throws SQLException {
		Connection conn = Create.getConnection();

		final String sql = "update office set id_city=?, address=? where id_office=?";

		PreparedStatement st = conn.prepareStatement(sql);
		st.setInt(1, id_city);
		st.setString(2, address);
		st.setInt(3, id_office);

		st.executeUpdate();
		st.close();
	}

	/**
	 * Changes an oreder's status.
	 * 
	 * @param id_order  id of an order.
	 * @param id_status id of the new order's status.
	 * @throws SQLException if a problem with the database occurs.
	 */
	public static void changeOrderStatus(int id_order, int id_status) throws SQLException {
		Connection conn = Create.getConnection();

		String sql = "update orders set id_status=? where id_order=?";

		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, id_status);
		ps.setInt(2, id_order);

		ps.executeUpdate();

		ps.close();
	}
}