package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ResourceBundle;

import database.Add;
import database.Create;
import database.TableQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import validation.Valid;

public final class RegisterController implements Initializable { 
	
	@FXML
	private TextField name, phone, address;
	@FXML
	private ComboBox<String> cBox0;
	@FXML
	private PasswordField password, repeatPassword;
	
	@FXML
	private Label error;
	
	HashMap<String, Integer> cityMap;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			Connection con = Create.getConnection();
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery("select * from cities");
			
			cityMap = new HashMap<String, Integer>();
			
			while(rs.next()) 
				cityMap.put(rs.getString(2),
							Integer.parseInt(rs.getString(1)));
			
			cBox0.getItems().addAll(cityMap.keySet());
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
    @FXML
    public void register(ActionEvent e) throws IOException {
    	System.out.println("Register");
    	this.error.setText("");
    	
    	String username = this.name.getText(), 
    			phoneNumber = this.phone.getText(),
    			city = this.cBox0.getValue(),
    			location = this.address.getText(),
    			password = this.password.getText(),
    	    	repeatPassword = this.repeatPassword.getText();
    	
    	TableQuery customerQuery = new TableQuery("customers");
    	
    	if(!Valid.username(username)) {
    		this.error.setText("Името не трябва да съдържа символи и да е твърде кратко");
    		return;
    	}
    	if(!Valid.phoneNumber(phoneNumber)) {
    		this.error.setText("Телефона трябва да съдържа 12 цифри");
    		return;
    	} else if(customerQuery.contains("phone", phoneNumber)) {
    		this.error.setText("Вече съществува такъв телефон в базата данни");
    		return;
    	}
    	if(city.equals("град")) {
    		city = "";
    	}
    	//poleto address ne e zadylzitelno
    	
    	if(!Valid.password(password)) {
    		this.error.setText("Паролата трябва да е между 5 и 30 символа");
    		return;
    	}
    	if(!repeatPassword.equals(password)) {
    		this.error.setText("Паролите не съвпадат");
    		return;
    	}
    	
    	System.out.println("Successful register");
    	
    	Add.customer(username, phoneNumber, cityMap.get(city), location, password);
    	
    	System.out.println("Added customer");
    }
    
    @FXML
    public void login(ActionEvent e) {
    	System.out.println("login");
    }
    
    @FXML
    private ResourceBundle resources;
    
    @FXML
    private URL location;
    
    @FXML
    void initialize() {
    	
    }

	
}
