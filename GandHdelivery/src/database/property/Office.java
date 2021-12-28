package database.property;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import database.TableQuery;
import database.users.Courier;

public class Office {
	private int id_office, id_company, id_city;
	private String address;
	public LinkedList<Courier> couriers;
	
	public Office() {
		this.id_office = 0;
		this.id_company = 0;
		this.id_city = 0;
		this.address = "";
		this.couriers = new LinkedList<Courier>();
	}
	
	public Office(int id_office, int id_company, int id_city, String address) {
		this.id_office = id_office;
		this.id_company = id_company;
		this.id_city = id_city;
		this.address = address;
		this.couriers = new LinkedList<Courier>();
	}
	
	public int getId_office() {
		return id_office;
	}

	public void setId_office(int id_office) {
		this.id_office = id_office;
	}

	public int getId_company() {
		return id_company;
	}

	public void setId_company(int id_company) {
		this.id_company = id_company;
	}

	public int getId_city() {
		return id_city;
	}

	public void setId_city(int id_city) {
		this.id_city = id_city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCity() throws SQLException {
		return TableQuery.cityIdToName(this.id_city);
	}
	
	public String getFullAddress() throws SQLException {
		return this.getCity() + " " + this.address;
	}
	
	public void setCouriers(ResultSet rs) throws SQLException {
		this.couriers.clear();
		Courier courier;
		do {
			courier = new Courier(rs.getInt("id_courier"), rs.getString("name"), rs.getString("phone"), rs.getString("password"), this.id_office);
			//if(office.getId_office() == 0) return;
			couriers.add(courier);
		}while(rs.next());
	}
}
