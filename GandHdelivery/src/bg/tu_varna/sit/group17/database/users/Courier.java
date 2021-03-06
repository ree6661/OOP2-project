package bg.tu_varna.sit.group17.database.users;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class represents the courier from the database.
 */
public final class Courier extends Consumer {

	private int id_office;

	public Courier() {
		super();
		this.id_office = 0;
	}

	public Courier(int id, String name, String phone, String password, int id_office) {
		super(id, name, phone, password, User.Courier);
		this.setId_office(id_office);
	}

	public String toString() {
		return "id: " + id + " name: " + name + " phone: " + phone;
	}

	public static Courier create(ResultSet rs) throws SQLException {
		Consumer c = Consumer.create(rs);

		int id_office = 0;

		try {
			id_office = rs.getInt(5);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return new Courier(c.getId(), c.getName(), c.getPhone(), c.getPassword(), id_office);
	}

	public int getId_office() {
		return id_office;
	}

	public void setId_office(int id_office) {
		this.id_office = id_office;
	}
}
