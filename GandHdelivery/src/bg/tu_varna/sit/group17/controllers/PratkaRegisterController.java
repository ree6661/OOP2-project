package bg.tu_varna.sit.group17.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import bg.tu_varna.sit.group17.application.Avatar;
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

public final class PratkaRegisterController extends ControllerParent {

	private final LoggerApp logger = new LoggerApp(getClass().getName());

	private PratkaRegister pratkaRegister;

	@FXML
	public ComboBox<String> category, officeSender, officeReceiver, companySender;
	@FXML
	public CheckBox fragile, sendToAddress, isPaid;
	@FXML
	public TextField phoneSender, phoneReceiver, sendPrice, address;
	@FXML
	public MenuButton userName;
	@FXML
	public ImageView avatar;
	@FXML
	public DatePicker receiveDate, clientReceiveDate;
	@FXML
	public Button notificationBell;
	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;

	@Override
	public void initData(Load load, Consumer consumer) {
		this.load = load;
		this.consumer = consumer;
		this.pratkaRegister = new PratkaRegister(this);

		this.avatar.setImage(Avatar.get());
		this.userName.setText(consumer.getName());
		this.pratkaRegister.prepareForm();
	}

	@FXML
	void initialize() {
		logger.info("In pratka register form");
		logger.info("Logged courier: " + consumer);
	}

	@FXML
	private void changeAvatar() {
		this.avatar.setImage(Avatar.next());
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
		load.notification.apply(this.notificationBell);
	}

	@FXML
	void registerOrder() {
		logger.info("Clicked register order");
		pratkaRegister.registerOrder();
	}
}
