package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.Launch;
import database.Admin;
import database.Courier;
import database.Customer;
import database.TableQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import validation.Valid;

public final class LoginController {
	
	private final String err = "Invalid phone number or password";
	private final String[] users = 
			{"customers", "couriers", "admins"};
	
	@FXML
	private TextField phone, pass;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;
	
	@FXML
	void initialize() {

	}
	
	@FXML
    void registerFx(ActionEvent event) {
		
	}
	
	@FXML
    void loginFx(ActionEvent event) throws SQLException, IOException {
    	String phoneNumber = this.phone.getText(),
    			password = this.pass.getText();
    	
    	if(!Valid.phoneNumber(phoneNumber) || !Valid.password(password)) {
    		//this.error.setText("Името не трябва да съдържа символи и да е твърде кратко");
    		System.out.println(err);
    		return;
    	}
    		//this.error.setText("Вече съществува такъв телефон в базата данни");
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
    		System.out.println(err);
    		return;
    	}
    	
    	System.out.println(recordTable);
    	
		switch(recordTable) {
		case "customers":
			HomeController.customer = Customer.create(record);
			Launch.launch.homeForm();
			break;
		case "couriers":
			PratkaRegisterController.courier = Courier.create(record);
			Launch.launch.pratkaForm();
			break;
		case "admins":
			FirmaController.admin = Admin.create(record);
			Launch.launch.firmaForm();
			break;
		default:
			System.out.println("error: unknown table: " + recordTable);
			return;
		}
		
    	System.out.println("Successful login");
    }
}
