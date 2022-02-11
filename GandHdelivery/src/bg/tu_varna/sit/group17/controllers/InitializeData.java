package bg.tu_varna.sit.group17.controllers;

import bg.tu_varna.sit.group17.application.Load;
import bg.tu_varna.sit.group17.database.users.Consumer;

/**
 * Initialization of the controllers.
 */
public interface InitializeData {
	/**
	 * Used for initializing data before the usage of a controller.
	 * 
	 * @param load     the form loader which will be initialized.
	 * @param consumer the consumer of the current login session.
	 */
	void initData(Load load, Consumer consumer);
}
