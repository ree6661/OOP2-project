package bg.tu_varna.sit.group17.database.property;

public final class PriceList {
	private int id_company;
	private int id_category;
	private double price;

	public PriceList() {
		id_company = 0;
		id_category = 0;
		price = 0;
	}
	
	public PriceList(int id_company, int id_category, double price) {
		this.id_company = id_company;
		this.id_category = id_category;
		this.price = price;
	}

	public int getId_company() {
		return id_company;
	}

	public void setId_company(int id_company) {
		this.id_company = id_company;
	}

	public int getId_category() {
		return id_category;
	}

	public void setId_category(int id_category) {
		this.id_category = id_category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
