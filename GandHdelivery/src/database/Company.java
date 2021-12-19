package database;

public class Company {
	private int id;
	private String name;
	
	public Company(int id, String companyName) {
		this.id = id;
		this.name = companyName;
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
	
	
}
