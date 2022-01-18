package bg.tu_varna.sit.group17.application;

import org.apache.log4j.PropertyConfigurator;

public final class LoggerApp {
	
	private String className;
	
	public LoggerApp(String className) {
		super();
		this.className = className;
		PropertyConfigurator.configure(getClass().getResource("log4j.properties.txt"));
	}
	
	public void info(String info) {
		org.apache.log4j.Logger.getLogger(className).info(info);
	}
	public void error(String error) {
		org.apache.log4j.Logger.getLogger(className).error(error);
	}
}
