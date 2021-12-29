package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.Launch;
import application.Logger;
import database.TableQuery;
import database.users.Admin;
import database.users.Courier;
import database.users.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import validation.Valid;

public final class LoginController {
	
	private final String err = "Невалидни телефон и/или парола";
	private final String[] users = 
			{"customers", "couriers", "admins"};
	
	private final Logger logger = new Logger(LoginController.class.getName());
	@FXML
	private TextField phone, pass;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;
	
	@FXML
	void initialize() {
		logger.info("In login form");
	}
	
	@FXML
    void registerFx(ActionEvent event) throws SQLException, IOException {
		Launch.launch.registerForm();
	}
	
	@FXML
    void loginFx(ActionEvent event) throws SQLException, IOException {
		logger.info("Clicked login");
		String phoneNumber = this.phone.getText(),
    			password = this.pass.getText();
    	
    	if(!Valid.phoneNumber(phoneNumber) || !Valid.password(password)) {
    		Launch.alert(err);
    		return;
    	}
    	
    	ResultSet record = null;
    	String recordTable = null;
    	for(int i = 0; i < users.length; ++i) {
    		record = TableQuery.getRecordFromTable(
    					"phone", phoneNumber, users[i]); 
    		
    		if(record != null) {
    			recordTable = users[i];
    			break;
    		}
    	}
    	
    	if(recordTable == null) {
    		Launch.alert(err);
    		return;
    	}
    	
		switch(recordTable) {
		case "customers":
			HomeController.customer = Customer.create(record);
			HomeController.user = true;
			Launch.launch.homeFormCustomer();
			break;
		case "couriers":
			PratkaRegisterController.courier = Courier.create(record);
			Launch.launch.pratkaForm();
			break;
		case "admins":
			FirmaController.admin = Admin.create(record);
			Launch.launch.firmaForm();
			break;
		}
    }
}
