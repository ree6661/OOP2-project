package bg.tu_varna.sit.group17.controllers;

import bg.tu_varna.sit.group17.application.Avatar;
import bg.tu_varna.sit.group17.application.Load;
import bg.tu_varna.sit.group17.application.LoggerApp;
import bg.tu_varna.sit.group17.application.Notification;
import bg.tu_varna.sit.group17.application.Property;
import bg.tu_varna.sit.group17.database.Login;
import bg.tu_varna.sit.group17.database.users.Consumer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public final class LoginController extends ControllerParent {
	
	private final LoggerApp logger = new LoggerApp(getClass().getName());
	private Login login;
	
	private Load load;
	
	@FXML
	private TextField phone, pass;

	@Override
	public void initData(Load load, Consumer consumer) {
		this.load = load;
		this.consumer = consumer;
		this.login = new Login(load);
	}
	
	@FXML
	void initialize() {
		logger.info("In login form");
		Avatar.reset();
		Notification.delivered = false;
		Notification.alertNotificationList.clear();
		Notification.ordersIdNotification.clear();
	}
	
	@FXML
    void registerFx(ActionEvent event) {
		/*
		String phoneNumber = this.phone.getText(),
    			password = this.pass.getText();
    	
    	if(Valid.phoneNumber(phoneNumber) && Valid.password(password)) {
    		ResultSet record = null;
    		
        	record = TableQuery.getRecordFromTable(
					"phone", phoneNumber, "couriers", "password", password);
        	
        	if(record != null) {
    			RegisterController.courier = Courier.create(record);
    			load.form(FormName.register, User.Guest);
    			return;
    		}
    	}
		message.alert("Поискайте от куриер да ви регистрира");
		*/
	}
	
	
	@FXML
    void loginFx(ActionEvent event) {
		logger.info("Clicked login");
		String phoneNumber = this.phone.getText(),
    			password = this.pass.getText();
    	login.user(phoneNumber, password);
	}

	@FXML
	private void forgotPassword() {
		load.link(Property.ginko);
	}
}
