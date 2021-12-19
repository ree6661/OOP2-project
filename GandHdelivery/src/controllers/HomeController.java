package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.ResourceBundle;

import database.Create;
import database.users.Customer;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public final class HomeController {
	
	public static Customer customer;
	
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