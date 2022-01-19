package bg.tu_varna.sit.group17.database.users;

import bg.tu_varna.sit.group17.application.FormName;

public enum User {
	Admin("admins", FormName.firma), Courier("couriers", FormName.pratkaRegister), Customer("customers", FormName.home), Guest("guests", FormName.login);
	
	private String tableName;
	private FormName formName;
	User(String tableName, FormName formName) {
		this.tableName = tableName;
		this.formName = formName;
	}
	public String getTableName() { return tableName; }
	
	public FormName getFormName() { return formName; }
}
