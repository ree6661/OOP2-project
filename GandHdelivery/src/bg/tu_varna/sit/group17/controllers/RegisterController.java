package bg.tu_varna.sit.group17.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import bg.tu_varna.sit.group17.application.FormName;
import bg.tu_varna.sit.group17.application.Load;
import bg.tu_varna.sit.group17.application.LoggerApp;
import bg.tu_varna.sit.group17.application.MessageBox;
import bg.tu_varna.sit.group17.application.Property;
import bg.tu_varna.sit.group17.database.Add;
import bg.tu_varna.sit.group17.database.users.Consumer;
import bg.tu_varna.sit.group17.validation.Valid;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public final class RegisterController extends ControllerParent { 
	
	private final LoggerApp logger = new LoggerApp(getClass().getName());
	private final MessageBox message = new MessageBox(logger);
	
	@FXML
	private TextField name, phone, address;
	@FXML
	private ComboBox<String> cBox0;
	@FXML
	private PasswordField password, repeatPassword;
	
	@Override
	public void initData(Load load, Consumer consumer) {
		this.load = load;
		this.consumer = consumer;
	}

	@FXML
	public void initialize() {
		logger.info("In register form");
		cBox0.getItems().addAll(Property.citiesMap.keySet());	
	}
	
    @FXML
    public void register(ActionEvent e) {
    	
    	try {
	    	logger.info("Clicked register");
	    	String username = this.name.getText(), 
	    			phoneNumber = this.phone.getText(),
	    			city = this.cBox0.getValue(),
	    			location = this.address.getText(),
	    			password = this.password.getText(),
	    	    	repeatPassword = this.repeatPassword.getText();
	    	String err = "";
	    	err = Valid.user(username, phoneNumber, password, repeatPassword);
	    	if(!err.equals("")) {
	    		message.alert(err);
	    		return;
	    	}
	    	
	    	if(city.equals("град")) {
	    		city = "";
	    	}
	    	
	    	Add.customer(username, phoneNumber, Property.citiesMap.get(city), location, password);
	    	
	    	message.alert("Успешна регистрация.");
	    	logger.info("Successful register");
    	} catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    @FXML
    public void login(ActionEvent e) throws SQLException, IOException {
    	load.form(FormName.login, consumer);
    }
    
    @FXML
    private ResourceBundle resources;
    
    @FXML
    private URL location;
}
