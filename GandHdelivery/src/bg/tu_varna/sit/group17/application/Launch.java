package bg.tu_varna.sit.group17.application;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Launch extends Application {
	
	private Scene loginScene, registerScene, homeScene, pratkaScene, firmaScene;
	private Stage stage;
	private Parent login, register, home, pratka, firma;
	
	public static Launch launch;
	static final Logger logger = Logger.getLogger(Launch.class.getName());
	@Override
	public void start(Stage stage) {
		stage.setResizable(false);
		PropertyConfigurator.configure(getClass().getResource("log4j.properties.txt"));
		logger.info("Starting application");
		
		this.stage = stage;
		Launch.launch = this;
		try {
			Property.initAll();
			//registerForm();
			loginForm();
			//homeFormCustomer();
//			PratkaRegisterController.courier = new Courier();
	//		PratkaRegisterController.courier.setId(1);
			//pratkaForm();
			//firmaForm();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void registerForm() throws SQLException, IOException {
		this.stage.hide();
		
		this.register = FXMLLoader.load(getClass().getResource("../fxml/register.fxml"));
		this.registerScene = new Scene(this.register);
		this.registerScene.getStylesheets().add(getClass().getResource("../css/login-register.css").toExternalForm());
		
		this.stage.setScene(this.registerScene);
		this.stage.show();
	}
	
	public void loginForm() throws SQLException, IOException {
		this.stage.hide();
		
		this.login = FXMLLoader.load(getClass().getResource("../fxml/login.fxml"));
		this.loginScene = new Scene(this.login);
		this.loginScene.getStylesheets().add(getClass().getResource("../css/login-register.css").toExternalForm());
		
		this.stage.setScene(this.loginScene);
		this.stage.show();
	}
	
	public void homeFormAdmin() throws SQLException, IOException {
		this.stage.hide();
		
		this.home = FXMLLoader.load(getClass().getResource("../fxml/home.fxml"));
		this.home.getStylesheets().add(getClass().getResource("../css/home.css").toExternalForm());
		this.home.getStylesheets().add(getClass().getResource("../css/hide-pratka.css").toExternalForm());
//		this.home.getStylesheets().add(getClass().getResource("../css/hide-spravki.css").toExternalForm());
		this.homeScene = new Scene(this.home);
		
		this.stage.setScene(this.homeScene);
		this.stage.show();
	}
	public void homeFormCourier() throws SQLException, IOException {
		this.stage.hide();
		
		this.home = FXMLLoader.load(getClass().getResource("../fxml/home.fxml"));
		this.home.getStylesheets().add(getClass().getResource("../css/home.css").toExternalForm());
		this.home.getStylesheets().add(getClass().getResource("../css/hide-firma.css").toExternalForm());
		this.homeScene = new Scene(this.home);
		
		this.stage.setScene(this.homeScene);
		this.stage.show();
	}
	public void homeFormCustomer() throws SQLException, IOException {
		this.stage.hide();
		
		this.home = FXMLLoader.load(getClass().getResource("../fxml/home.fxml"));
		this.home.getStylesheets().add(getClass().getResource("../css/home.css").toExternalForm());
		this.home.getStylesheets().add(getClass().getResource("../css/hide-pratka.css").toExternalForm());
		this.home.getStylesheets().add(getClass().getResource("../css/hide-firma.css").toExternalForm());
		this.homeScene = new Scene(this.home);
		
		this.stage.setScene(this.homeScene);
		this.stage.show();
	}
	
	public void pratkaForm() throws SQLException, IOException {
		this.stage.hide();
		
		this.pratka = FXMLLoader.load(getClass().getResource("../fxml/pratkaRegister.fxml"));
		this.pratkaScene = new Scene(this.pratka);
		this.pratka.getStylesheets().add(getClass().getResource("../css/home.css").toExternalForm());
		this.pratka.getStylesheets().add(getClass().getResource("../css/pratkaRegister.css").toExternalForm());
		
		this.stage.setScene(this.pratkaScene);
		this.stage.show();
	}
	
	public void firmaForm() throws SQLException, IOException {
		this.stage.hide();
		
		this.firma = FXMLLoader.load(getClass().getResource("../fxml/firma.fxml"));
		this.firmaScene = new Scene(this.firma);
		this.firma.getStylesheets().add(getClass().getResource("../css/home.css").toExternalForm());
		//this.firma.getStylesheets().add(getClass().getResource("../css/spravkiOnHover.css").toExternalForm());
		
		this.firma.getStylesheets().add(getClass().getResource("../css/hide-pratka.css").toExternalForm());
		
		this.stage.setScene(this.firmaScene);
		this.stage.show();
	}
	
	public static void alert(String error, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Error");
		alert.setHeaderText(error);
		alert.setContentText(content);
		alert.showAndWait();
	}
	
	public static void alert(String error) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Error");
		alert.setHeaderText(error);
		alert.showAndWait();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}