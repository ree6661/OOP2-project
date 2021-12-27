package database;

import java.sql.Date;

public class Order {
	private int id_order;
	private String category, office_sender, office_recipient, customer_sender, customer_recipient, courier, status,
			address;
	private boolean fragile, paid, cash_on_delivery, delivery_to_address;
	private Date acceptance_by_sender, customer_delivery;

	public Order(int id_order, String category, String office_sender, String office_recipient, String customer_sender,
			String customer_recipient, String courier, String status, String address, boolean fragile, boolean paid,
			boolean cash_on_delivery, boolean delivery_to_address, Date acceptance_by_sender, Date customer_delivery) {
		super();
		this.id_order = id_order;
		this.category = category;
		this.office_sender = office_sender;
		this.office_recipient = office_recipient;
		this.customer_sender = customer_sender;
		this.customer_recipient = customer_recipient;
		this.courier = courier;
		this.status = status;
		this.address = address;
		this.fragile = fragile;
		this.paid = paid;
		this.cash_on_delivery = cash_on_delivery;
		this.delivery_to_address = delivery_to_address;
		this.acceptance_by_sender = acceptance_by_sender;
		this.customer_delivery = customer_delivery;
	}

	public int getId_order() {
		return id_order;
	}

	public void setId_order(int id_order) {
		this.id_order = id_order;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getOffice_sender() {
		return office_sender;
	}

	public void setOffice_sender(String office_sender) {
		this.office_sender = office_sender;
	}

	public String getOffice_recipient() {
		return office_recipient;
	}

	public void setOffice_recipient(String office_recipient) {
		this.office_recipient = office_recipient;
	}

	public String getCustomer_sender() {
		return customer_sender;
	}

	public void setCustomer_sender(String customer_sender) {
		this.customer_sender = customer_sender;
	}

	public String getCustomer_recipient() {
		return customer_recipient;
	}

	public void setCustomer_recipient(String customer_recipient) {
		this.customer_recipient = customer_recipient;
	}

	public String getCourier() {
		return courier;
	}

	public void setCourier(String courier) {
		this.courier = courier;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isFragile() {
		return fragile;
	}

	public void setFragile(boolean fragile) {
		this.fragile = fragile;
	}

	public boolean isPaid() {
		return paid;
	}

	public void setPaid(boolean paid) {
		this.paid = paid;
	}

	public boolean isCash_on_delivery() {
		return cash_on_delivery;
	}

	public void setCash_on_delivery(boolean cash_on_delivery) {
		this.cash_on_delivery = cash_on_delivery;
	}

	public boolean isDelivery_to_address() {
		return delivery_to_address;
	}

	public void setDelivery_to_address(boolean delivery_to_address) {
		this.delivery_to_address = delivery_to_address;
	}

	public Date getAcceptance_by_sender() {
		return acceptance_by_sender;
	}

	public void setAcceptance_by_sender(Date acceptance_by_sender) {
		this.acceptance_by_sender = acceptance_by_sender;
	}

	public Date getCustomer_delivery() {
		return customer_delivery;
	}

	public void setCustomer_delivery(Date customer_delivery) {
		this.customer_delivery = customer_delivery;
	}
}
