package controllers;


import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.Launch;
import application.Property;
import database.Add;
import database.Company;
import database.Office;
import database.TableQuery;
import database.Update;
import database.users.Admin;
import database.users.Courier;
import database.users.Delete;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import validation.Valid;

public class FirmaController {
	
	public Company company;
	public Office office;
	public Courier courier;
	
	public static Admin admin;
	
	@FXML
	private ComboBox<String> companies, offices, city, couriers;
	@FXML
	private TextField firmaName, address, courierTextField, phone, password;
	
    @FXML
    private ResourceBundle resources;
    
    @FXML
    private URL location;
    
    @FXML
    void initialize() throws SQLException {
    	Property.initAll();
//    	companies.getItems().clear();
//    	offices.getItems().clear();
//    	city.getItems().clear();
//    	couriers.getItems().clear();
    	if(companies.getItems().size() == 0) {
    		companies.getItems().addAll(Property.companiesMap.keySet());
    		companies.valueProperty().addListener(firmaListener());    		
    	}
    	if(city.getItems().size() == 0) {
    		city.getItems().addAll(Property.citiesMap.keySet());
    		city.getSelectionModel().select(0);
    	}
		offices.valueProperty().addListener(officesListener());		
		couriers.valueProperty().addListener(couriersListener());
    }
    @FXML
    void queries() {
    	
    }
    @FXML
    void registerOrder() throws SQLException, IOException {
    	
    	Launch.launch.pratkaForm();
    }
    
    @FXML
    private void addFirma() throws SQLException {
    	String name = firmaName.getText();
    	if(name.equals("")) return;
    	
    	if(TableQuery.getCompanyId(name) > 0) {
    		System.out.println("Can't add company: already exists");
    		return;
    	}
    	
    	Add.company(name);
    	System.out.println("Successfully added company");
    	companies.getItems().clear();
    	Property.initCompaniesMap();
    	companies.getItems().addAll(Property.companiesMap.keySet());
    }
    @FXML
    private void changeFirma() throws SQLException {
    	String name = companies.getSelectionModel().getSelectedItem();
    	if(name.equals("") || name.length() < 3) return;
    	int id_company = TableQuery.getCompanyId(name);
    	if(id_company < 1){
    		System.out.println("Can't change company: not exists");
    		return;
    	}
    	Update.company(id_company, name);
    	System.out.println("Successfully updated company");
    }
    @FXML
    private void deleteFirma() throws SQLException {
    	if(this.company == null) return;
    	Delete.company(this.company);
    	this.company = null;
    	System.out.println("Successfully deleted company");
    	if(couriers.getItems() == null) return;
    	couriers.getItems().clear();
    	if(offices.getItems() == null) return;
    	offices.getItems().clear();
    	if(companies.getItems() == null) return;
    	companies.getItems().clear();
    	Property.initCompaniesMap();
    	companies.getItems().addAll(Property.companiesMap.keySet());
    }
    @FXML
    private void addOffice() throws SQLException {
    	if(company == null) return;
    	String cityName = this.city.getPromptText(),
    			address = this.address.getText();
    	
    	if(cityName.equals("") || address.equals("")) return;
    	
    	int cityIndex = Property.citiesMap.get(cityName);
    	String sql = "select * from office where id_company='" +
				this.company.getId() + 
				"' and id_city='" + cityIndex + "'and address='" + address + "'";
    	ResultSet rs = TableQuery.execute(sql);
    	if(rs != null) {
    		System.out.println("Can't add office: already exists");
    		return;
    	}
		Add.office(this.company.getId(), cityIndex, address);
		System.out.println("successfully added office");
		initialize();
    }
    @FXML
    private void changeOffice() throws SQLException {
    	if(company == null) return;
    	String cityName = this.city.getSelectionModel().getSelectedItem();
    	
    	int cityIndex = Property.citiesMap.get(cityName);
    	String address = this.address.getText();
    	
    	if(cityIndex == 0 || address.equals("")) return;
    	
    	String sql = "select * from office where id_company='" +
				this.company.getId() + 
				"' and id_office='" + this.office.getId_office() + "'";
    	ResultSet rs = TableQuery.execute(sql);
    	
    	if(rs == null) {
    		System.out.println("Can't change office: not exists");
    		return;
    	}
    	System.out.println(this.office.getId_office()+ address + "c " + cityIndex);
    	Update.office(this.office.getId_office(), cityIndex, address);
    	System.out.println("successfully changed office");
    	initialize();
    }
    @FXML
    private void deleteOffice() throws SQLException {
    	Delete.office(this.office);
    	this.office = null;
    	System.out.println("Successfully deleted office");
    	if(couriers.getItems() == null) return;
    	couriers.getItems().clear();
    	if(offices.getItems() == null) return;
    	offices.getItems().clear();
    	initialize();
    }
    @FXML
    private void addCourier() throws SQLException {
    	if(this.company == null || this.office == null) return;
    	String name = this.courierTextField.getText(),
    			phone = this.phone.getText(),
    			password = this.password.getText();
    	
    	if(name.equals("") || phone.equals("") || password.equals(""))
    		return;
    	
    	String err = Valid.user(name, phone, password, password);
    	if(!err.equals("")) {
    		System.out.println(err);
    		return;
    	}
    	
    	String sql = "select * from couriers where id_office='" +
				this.office.getId_office() + 
				"' and name='" + name + "' and phone='" + phone + 
				"' and password='" + password + "'";
    	ResultSet rs = TableQuery.execute(sql);
    	if(rs != null) {
    		System.out.println("Can't add courier: already exists");
    		return;
    	}
		Add.courier(name, phone, password, this.office.getId_office());
		System.out.println("successfully added courier");
		initialize();
    }
    @FXML
    private void changeCourier() throws SQLException {
    	if(this.company == null || this.office == null || 
    						this.courier == null) {
    		System.out.println("null");
    		return;
    	}
    	
    	String name = this.courierTextField.getText(),
    			phone = this.phone.getText(),
    			password = this.password.getText();
    	
    	if(name.equals("") || phone.equals("") || 
    		password.equals("") || this.courier.getId() == 0) {
    		System.out.println("zero");
    		return;
    	};
    	
    	String err = Valid.user(name, phone, password, password);
    	if(!err.equals("") && !err.equals("Вече съществува такъв телефон в базата данни")) {
    		System.out.println("err: " + err);
    		return;
    	}
    	
    	Update.courier(this.courier.getId(), name, phone, password);
    	System.out.println("Successfully changed courier");
    	initialize();
    	/*
    	String sql = "select * from couriers where id_office='" +
				this.office.getId_office() + 
				"' and name='" + name + "' and phone='" + phone + 
				"' and password='" + password + "'";
    	ResultSet rs = TableQuery.execute(sql);
    	if(rs != null) {
    		System.out.println("Can't add courier: already exists");
    		return;
    	}
		Add.courier(name, phone, password, this.office.getId_office());
		System.out.println("successfully added courier");
		initialize();
		*/
    }
    @FXML
    private void deleteCourier() throws SQLException {
    	Delete.courier(this.courier);
    	this.courier = null;
    	System.out.println("Successfully deleted courier");
    	if(couriers.getItems() == null) return;
    	couriers.getItems().clear();
    	initialize();
    }
    
