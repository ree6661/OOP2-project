package bg.tu_varna.sit.group17.application;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public final class MessageBox {
	private LoggerApp logger;
	
	public MessageBox(LoggerApp logger) {
		this.logger = logger;
	}
	
	public void alert(String error, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Съобщение");
		alert.setHeaderText(error);
		alert.setContentText(content);
		alert.showAndWait();
		logger.info(content);
	}
	
	public void alert(String error) {
		Alert alert = new Alert(AlertType.INFORMATION);
		
		alert.setTitle("Съобщение");
		alert.setHeaderText(error);
		alert.showAndWait();
		logger.info(error);
	}
}
