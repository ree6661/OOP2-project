package controllers;


import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.Set;

import application.Property;
import database.Company;
import database.TableQuery;
import database.users.Admin;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class FirmaController {
	
	public Company company;
	
	public static Admin admin;
	
	@FXML
	private ComboBox<String> companies, offices, city;
	@FXML
	private TextField address;
	
    @FXML
    private ResourceBundle resources;
    
    @FXML
    private URL location;
    
    @FXML
    void initialize() {
    	companies.getItems().addAll(Property.companiesMap.keySet());
		companies.valueProperty().addListener(firmaListener());
		
		city.getItems().addAll(Property.citiesMap.keySet());
    }
    
    private ChangeListener<String> firmaListener() {
    	ChangeListener<String> listener = new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				offices.getItems().clear();
				
				final int company_id = Property.companiesMap.get(newValue);
				
				String sql = "select * from office where id_company=" + company_id;
				try {
					ResultSet rs = TableQuery.execute(sql);
					if(rs == null) {
						System.out.println("no office in company");
						return;
					}
					
					sql = "select * from office where id_company=" + company_id;
					ResultSet officeResult = TableQuery.execute(sql);
					if(officeResult == null) {
						System.out.println("no office in resultset");
						return;
					}
					/*
					sql = "select city from cities where id_city=" + 
							officeResult.getString("id_city");
					ResultSet officeCity = TableQuery.execute(sql);
					if(officeCity == null) {
						System.out.println("err: office no city");
						return;
					}
					*/
					LinkedList<String> officeList = new LinkedList<>();
					
					while(officeResult.next()) {
						officeList.add(
								Property.citiesMap.get(officeResult.getString("id_city")) + 
								" " + officeResult.getString("address"));
					}
					offices.getItems().addAll(officeList);
					
					
					
					//address.setText(officeResult.getString("address"));
					
				} catch(SQLException e) {
					e.printStackTrace();
					System.out.println(e + "\nerror: can't load company office");
				}
			}
		};
		return listener;
    }
}
