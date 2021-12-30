package bg.tu_varna.sit.group17.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.ResourceBundle;

import bg.tu_varna.sit.group17.application.Launch;
import bg.tu_varna.sit.group17.application.Logger;
import bg.tu_varna.sit.group17.application.Property;
import bg.tu_varna.sit.group17.database.Add;
import bg.tu_varna.sit.group17.database.TableQuery;
import bg.tu_varna.sit.group17.database.property.Company;
import bg.tu_varna.sit.group17.database.users.Courier;
import bg.tu_varna.sit.group17.validation.Valid;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class PratkaRegisterController {
	
	public LinkedList<Company> companies;
	public Company company;

	public static Courier courier;
    
	private final Logger logger = new Logger(PratkaRegisterController.class.getName());
	@FXML
	private ComboBox<String> category, officeSender,
						officeReceiver, companySender;
	@FXML
	private CheckBox fragile, sendToAddress, isPaid;
	
	@FXML
	private TextField phoneSender, phoneReceiver, sendPrice, address;
	
	
	@FXML
	private DatePicker receiveDate, clientReceiveDate;
	
	@FXML
    private ResourceBundle resources;
    
    @FXML
    private URL location;
    
    @FXML
    private void queries() throws SQLException, IOException {
    	Launch.launch.homeFormCourier();
    }

    @FXML
    void initialize() throws SQLException {
    	logger.info("In pratka register form");
    	logger.info("Logged courier: " + courier);
    	
    	companies = TableQuery.allCompanies();
    	if(companySender.getItems().size() == 0) {
    		for(Company c : companies) 
    			if(c.offices.size() != 0)
    				companySender.getItems().add(c.getName());
    		
    		companySender.valueProperty().addListener(firmaListener());    		
    	}
    	String sql = "select category from categories";
    	ResultSet rs = TableQuery.execute(sql);
    	if(rs == null) {
    		Launch.alert("Не е избрана категория");
    		return;
    	}
    	this.category.valueProperty().addListener(categoryListener());
    	do this.category.getItems().add(rs.getString("category"));
    	while(rs.next());
    	
    }
    @FXML
    void registerOrder() throws SQLException {
    	logger.info("Clicked register order");
    	
    	String phoneSender = this.phoneSender.getText(),
    			phoneReceiver = this.phoneReceiver.getText(),
    			address = this.address.getText();
    	LocalDate receiveDate = this.receiveDate.getValue(),
    			clientLocalDate = this.clientReceiveDate.getValue();
    	
    	if(receiveDate == null || clientLocalDate == null) {
    		Launch.alert("Полетата за дати не може да са празни");
    		return;
    	}
    	
    	boolean fragile = this.fragile.isSelected(),
    			sendToAddress = this.sendToAddress.isSelected(),
    			isPaid = this.isPaid.isSelected();
    	double price = 0;
    	if(this.sendPrice == null) return;
    	try {
    		price = Double.parseDouble(this.sendPrice.getText());
    	}catch(Exception e) {return;}
    	if(!Valid.order(phoneSender, phoneReceiver,
    			this.receiveDate.getValue().toString(), this.clientReceiveDate.getValue().toString())) 
    		return;
    	
    	if(!sendToAddress && officeSender.getSelectionModel().getSelectedItem().equals(officeReceiver.getSelectionModel().getSelectedItem())) {
    		Launch.alert("Не може да се достави до същия офис");
    		return;
    	}
    	if(sendToAddress && address.equals("")) {
    		Launch.alert("Полето адрес не може да е празно");
    		return;
    	}
    	
    	if(Date.valueOf(receiveDate).after(Date.valueOf(clientLocalDate))) {
    		Launch.alert("Не може да се изпрати, преди дата на получаване");
    		return;
    	}
    	
    	int id_category = TableQuery.getCategoryId(category.getSelectionModel().getSelectedItem());
    	int id_office_sender = TableQuery.getOfficeId(officeSender.getSelectionModel().getSelectedItem()),
    		id_office_recipient = TableQuery.getOfficeId(officeReceiver.getSelectionModel().getSelectedItem()),
    		id_customer_sender = TableQuery.getCustomerId(phoneSender),
    		id_customer_recipient = TableQuery.getCustomerId(phoneReceiver),
    		id_courier = PratkaRegisterController.courier.getId();
    	
    	final int id_status = 1;
    	
    	if(id_category == 0) {
    		System.out.println("Can't find ID");
    		return;
    	}
    	Add.order(id_category, id_office_sender,
    			id_office_recipient, id_customer_sender,
    			id_customer_recipient, id_courier, id_status,
    			fragile, isPaid, price, sendToAddress, address,
    			Date.valueOf(receiveDate), Date.valueOf(clientLocalDate));
    	Launch.alert("Успешно добавена поръчка");
    	logger.info("Successful added order");
    }
    
    private ChangeListener<String> firmaListener() {
    	return new ChangeListener<String>() {
    		@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
    			logger.info("Changed firma combobox");
    			
    			officeSender.getItems().clear();
    			officeReceiver.getItems().clear();
    			
    			company = new Company();
    			
    			if(newValue == null) return;
    			
    			company.setId(Property.companiesMap.get(newValue));
    			company.setName(newValue);
    			
    			try {
	    			for(int i = 0; i < companies.size(); ++i) {
	    				for(int j = 0; j < companies.get(i).offices.size(); ++j)
								officeSender.getItems().add(
									companies.get(i).offices.get(j).getFullAddress());
	    			}
    			} catch (SQLException e) {
					e.printStackTrace();
				}
    			officeReceiver.getItems().addAll(officeSender.getItems());
    		}
    	};
    }
    
    private ChangeListener<String> categoryListener() {
    	return new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				logger.info("Changed category combobox");
				
				int id_category = category.getSelectionModel().getSelectedIndex()+1;
				
				try {
					String sql = "select price from price_list where id_company='" + company.getId() + "' and id_category='" + id_category + "'";
					ResultSet rs = TableQuery.execute(sql);
					if(rs == null) {
						Launch.alert("Фирмата няма цени");
						return;
					}
					
					sendPrice.setText(Double.toString(rs.getDouble("price")));
					
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		};
    }
}
