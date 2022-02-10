package bg.tu_varna.sit.group17.controllers;

import bg.tu_varna.sit.group17.application.Load;
import bg.tu_varna.sit.group17.database.users.Consumer;

/**
 * Parent class of all controllers.
 */
public abstract class ControllerParent implements InitializeData {
	/**
	 * The consumer for the current login session.
	 */
	protected Consumer consumer;
	/**
	 * Loader of forms passed by the controllers.
	 */
	protected Load load;
	
	/**
	 * @return the current user object.
	 */
	public Consumer getConsumer() {
		return consumer;
	}
	/**
	 * @return the loader of the forms.
	 */
	public Load getLoad() {
		return load;
	}
}
