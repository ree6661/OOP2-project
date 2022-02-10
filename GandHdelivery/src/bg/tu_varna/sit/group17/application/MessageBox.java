package bg.tu_varna.sit.group17.application;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Class for displaying message windows.
 */
public final class MessageBox {
	private LoggerApp logger;

	/**
	 * @param logger the logger which will log information about the message.
	 */
	public MessageBox(LoggerApp logger) {
		this.logger = logger;
	}

	/**
	 * @param error   short text about the error.
	 * @param content detail information about the error.
	 */
	public void alert(String error, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Съобщение");
		alert.setHeaderText(error);
		alert.setContentText(content);
		alert.showAndWait();
		logger.info(content);
	}

	/**
	 * @param error short text about the error.
	 */
	public void alert(String error) {
		Alert alert = new Alert(AlertType.INFORMATION);

		alert.setTitle("Съобщение");
		alert.setHeaderText(error);
		alert.showAndWait();
		logger.info(error);
	}
}
