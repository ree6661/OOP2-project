package controllers;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.ResourceBundle;

import application.Property;
import database.Add;
import database.Company;
import database.TableQuery;
import database.users.Courier;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import validation.Valid;

public class PratkaRegisterController {
	
	public LinkedList<Company> companies;
	public Company company;

	public static Courier courier;
    
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
    void initialize() throws SQLException {
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
    		System.out.println("categories empty");
    		return;
    	}
    	do category.getItems().add(rs.getString("category"));
    	while(rs.next());
    	
    }
    @FXML
    void registerOrder() throws SQLException {
    	String phoneSender = this.phoneSender.getText(),
    			phoneReceiver = this.phoneReceiver.getText(),
    			address = this.address.getText();
    	LocalDate receiveDate = this.receiveDate.getValue(),
    			clientLocalDate = this.clientReceiveDate.getValue();
    	
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
    		System.out.println("Can't deliver to the same office");
    		return;
    	}
    	if(sendToAddress && address.equals("")) {
    		System.out.println("address can't be empty");
    		return;
    	}
    	
    	if(Date.valueOf(receiveDate).after(Date.valueOf(clientLocalDate))) {
    		System.out.println("Can't send date be before receive date");
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
    	System.out.println("Successfully added order");
    }
    
    private ChangeListener<String> firmaListener() {
    	return new ChangeListener<String>() {
    		@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
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
}
