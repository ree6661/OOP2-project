package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import database.users.Admin;
import database.users.Courier;
import database.users.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public final class HomeController {
	
	public static Admin admin;
	public static Courier courier;
	public static Customer customer;
	public static int user = 0;//1 admin 2 courier 3 customer
	@FXML
	private ComboBox<String> functions;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void initialize() {
    	/*
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
		}*/
    }
}