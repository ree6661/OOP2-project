package bg.tu_varna.sit.group17.database.property;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class Company {
	private int id;
	private String name;
	public LinkedList<Office> offices;
	
	public Company() {
		id = 0;
		name = "";
		offices = new LinkedList<>();
	}
	
	public Company(int id, String companyName) {
		this.id = id;
		this.name = companyName;
		offices = new LinkedList<Office>();
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
	
	public void setOffices(ResultSet rs) throws SQLException {
		this.offices.clear();
		Office office;
		do {
			office = new Office(rs.getInt("id_office"), this.id, rs.getInt("id_city"), rs.getString("address"));
			//if(office.getId_office() == 0) return;
			offices.add(office);
		}while(rs.next());
	}
	
}
