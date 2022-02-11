package bg.tu_varna.sit.group17.database.property;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents the company from the database.
 */
public final class Company {
	private int id;
	private String name;
	private List<Office> offices;

	public Company() {
		id = 0;
		name = "";
		offices = new LinkedList<>();
	}

	public Company(int id, String companyName) {
		this.id = id;
		this.name = companyName;
		offices = new LinkedList<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Office> getOffices() {
		return offices;
	}

	public void setOffices(ResultSet rs) throws SQLException {
		this.offices.clear();
		Office office;
		do {
			office = new Office(rs.getInt("id_office"), this.id, rs.getInt("id_city"), rs.getString("address"));
			offices.add(office);
		} while (rs.next());
	}
}
