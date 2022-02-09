package bg.tu_varna.sit.group17.database.property;

public final class Statuses {
	private int id;
	private String name;

	public Statuses() {
		id = 0;
		 name = "";
	}
	
	public Statuses(int id, String name) {
		this.id = id;
		this.name = name;
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
