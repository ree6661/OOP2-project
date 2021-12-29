package application;

public class Logger {
	private String className;

	public Logger(String className) {
		super();
		this.className = className;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	public void info(String info) {
		org.apache.log4j.Logger.getLogger(className).info(info);
	}
}
