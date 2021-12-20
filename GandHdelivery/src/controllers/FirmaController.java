package controllers;


import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ResourceBundle;

import application.Property;
import database.Company;
import database.Office;
import database.TableQuery;
import database.users.Admin;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class FirmaController {
	
	public Company company;
	public Office office;
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
		offices.valueProperty().addListener(OfficeListener());
		city.getItems().addAll(Property.citiesMap.keySet());
    }
    
    private ChangeListener<String> firmaListener() {
    	ChangeListener<String> listener = new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				offices.getItems().clear();
				company = new Company();
				
				company.setId(Property.companiesMap.get(newValue));
				
				String sql = "select * from office where id_company='" + company.getId() + "'";
				try {
					ResultSet rs = TableQuery.execute(sql);
					
					if(rs == null) {
						offices.setPromptText("Няма офис");
						System.out.println("no office in company");
						return;
					}
					company.setOffices(rs);
					
					for(int i = 0; i < company.offices.size(); ++i) {
						Office office = company.offices.get(i);
						
						offices.getItems().add(
								TableQuery.cityIdToName(office.getId_city()) + 
								" " + office.getAddress());
					}
					Office prompt = company.offices.get(0);
					
					offices.setPromptText(prompt.getFullAddress());
					city.setPromptText(prompt.getCity());
					address.setText(prompt.getAddress());
					
				} catch(SQLException e) {
					e.printStackTrace();
					System.out.println(e + "\nerror: can't load company office");
				}
			}
		};
		return listener;
    }
    
    private ChangeListener<String> OfficeListener() {
    	ChangeListener<String> listener = new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				//city.getItems().clear();
				office = new Office();
				
				try {
					for(int i = 0; i < company.offices.size(); ++i) {
						String officeName = 
								TableQuery.cityIdToName(company.offices.get(i).getId_city()) + 
								" " + company.offices.get(i).getAddress();
						if(officeName.equals(newValue)) {
							office = company.offices.get(i);
							break;
						}
					}
					if(office.getId_office() == 0) return;
					
					city.setPromptText(TableQuery.cityIdToName(office.getId_city()));
					
					address.setText(office.getAddress());
					
				} catch(SQLException e) {
					e.printStackTrace();
					System.out.println(e + "\nerror: can't load company office");
				}
			}
		};
		return listener;
    }
}
