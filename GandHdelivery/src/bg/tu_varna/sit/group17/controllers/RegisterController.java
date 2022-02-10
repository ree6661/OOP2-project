package bg.tu_varna.sit.group17.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import bg.tu_varna.sit.group17.application.FormName;
import bg.tu_varna.sit.group17.application.Load;
import bg.tu_varna.sit.group17.application.LoggerApp;
import bg.tu_varna.sit.group17.database.Register;
import bg.tu_varna.sit.group17.database.users.Consumer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * Controller of the registration form used for registering customers from courier.
 */
public final class RegisterController extends ControllerParent {
	private final LoggerApp logger = new LoggerApp(getClass().getName());
	private Register register;
	@FXML
	private MenuButton userName;
	@FXML
	private TextField name, phone, address;
	@FXML
	private ImageView avatar;
	@FXML
	private Button notificationBell;
	@FXML
	private ComboBox<String> cBox0;
	@FXML
	private PasswordField password, repeatPassword;
	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;

	@Override
	public void initData(Load load, Consumer consumer) {
		logger.info("In register form");
		this.load = load;
		this.consumer = consumer;
		this.register = new Register(load, consumer, notificationBell);
		this.avatar.setImage(load.getAvatar().get());
		this.userName.setText(consumer.getName());
		this.register.prepareForm();
		
		cBox0.getItems().addAll(load.getProperty().getCities().keySet());
	}

	@FXML
	private void changeAvatar() {
		this.avatar.setImage(load.getAvatar().next());
	}

	@FXML
	private void logOut() {
		load.form(FormName.login, consumer);
	}

	@FXML
	private void registerPratka() throws SQLException, IOException {
		load.form(FormName.pratkaRegister, consumer);
	}

	@FXML
	private void queries() throws SQLException, IOException {
		load.form(FormName.home, consumer);
	}

	@FXML
	private void firma() throws SQLException, IOException {
		load.form(FormName.firma, consumer);
	}

	@FXML
	private void notificationBellClick() {
		load.getNotification().apply(this.notificationBell);
	}

	@FXML
	private void register(ActionEvent e) {
		register.addCustomer(name.getText(), phone.getText(), cBox0.getValue(), address.getText(), password.getText(),
				repeatPassword.getText());
	}
}
