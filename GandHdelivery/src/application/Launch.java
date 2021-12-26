package application;

import java.io.IOException;
import java.sql.SQLException;

import controllers.PratkaRegisterController;
import database.users.Courier;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launch extends Application {
	
	private Scene loginScene, registerScene, homeScene, pratkaScene, firmaScene;
	private Stage stage;
	private Parent login, register, home, pratka, firma;
	
	public static Launch launch;
	
	@Override
	public void start(Stage stage) {
		this.stage = stage;
		Launch.launch = this;
		try {
			Property.initAll();
			PratkaRegisterController.courier = new Courier();
			PratkaRegisterController.courier.setId(1);
			pratkaForm();
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
	
	public void homeForm() throws SQLException, IOException {
		this.stage.hide();
		
		this.home = FXMLLoader.load(getClass().getResource("../fxml/home.fxml"));
		this.home.getStylesheets().add(getClass().getResource("../css/home.css").toExternalForm());
//		this.home.getStylesheets().add(getClass().getResource("../css/firma.css").toExternalForm());
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
//		this.firma.getStylesheets().add(getClass().getResource("../css/firma.css").toExternalForm());
//		this.firma.getStylesheets().add(getClass().getResource("../css/pratkaRegister.css").toExternalForm());
		
		this.stage.setScene(this.firmaScene);
		this.stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}