    private ChangeListener<String> firmaListener() {
    	ChangeListener<String> listener = new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				offices.getItems().clear();
				city.setPromptText("");
				address.setText("");
				
				couriers.getItems().clear();
				courierTextField.setText("");
				phone.setText("");
				password.setText("");
				
				company = new Company();
				if(newValue == null) return;
				company.setId(Property.companiesMap.get(newValue));
				
				company.setName(newValue);
				
				firmaName.setText(company.getName());
				
				String sql = "select * from office where id_company='" + company.getId() + "'";
				try {
					ResultSet rs = TableQuery.execute(sql);
					
					if(rs == null) {
						offices.setPromptText("Няма офис");
						couriers.setPromptText("Няма куриер");
						System.out.println("no office in company");
						return;
					}
					company.setOffices(rs);
					
					if(company.offices.size() == 0) {
						offices.setPromptText("Няма офис");
						couriers.setPromptText("Няма куриер");
						return;
					}
					
					for(int i = 0; i < company.offices.size(); ++i) {
						Office office = company.offices.get(i);
						
						offices.getItems().add(
								TableQuery.cityIdToName(office.getId_city()) + 
								" " + office.getAddress());
					}
					office = company.offices.get(0);
					
					//offices.setPromptText(office.getFullAddress());
					offices.getSelectionModel().selectFirst();
					city.setPromptText(office.getCity());
					address.setText(office.getAddress());
					
					//if(office.couriers.size() == 0) return;
					/*
					for(int i = 0; i < office.couriers.size(); ++i) {
						Courier courier = office.couriers.get(i);
						couriers.getItems().add(courier.getName());
					}
					
					courier = office.couriers.get(0);
					
					couriers.setPromptText(courier.getName());
					courierTextField.setAccessibleText(courier.getName());
					phone.setAccessibleText(courier.getPhone());
					password.setAccessibleText(courier.getPassword());
					*/
				} catch(SQLException e) {
					e.printStackTrace();
					System.out.println(e + "\nerror: can't load company office");
				}
			}
		};
		return listener;
    }
    
    private ChangeListener<String> officesListener() {
    	ChangeListener<String> listener = new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
				couriers.getItems().clear();
				courierTextField.setText("");
				phone.setText("");
				password.setText("");
				
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
					if(office.getId_office() == 0) {
						offices.setPromptText("Няма офис");
						return;
					}
					city.setPromptText(TableQuery.cityIdToName(office.getId_city()));
					address.setText(office.getAddress());
					
					String sql = "select * from couriers where id_office='" + office.getId_office() + "'";
					ResultSet rs = TableQuery.execute(sql);
					
					if(rs == null) {
						couriers.setPromptText("Няма куриери");
						System.out.println("no couriers");
						return;
					}
					
					office.setCouriers(rs);
					if(office.couriers.size() == 0) {
						couriers.setPromptText("Няма куриери");
						return;
					}
					
					for(int i = 0; i < office.couriers.size(); ++i) {
						Courier courier = office.couriers.get(i);
						couriers.getItems().add(courier.getName());
					}
					
					
					courier = office.couriers.get(0);
					couriers.getSelectionModel().selectFirst();
					
				} catch(SQLException e) {
					e.printStackTrace();
					System.out.println(e + "\nerror: can't load company office");
				}
			}
		};
		return listener;
    }
    
    private ChangeListener<String> couriersListener() {
    	ChangeListener<String> listener = new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
				courierTextField.setText("");
				phone.setText("");
				password.setText("");
				
				int selectedIndex = couriers.getSelectionModel().getSelectedIndex();
				if(selectedIndex < 0 || selectedIndex > office.couriers.size()-1) return;
				
				courier = office.couriers.get(selectedIndex);
				/*
				for(int i = 0; i < office.couriers.size(); ++i) {
					Courier c = office.couriers.get(i);
					String courierIdname = c.getId() + c.getName();
					if(courierIdname.equals(newValue)) {
						courier = office.couriers.get(i);
						break;
					}
				}
				*/
				courierTextField.setText(courier.getName());
				phone.setText(courier.getPhone());
				password.setText(courier.getPassword());
			}
		};
		return listener;
    }
}
