package bg.tu_varna.sit.group17.application;

/**
 * Stores all form names, which will be used in the application.
 */
public enum FormName {
	/**
	 * represents the login form for logging into existing user account.
	 */
	login,
	/**
	 * represents the register form for registering new customers.
	 */
	register,
	/**
	 * represents the home form the form every user can use.
	 */
	home,
	/**
	 * represents the package sending form for registering new package to be send to a customer.
	 */
	pratkaRegister,
	/**
	 * represents the company form which only the administrator can access.
	 */
	firma;
}
