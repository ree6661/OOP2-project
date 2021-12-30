package bg.tu_varna.sit.group17.database.queries;

public class Query3 implements Query {
	private int company, orders, offices, couriers, admins;
	
	public Query3(int company, int orders, int offices, int couriers, int admins) {
		super();
		this.company = company;
		this.orders = orders;
		this.offices = offices;
		this.couriers = couriers;
		this.admins = admins;
	}

	public int getOrders() {
		return orders;
	}

	public void setOrders(int orders) {
		this.orders = orders;
	}

	public int getOffices() {
		return offices;
	}

	public void setOffices(int offices) {
		this.offices = offices;
	}

	public int getCouriers() {
		return couriers;
	}

	public void setCouriers(int couriers) {
		this.couriers = couriers;
	}

	public int getAdmins() {
		return admins;
	}

	public void setAdmins(int admins) {
		this.admins = admins;
	}

	public int getCompany() {
		return company;
	}

	public void setCompany(int company) {
		this.company = company;
	}
}
