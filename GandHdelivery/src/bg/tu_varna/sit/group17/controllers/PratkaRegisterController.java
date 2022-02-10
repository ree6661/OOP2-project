package bg.tu_varna.sit.group17.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import bg.tu_varna.sit.group17.application.FormName;
import bg.tu_varna.sit.group17.application.Load;
import bg.tu_varna.sit.group17.application.LoggerApp;
import bg.tu_varna.sit.group17.database.PratkaRegister;
import bg.tu_varna.sit.group17.database.users.Consumer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * Controller of the package register form used for registering packages from and to customers.
 */
public final class PratkaRegisterController extends ControllerParent {

	private final LoggerApp logger = new LoggerApp(getClass().getName());

	private PratkaRegister pratkaRegister;

	@FXML
	private ComboBox<String> category, officeSender, officeReceiver, companySender;
	@FXML
	private CheckBox fragile, sendToAddress, isPaid;
	@FXML
	private TextField phoneSender, phoneReceiver, sendPrice, address;
	@FXML
	private MenuButton userName;
	@FXML
	private ImageView avatar;
	@FXML
	private DatePicker receiveDate, clientReceiveDate;
	@FXML
	private Button notificationBell;
	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;

	@Override
	public void initData(Load load, Consumer consumer) {
		logger.info("In pratka register form");
		logger.info("Logged courier: " + consumer);
		this.load = load;
		this.consumer = consumer;
		this.pratkaRegister = new PratkaRegister(consumer, load, category, officeSender, officeReceiver, companySender, notificationBell, phoneSender, phoneReceiver, sendPrice, address, receiveDate, clientReceiveDate, fragile, sendToAddress, isPaid);

		this.avatar.setImage(load.getAvatar().get());
		this.userName.setText(consumer.getName());
		this.pratkaRegister.prepareForm();
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
	private void queries() {
		load.form(FormName.home, consumer);
	}

	@FXML
	private void klient() {
		load.form(FormName.register, consumer);
	}

	@FXML
	private void notificationBellClick() {
		load.getNotification().apply(this.notificationBell);
	}

	@FXML
	private void registerOrder() {
		logger.info("Clicked register order");
		pratkaRegister.registerOrder();
	}
}
