package bg.tu_varna.sit.group17.database.property;

/**
 * This class represents the cities from the database.
 */
public final class Cities {
	private int id;
	private String name;

	public Cities() {
		id = 0;
		name = "";
	}

	public Cities(int id, String name) {
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
