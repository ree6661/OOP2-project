package bg.tu_varna.sit.group17.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import bg.tu_varna.sit.group17.database.property.Company;
import bg.tu_varna.sit.group17.database.property.Office;
import bg.tu_varna.sit.group17.database.users.Courier;

/**
 * This class is used for deleting records from the database.
 */
public final class Delete {
	private Delete() {
		// utility
	}

	/**
	 * Deletes a company's packages price list.
	 * 
	 * @param company the company whoose price list to be deleted.
	 * @throws SQLException if a problem with the database occurs.
	 */
	public static void companyCategory(Company company) throws SQLException {
		if (company == null) {
			return;
		}
		Connection conn = Create.getConnection();

		if (company.getOffices().size() != 0)
			for (int i = 0; i < company.getOffices().size(); ++i)
				Delete.office(company.getOffices().get(i));

		String sql = "delete from price_list where id_company=?";
		PreparedStatement ps = conn.prepareStatement(sql);

		ps.setInt(1, company.getId());
		ps.execute();

		sql = "DELETE FROM companies WHERE id_company=?";

		ps = conn.prepareStatement(sql);
		ps.setInt(1, company.getId());
		ps.execute();

		ps.close();
	}

	/**
	 * Deletes an office from the database.
	 * 
	 * @param office office to be deleted.
	 * @throws SQLException if a problem with the database occurs.
	 */
	public static void office(Office office) throws SQLException {
		if (office == null) {
			return;
		}
		Connection conn = Create.getConnection();

		if (office.getCouriers().size() != 0)
			for (int i = 0; i < office.getCouriers().size(); ++i)
				Delete.courier(office.getCouriers().get(i));

		final String sql = "DELETE FROM office WHERE id_office=?";

		PreparedStatement st = conn.prepareStatement(sql);
		st.setInt(1, office.getId_office());

		st.execute();
		st.close();
	}

	/**
	 * Deletes courier from the database.
	 * 
	 * @param courier the courier to be deleted.
	 * @throws SQLException if a problem with the database occurs.
	 */
	public static void courier(Courier courier) throws SQLException {
		if (courier == null) {
			return;
		}
		Connection conn = Create.getConnection();

		final String sql = "DELETE FROM couriers WHERE id_courier=?";

		PreparedStatement st = conn.prepareStatement(sql);
		st.setInt(1, courier.getId());

		st.execute();
		st.close();
	}
}
