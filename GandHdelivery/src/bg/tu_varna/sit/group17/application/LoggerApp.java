package bg.tu_varna.sit.group17.application;

import org.apache.log4j.PropertyConfigurator;

/**
 * Class for logging information about the application usage and errors.
 */
public final class LoggerApp {

	private String className;

	/**
	 * @param className class which will be logged.
	 */
	public LoggerApp(String className) {
		this.className = className;
		PropertyConfigurator.configure(getClass().getResource("log4j.properties.txt"));
	}

	/**
	 * @param info displays information text in the log file.
	 */
	public void info(String info) {
		org.apache.log4j.Logger.getLogger(className).info(info);
	}

	/**
	 * @param error displays problem/error information in the log file.
	 */
	public void error(String error) {
		org.apache.log4j.Logger.getLogger(className).error(error);
	}
}
