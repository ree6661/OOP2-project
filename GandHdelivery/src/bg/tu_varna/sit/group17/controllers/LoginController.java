package bg.tu_varna.sit.group17.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import bg.tu_varna.sit.group17.application.FormName;
import bg.tu_varna.sit.group17.application.Load;
import bg.tu_varna.sit.group17.application.LoggerApp;
import bg.tu_varna.sit.group17.application.MessageBox;
import bg.tu_varna.sit.group17.application.Property;
import bg.tu_varna.sit.group17.application.User;
import bg.tu_varna.sit.group17.database.TableQuery;
import bg.tu_varna.sit.group17.database.users.Admin;
import bg.tu_varna.sit.group17.database.users.Courier;
import bg.tu_varna.sit.group17.database.users.Customer;
import bg.tu_varna.sit.group17.validation.Valid;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;

public final class LoginController implements InitializeData {
	
	private Load load;
	private final String err = "Невалидни телефон и/или парола";
	private final String[] users = 
			{"customers", "couriers", "admins"};
	private final String ginko = "https://medpedia.framar.bg/%D0%B0%D0%BB%D1%82%D0%B5%D1%80%D0%BD%D0%B0%D1%82%D0%B8%D0%B2%D0%BD%D0%B0-%D0%BC%D0%B5%D0%B4%D0%B8%D1%86%D0%B8%D0%BD%D0%B0/%D0%BA%D0%B0%D0%BA-%D0%B4%D0%B0-%D0%BF%D0%BE%D0%B4%D1%81%D0%B8%D0%BB%D0%B8%D0%BC-%D0%BF%D0%B0%D0%BC%D0%B5%D1%82%D1%82%D0%B0-%D1%81%D0%B8";
	private final LoggerApp logger = new LoggerApp(getClass().getName());
	private final MessageBox message = new MessageBox(logger);
	@FXML
	private TextField phone, pass;
	
	@FXML
	private Hyperlink forgot;
	
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;
	

	@Override
	public void initData(Load load) {
		this.load = load;
	}
	
	@FXML
	void initialize() {
		logger.info("In login form");
		Property.resetAvatar();
		Property.delivered = false;
		Property.alertNotificationList.clear();
		Property.ordersIdNotification.clear();
	}
	
	@FXML
    void registerFx(ActionEvent event) throws SQLException, IOException {
		
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
	}
	
	@FXML
    void loginFx(ActionEvent event) throws SQLException, IOException {
		logger.info("Clicked login");
		String phoneNumber = this.phone.getText(),
    			password = this.pass.getText();
    	
    	if(!Valid.phoneNumber(phoneNumber) || !Valid.password(password)) {
    		message.alert(err);
    		return;
    	}
    	
    	ResultSet record = null;
    	String recordTable = null;
    	for(int i = 0; i < users.length; ++i) {
    		record = TableQuery.getRecordFromTable(
    					"phone", phoneNumber, users[i], "password", password); 
    		
    		if(record != null) {
				recordTable = users[i];
				break;
    		}
    	}
    	
    	if(recordTable == null) {
    		message.alert(err);
    		return;
    	}
    	
		switch(recordTable) {
		case "customers":
			HomeController.customer = Customer.create(record);
			Property.username = HomeController.customer.getName();
			Property.user = User.Customer;
			load.form(FormName.home, User.Customer);
			break;
		case "couriers":
			PratkaRegisterController.courier = Courier.create(record);
			Property.username = PratkaRegisterController.courier.getName();
			Property.user = User.Courier;
			load.form(FormName.pratkaRegister, User.Courier);
			break;
		case "admins":
			FirmaController.admin = Admin.create(record);
			Property.username = FirmaController.admin.getName();
			Property.user = User.Admin;
			load.form(FormName.firma, User.Admin);
			break;
		}
    }
	@FXML
	private void forgotPassword() {
		load.openLink(this.ginko);
	}
}
