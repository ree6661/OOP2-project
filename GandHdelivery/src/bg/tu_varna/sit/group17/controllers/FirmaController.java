package bg.tu_varna.sit.group17.controllers;


import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import bg.tu_varna.sit.group17.application.Avatar;
import bg.tu_varna.sit.group17.application.FormName;
import bg.tu_varna.sit.group17.application.Load;
import bg.tu_varna.sit.group17.application.LoggerApp;
import bg.tu_varna.sit.group17.application.MessageBox;
import bg.tu_varna.sit.group17.application.Property;
import bg.tu_varna.sit.group17.database.Add;
import bg.tu_varna.sit.group17.database.Delete;
import bg.tu_varna.sit.group17.database.TableQuery;
import bg.tu_varna.sit.group17.database.Update;
import bg.tu_varna.sit.group17.database.property.Company;
import bg.tu_varna.sit.group17.database.property.Office;
import bg.tu_varna.sit.group17.database.users.Consumer;
import bg.tu_varna.sit.group17.database.users.Courier;
import bg.tu_varna.sit.group17.database.users.User;
import bg.tu_varna.sit.group17.validation.Valid;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class FirmaController extends ControllerParent {
	
	public Company company;
	public Office office;
	public Courier courier;
	
	private Load load;
	private final LoggerApp logger = new LoggerApp(getClass().getName());
	private final MessageBox message = new MessageBox(logger);
	@FXML
	public ComboBox<String> companies, offices, city, couriers;
	@FXML
	public TextField firmaName, address, courierTextField, phone, password,
						plik, kolet, paket, tovar;
	
	@FXML
	private MenuButton userName;
	@FXML
	public ImageView avatar;
	
    @FXML
    private ResourceBundle resources;
    
    @FXML
    private URL location;
    
    @Override
	public void initData(Load load, Consumer admin) {
    	this.load = load;
    	this.consumer = admin;
	}
    
    @FXML
    void initialize() throws SQLException {
    	logger.info("In firma form");
    	logger.info("Logged admin: " + consumer);
    	//user = User.Admin;
    	this.userName.setText(consumer.getName());
    	Property.initAll();
    	this.avatar.setImage(Avatar.get());
    	if(companies.getItems().size() == 0) {
    		companies.getItems().addAll(Property.companiesMap.keySet());
    		companies.valueProperty().addListener(firmaListener());    		
    	}
    	if(this.city.getItems().size() == 0) {
    		this.city.getItems().addAll(Property.citiesMap.keySet());
    		this.city.getSelectionModel().select(0);
    	}
		offices.valueProperty().addListener(officesListener());		
		couriers.valueProperty().addListener(couriersListener());
	}
    @FXML
    void queries() throws SQLException, IOException {
    	load.form(FormName.home, consumer);
    }
    @FXML
    void registerOrder() throws SQLException, IOException {
    	load.form(FormName.pratkaRegister, consumer);
    }
    
    @FXML
	private void changeAvatar() {
		this.avatar.setImage(Avatar.next());
	}
    @FXML
	private void logOut() throws SQLException, IOException {
    	load.form(FormName.login, consumer);
	}
    
    @FXML
    private void notificationBellClick() {
    	message.alert("Нямате известия");
    }
    
    @FXML
    private void addFirma() throws SQLException {
    	logger.info("Clicked add firma");
    	
    	String name = firmaName.getText();
    	if(name.equals("")) {
    		message.alert("Полето име на фирма е празно");
    		return;
    	}
    	
    	if(TableQuery.getCompanyId(name) > 0) {
    		message.alert("Не може да се добави фирмата. Вече съществува фирма с такова име");
    		return;
    	}
    	double plik = -1, kolet = -1, paket = -1, tovar = -1;
    	try {
    		plik = Double.parseDouble(this.plik.getText());
    		kolet = Double.parseDouble(this.kolet.getText());
    		paket = Double.parseDouble(this.paket.getText());
    		tovar = Double.parseDouble(this.tovar.getText());
    		if(!Valid.price(plik) || !Valid.price(kolet) || 
    				!Valid.price(paket) || !Valid.price(tovar)) 
        		throw new NullPointerException();
        	
    	}catch(NullPointerException e) {
    		message.alert("Невалидна цена");
    		return;
    	}
    	
    	Add.companyPrice(name, plik, kolet, paket, tovar);
    	message.alert("Успешно добавена фирма");
    	logger.info("Successful added company");
    	companies.getItems().clear();
    	Property.initCompaniesMap();
    	companies.getItems().addAll(Property.companiesMap.keySet());
    }
    @FXML
    private void changeFirma() throws SQLException {
    	logger.info("Clicked change firma");
    	
    	String name = firmaName.getText();//companies.getSelectionModel().getSelectedItem();
    	if(name.equals("") || name.length() < 3) {
    		message.alert("Невалидно име на фирма");
    		return;
    	}
    	int id_company = TableQuery.getCompanyId(companies.getSelectionModel().getSelectedItem());
    	if(id_company < 1){
    		message.alert("Фирмата не може да се промени, защото не съществува");
    		return;
    	}
    	double plik = -1, kolet = -1, paket = -1, tovar = -1;
    	try {
    		plik = Double.parseDouble(this.plik.getText());
    		kolet = Double.parseDouble(this.kolet.getText());
    		paket = Double.parseDouble(this.paket.getText());
    		tovar = Double.parseDouble(this.tovar.getText());
    		if(!Valid.price(plik) || !Valid.price(kolet) || 
    				!Valid.price(paket) || !Valid.price(tovar)) 
        		throw new NullPointerException();
        	
    	}catch(NullPointerException e) {
    		message.alert("Невалидна цена");
    		return;
    	}
    	
    	Update.companyCategory(id_company, name, plik, kolet, paket, tovar);
    	message.alert("Фирмата е успешно редактирана");
    	logger.info("Successful changed company");
    	companies.getItems().clear();
    	Property.initCompaniesMap();
    	companies.getItems().addAll(Property.companiesMap.keySet());
    	companies.getSelectionModel().selectFirst();
    }
    @FXML
    private void deleteFirma() throws SQLException {
    	logger.info("Clicked delete firma");
    	
    	if(this.company == null) return;
    	Delete.companyCategory(this.company);
    	this.company = null;
    	message.alert("Фирмата е успешно изтрита");
    	logger.info("Successful deleted company");
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
    public void addOffice() throws SQLException {
    	
    	logger.info("Clicked add office");
    	
    	if(company == null) {
    		message.alert("Фирмата е празна");
    		return;
    	}
    	String cityName = this.city.getPromptText(),
    			address = this.address.getText();
    	
    	if(cityName == null || address == null || cityName.isBlank() || address.isBlank()) {
    		message.alert("Не може града и/или адреса да са празни");
    		return;
    	}
    	
    	
    	int cityIndex = Property.citiesMap.get(cityName);
    	String sql = "select * from office where id_company='" +
				this.company.getId() + 
				"' and id_city='" + cityIndex + "'and address='" + address + "'";
    	ResultSet rs = TableQuery.execute(sql);
    	if(rs != null) {
    		message.alert("Вече съществува такъв офис.");
    		return;
    	}
		Add.office(this.company.getId(), cityIndex, address);
		message.alert("Успешно добавен офис");
		logger.info("Successful added office");
		offices.getSelectionModel().selectFirst();
		initialize();
    }
    @FXML
    private void changeOffice() throws SQLException {
    	logger.info("Clicked change office");
    	
    	if(this.office == null || this.offices.getPromptText().isBlank() ||
    			this.companies.getPromptText().isBlank()) {
    		message.alert("Фирмата и офиса не може да са празни");
    		return;
    	}
    	String cityName = this.city.getSelectionModel().getSelectedItem();
    	
    	if(cityName.isBlank()) {
    		message.alert("Града не е избран");
    		return;
    	}
    	int cityIndex = Property.citiesMap.get(cityName);
    	String address = this.address.getText();
    	
    	if(cityIndex == 0 || address.equals("")) {
    		message.alert("Не може града и/или адреса да са празни");
    		return;
    	}
    	
    	String sql = "select * from office where id_company='" +
				this.company.getId() + 
				"' and id_office='" + this.office.getId_office() + "'";
    	ResultSet rs = TableQuery.execute(sql);
    	
    	if(rs == null) {
    		message.alert("Офиса не може да се промени. Не съществува такъв");
    		return;
    	}
    	
    	Update.office(this.office.getId_office(), cityIndex, address);
    	message.alert("Успешно редактиран офис");
    	logger.info("Successful changef office");
    	initialize();
    }
    @FXML
    private void deleteOffice() throws SQLException {
    	
    	logger.info("Clicked delete office");
    	if(this.office == null || this.offices.getPromptText().isBlank() ||
    			this.companies.getPromptText().isBlank()) {
    		message.alert("Фирмата и офиса не може да са празни");
    		return;
    	}
    	Delete.office(this.office);
    	this.office = null;
    	message.alert("Успешно изтрит офис");
    	logger.info("Successful deleted office");
    	if(couriers.getItems() == null) return;
    	couriers.getItems().clear();
    	if(offices.getItems() == null) return;
    	offices.getItems().clear();
    	initialize();
    }
    @FXML
    private void addCourier() throws SQLException {
    	logger.info("Clicked add courier");
    	
    	if(this.company == null || this.office == null || this.office.getId_office() == 0) {
    		message.alert("Фирмата или офиса са празни");
    		return;
    	}
    	String name = this.courierTextField.getText(),
    			phone = this.phone.getText(),
    			password = this.password.getText();
    	
    	if(name.equals("") || phone.equals("") || password.equals("")) {
    		message.alert("Полетата не може да са празни");
    		return;
    	}
    	
    	String err = Valid.user(name, phone, password, password);
    	if(!err.equals("")) {
    		message.alert(err);
    		return;
    	}
    	
    	String sql = "select * from couriers where id_office='" +
				this.office.getId_office() + 
				"' and name='" + name + "' and phone='" + phone + 
				"' and password='" + password + "'";
    	ResultSet rs = TableQuery.execute(sql);
    	if(rs != null) {
    		message.alert("Вече съществува такъв куриер");
    		return;
    	}
		Add.courier(name, phone, password, this.office.getId_office());
		rs = TableQuery.execute("select * from couriers where phone='" + phone + "'");
		this.courier = Courier.create(rs);
		office.couriers.add(this.courier);
		message.alert("Успешно добавен куриер");
		logger.info("Successful added courier");
		couriers.getSelectionModel().selectFirst();
		rs = TableQuery.execute("select * from couriers where phone='" + phone + "'");
		if(rs != null)
			this.courier = Courier.create(rs);
		initialize();
    }
    @FXML
    private void changeCourier() throws SQLException {
    	logger.info("Clicked change courier");
    	
    	if(this.company == null || this.office == null || 
    						this.courier == null) {
    		message.alert("Фирмата офиса и куриера не може да са празни");
    		return;
    	}
    	
    	String name = this.courierTextField.getText(),
    			phone = this.phone.getText(),
    			password = this.password.getText();
    	
    	if(name.equals("") || phone.equals("") || 
    		password.equals("") || this.courier.getId() == 0) {
    		message.alert("Полетата не може да са празни");
    		return;
    	};
    	
    	String err = Valid.user(name, phone, password, password);
    	if(!err.equals("") && !err.equals("Вече съществува такъв телефон в базата данни")) {
    		message.alert(err);
    		return;
    	}
    	
    	Update.courier(this.courier.getId(), name, phone, password);
    	message.alert("Успешно редактиран куриер");
    	logger.info("Successful changed courier");
    	initialize();
    }
    @FXML
    private void deleteCourier() throws SQLException {
    	logger.info("Clicked delete courier");
    	if(this.company == null || this.office == null || this.courier == null ||
    			this.offices.getPromptText().isBlank() ||
    			this.companies.getPromptText().isBlank() || 
    			this.couriers.getPromptText().isBlank()) {
    		
    		message.alert("Фирмата офиса и куриера не може да са празни");
    		return;
    	}
    	Delete.courier(this.courier);
    	this.courier = null;
    	message.alert("Успешно изтрит куриер");
    	logger.info("Successful deleted courier");
    	if(couriers.getItems() == null) return;
    	couriers.getItems().clear();
    	initialize();
    }
    
    private ChangeListener<String> firmaListener() {
    	return new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				logger.info("Changed firma combobox");
				
				offices.getItems().clear();
				city.setPromptText(city.getSelectionModel().getSelectedItem().toString());
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
				
				try {
					String sql = "select * from price_list where id_company='" + company.getId() + "'";
					ResultSet rs = TableQuery.execute(sql);
					if(rs == null) {
						message.alert("firmata nqma ceni");
						return;
					}
					do {	
						switch(rs.getInt("id_category")) {
						case 1: plik.setText(Double.toString(rs.getDouble("price")));
						case 2: kolet.setText(Double.toString(rs.getDouble("price")));
						case 3: paket.setText(Double.toString(rs.getDouble("price")));
						case 4: tovar.setText(Double.toString(rs.getDouble("price")));
						}
					}while(rs.next());
					
					
					sql = "select * from office where id_company='" + company.getId() + "'";
				
					rs = TableQuery.execute(sql);
					
					if(rs == null) {
						offices.setPromptText("Няма офис");
						couriers.setPromptText("Няма куриер");
						message.alert("Фирмата още няма офиси");
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
					
					offices.getSelectionModel().selectFirst();
					city.setPromptText(office.getCity());
					address.setText(office.getAddress());
					
				} catch(SQLException e) {
					e.printStackTrace();
					message.alert("Не се намира офиса");
				}
			}
		};
    }
    
    private ChangeListener<String> officesListener() {
    	return new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
				logger.info("Changed office combobox");
				
				couriers.getItems().clear();
				courierTextField.setText("");
				phone.setText("");
				password.setText("");
				
				office = new Office();
				
				try {
					if(company != null)
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
					message.alert("Не са намират офисите");
				}
			}
		};
    }
    
    private ChangeListener<String> couriersListener() {
    	return new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				logger.info("Changed courier combobox");
				
				courierTextField.setText("");
				phone.setText("");
				password.setText("");
				
				int selectedIndex = couriers.getSelectionModel().getSelectedIndex();
				if(selectedIndex < 0 || selectedIndex > office.couriers.size()-1) return;
				
				courier = office.couriers.get(selectedIndex);
				
				courierTextField.setText(courier.getName());
				phone.setText(courier.getPhone());
				password.setText(courier.getPassword());
			}
		};
    }
}
