package controllers;

import java.net.URL;
import java.util.ResourceBundle;

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
    void loginFx(ActionEvent event) {
    	String phoneNumber = this.phone.getText(),
    			password = this.pass.getText();
    	
    	if(!Valid.phoneNumber(phoneNumber) || !Valid.password(password)) {
    		//this.error.setText("Името не трябва да съдържа символи и да е твърде кратко");
    		System.out.println(err);
    		return;
    	}
    		//this.error.setText("Вече съществува такъв телефон в базата данни");
    	
    	final String userPassword = 
    			TableQuery.getRecordFromTables(
    					"password", "phone", phoneNumber, users); 
    	
    	if(!password.equals(userPassword)) {
    		System.out.println(err);
    		return;
    	}
    	
    	System.out.println("password: " + userPassword);
    	
    	System.out.println("Successful login");
    }
}
