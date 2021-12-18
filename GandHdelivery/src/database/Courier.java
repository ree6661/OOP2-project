package database;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Courier extends Consumer {
	
	private int id_office;
	
	public Courier(int id, String name, String phone, String password, int id_office) {
		super(id, name, phone, password);
		this.setId_office(id_office);
	}
	
	public static Courier create(ResultSet rs) throws SQLException {
		Consumer c = Consumer.create(rs);
		
		int id_office = 0;
		
		try {
			id_office = rs.getInt(5);
					
		} catch(SQLException e) {
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
