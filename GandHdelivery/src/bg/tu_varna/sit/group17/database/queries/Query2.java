package bg.tu_varna.sit.group17.database.queries;

import java.sql.Date;

public class Query2 implements Query {
	private int number;
	private String name, phone, status;
	private Date from, to;
	
	public Query2(int number, String name, String phone, String status, Date from, Date to) {
		super();
		this.number = number;
		this.name = name;
		this.phone = phone;
		this.status = status;
		this.from = from;
		this.to = to;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getFrom() {
		return from;
	}
	public void setFrom(Date from) {
		this.from = from;
	}
	public Date getTo() {
		return to;
	}
	public void setTo(Date to) {
		this.to = to;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
}
