package bg.tu_varna.sit.group17.database.users;

import bg.tu_varna.sit.group17.application.FormName;

/**
 * Stores all users which will be used in the application.
 */
public enum User {
	/**
	 * represents the aministrator and sets company form for default form.
	 */
	Admin("admins", FormName.firma),
	/**
	 * represents the courier and sets package register form for default form.
	 */
	Courier("couriers", FormName.pratkaRegister),
	/**
	 * represents the cusromer and sets home form for default form.
	 */
	Customer("customers", FormName.home),
	/**
	 * represents non logged user and sets login form for default form.
	 */
	Guest("guests", FormName.login);

	private String tableName;
	private FormName formName;

	private User(String tableName, FormName formName) {
		this.tableName = tableName;
		this.formName = formName;
	}

	public String getTableName() {
		return tableName;
	}

	public FormName getFormName() {
		return formName;
	}
}
