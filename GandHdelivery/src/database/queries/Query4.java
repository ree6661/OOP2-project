package database.queries;

public class Query4 implements Query {
	private int id, orders;
	private String name, office;
	
	public Query4(int id, int orders, String name, String office) {
		super();
		this.id = id;
		this.orders = orders;
		this.name = name;
		this.office = office;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrders() {
		return orders;
	}
	public void setOrders(int orders) {
		this.orders = orders;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		this.office = office;
	}
}
