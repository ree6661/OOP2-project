package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import application.Launch;
import application.Logger;
import application.Property;
import database.TableQuery;
import database.property.Order;
import database.queries.Query;
import database.queries.Query2;
import database.queries.Query3;
import database.queries.Query4;
import database.users.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import validation.Valid;

public final class HomeController {
	
	private String[] quеryNames = {
		"Пратки", "Статус на пратка", "Статистика на фирма",
		"Куриери", "Клиенти"
	};
	
	public static Customer customer;
	public static boolean user = false;
	
	private final Logger logger = new Logger(HomeController.class.getName());
	@FXML
	private ComboBox<String> functions;
	
	@FXML
	private DatePicker dateFrom, dateTo;
	
	@FXML
	private TextField phone;
	
	@FXML
	private TableView<Query> table;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
	@FXML
    void initialize() throws SQLException {
		logger.info("In home form");
		if(HomeController.user) {
			logger.info("Logged customer: " + customer);
		}
		
		if(HomeController.user) {
			this.phone.setText(HomeController.customer.getPhone());
			this.phone.setDisable(true);
			
			this.quеryNames = new String[] {"Статус на пратка"};
			this.functions.setDisable(true);
		}
		Property.initAll();
		
    	functions.getItems().addAll(Arrays.asList(quеryNames));
    	functions.getSelectionModel().selectFirst();;
    	
		this.table.setEditable(true);
		this.table.setPlaceholder(new Label("Няма данни"));
    }
	
	@FXML
	private void registerPratka() throws SQLException, IOException {
		Launch.launch.pratkaForm();
	}
	
	@FXML
	private void firma() throws SQLException, IOException {
		Launch.launch.firmaForm();
	}
	
	@FXML
    private void filter() throws SQLException {
    	logger.info("Clicked filter");
    	int queryIndex = functions.getSelectionModel().getSelectedIndex();
    	logger.info("Query index: " + queryIndex);
    	switch(queryIndex) {
    	case 0: query1(); break;
    	case 1: query2(); break;
    	case 2: query3(); break;
    	case 3: query4(); break;
    	case 4: query5(); break;
    	}
    }
	
	private void query1() throws SQLException {
		logger.info("In query1");
		LocalDate from = this.dateFrom.getValue(),
    			to = this.dateTo.getValue();
    	if(from == null || to == null) {
    		Launch.alert("Полетата за дати не може да са празни");
    		return;
    	}
    	if(Date.valueOf(from).after(Date.valueOf(to))) {
    		System.out.println("err");
    		Launch.alert("Началната дата не може да е преди крайната дата");
    		return;
    	}
		
		TableColumn<Query, String> idColumn = new TableColumn<Query, String>("ID Поръчка"),
				category = new TableColumn<Query, String>("Категория"),
				officeSender = new TableColumn<Query, String>("Изпращащ офис"),
				officeRecipient = new TableColumn<Query, String>("Получаващ офис"),
				customerSender = new TableColumn<Query, String>("Клиент изпращач"),
				customerRecipient = new TableColumn<Query, String>("Клиент получател"),
				courierC = new TableColumn<Query, String>("Обслужващ куриер"),
				statusC = new TableColumn<Query, String>("Статус"),
				fragileC = new TableColumn<Query, String>("Чупливо"),
				paidC = new TableColumn<Query, String>("Наложен платеж"),
				cashDelivery = new TableColumn<Query, String>("Цена на доставка"),
				deliveryToAddress = new TableColumn<Query, String>("Досавяне до адрес"),
				addressC = new TableColumn<Query, String>("Адрес на доставяне"),
				acceptanceSender = new TableColumn<Query, String>("Дата на изпращане"),
				customerDelivery = new TableColumn<Query, String>("Дата на получаване");
	
	table.getColumns().addAll(List.of(idColumn, category, officeSender, officeRecipient, 
			customerSender, customerRecipient, courierC, statusC, fragileC, paidC,
			cashDelivery, deliveryToAddress, addressC, acceptanceSender, customerDelivery));
	
	final ObservableList<Query> data = FXCollections.observableArrayList();
	
	String sql = "select * from orders";
	ResultSet rs = TableQuery.execute(sql);
	if(rs == null) return;
	
	do {
		int id_order = rs.getInt("id_order");
		String categoryName = TableQuery.categoryIdToString(rs.getInt("id_category"));
		String office_sender = TableQuery.getOffice(rs.getInt("id_office_sender")),
				office_recipient = TableQuery.getOffice(rs.getInt("id_office_recipient"));
		String customer_sender = TableQuery.getCustomer(rs.getInt("id_customer_sender")),
				customer_recipient = TableQuery.getCustomer(rs.getInt("id_customer_recipient")),
				courier = TableQuery.getCourier(rs.getInt("id_courier")),
				status = TableQuery.getStatus(rs.getInt("id_status"));
		boolean fragile = rs.getBoolean("fragile"),
				paid = rs.getBoolean("paid"),
				delivery_to_address = rs.getBoolean("delivery_to_address");
		String address = rs.getString("address");
		Date acceptance_by_sender = rs.getDate("acceptance_by_sender"),
				customer_delivery = rs.getDate("customer_delivery");
		if(acceptance_by_sender.before(Date.valueOf(dateFrom.getValue())) ||
				customer_delivery.after(Date.valueOf(dateTo.getValue()))) 
			continue;
		
		double payPrice = rs.getDouble("cash_on_delivery");
		
		data.add(new Order(id_order, categoryName, office_sender, office_recipient,
				customer_sender, customer_recipient, courier, status, address,
				fragile, paid, delivery_to_address,payPrice, acceptance_by_sender, customer_delivery));
		
	} while(rs.next());
	
	idColumn.setCellValueFactory(new PropertyValueFactory<>("id_order"));
	category.setCellValueFactory(new PropertyValueFactory<>("category"));
	officeSender.setCellValueFactory(new PropertyValueFactory<>("office_sender"));
	officeRecipient.setCellValueFactory(new PropertyValueFactory<>("office_recipient"));
	customerSender.setCellValueFactory(new PropertyValueFactory<>("customer_sender"));
	customerRecipient.setCellValueFactory(new PropertyValueFactory<>("customer_recipient"));
	courierC.setCellValueFactory(new PropertyValueFactory<>("courier"));
	statusC.setCellValueFactory(new PropertyValueFactory<>("status"));
	fragileC.setCellValueFactory(new PropertyValueFactory<>("fragile"));
	paidC.setCellValueFactory(new PropertyValueFactory<>("paid"));
	cashDelivery.setCellValueFactory(new PropertyValueFactory<>("cash_on_delivery"));
	deliveryToAddress.setCellValueFactory(new PropertyValueFactory<>("delivery_to_address"));
	addressC.setCellValueFactory(new PropertyValueFactory<>("address"));
	acceptanceSender.setCellValueFactory(new PropertyValueFactory<>("acceptance_by_sender"));
	customerDelivery.setCellValueFactory(new PropertyValueFactory<>("customer_delivery"));
	
	this.table.setItems(data);
	}
	
	private void query2() throws SQLException {
		logger.info("In query2");
		
		String phone = this.phone.getText();
		if(!Valid.phoneNumber(phone)) {
			Launch.alert("Въведете правилен телефонен номер на клиент", "Телефона трябва да съдържа 12 цифри");
			return;
		}
		
		LocalDate from = this.dateFrom.getValue(),
    			to = this.dateTo.getValue();
    	if(from == null || to == null) {
    		Launch.alert("Полетата за дати не може да са празни");
    		return;
    	}
    	if(Date.valueOf(from).after(Date.valueOf(to))) {
    		System.out.println("err");
    		Launch.alert("Началната дата не може да е преди крайната дата");
    		return;
    	}
		
    	TableColumn<Query, String> nameC = new TableColumn<Query, String>("Име на клиент"),
				phoneC = new TableColumn<Query, String>("Телефон"),
				statusC = new TableColumn<Query, String>("Статус"),
				fromC = new TableColumn<Query, String>("Дата на приемане"),
				toC = new TableColumn<Query, String>("Дата на пристигане");
    	
    	this.table.getColumns().addAll(List.of(nameC, phoneC, statusC, fromC, toC));
    	
    	final ObservableList<Query> data = FXCollections.observableArrayList();
    	
    	String sql = "select id_customer, name, phone from customers";
    	ResultSet customerSet = TableQuery.execute(sql);
    	if(customerSet == null) {
    		Launch.alert("Няма клиенти");
    		return;
    	}
    	
    	do {
    		int customerId = TableQuery.getCustomerId(this.phone.getText());
    		sql = "select id_status, acceptance_by_sender, customer_delivery from orders where id_customer_sender='" + customerId + "' or id_customer_recipient='" + customerId + "'";
    		ResultSet orderSet = TableQuery.execute(sql);
    		if(orderSet == null) {
    			Launch.alert("Клиента с този номер няма поръчки");
    			return;
    		}
    		String status = TableQuery.getStatus(orderSet.getInt("id_status"));
    		
    		data.add(new Query2(customerSet.getString("name"), customerSet.getString("phone"), status, orderSet.getDate("acceptance_by_sender"), orderSet.getDate("customer_delivery")));
    		
    	}while(customerSet.next());
    	
    	nameC.setCellValueFactory(new PropertyValueFactory<>("name"));
    	phoneC.setCellValueFactory(new PropertyValueFactory<>("phone"));
    	statusC.setCellValueFactory(new PropertyValueFactory<>("status"));
    	fromC.setCellValueFactory(new PropertyValueFactory<>("from"));
    	toC.setCellValueFactory(new PropertyValueFactory<>("to"));
    	
    	this.table.setItems(data);
	}
	
	private void query3() throws SQLException {
		logger.info("In query3");
		
		TableColumn<Query, String> 
				companiesC = new TableColumn<>("Брой фирми"),
				ordersC = new TableColumn<>("Брой поръчки"),
				officesC = new TableColumn<>("Брой офиси"),
				couriersC = new TableColumn<>("Брой куриери"),
				adminsC = new TableColumn<>("Брой администратори");
		
		table.getColumns().addAll(List.of(companiesC, ordersC, officesC, couriersC, adminsC));
		
		final ObservableList<Query> data = FXCollections.observableArrayList();
		
		String sql = "select count(*) from companies";
		ResultSet rs = TableQuery.execute(sql);
		int companies = rs.getInt(1);
		sql = "select count(*) from orders";
		rs = TableQuery.execute(sql);
		int orders = rs.getInt(1);
		sql = "select count(*) from office";
		rs = TableQuery.execute(sql);
		int offices = rs.getInt(1);
		sql = "select count(*) from couriers";
		rs = TableQuery.execute(sql);
		int couriers = rs.getInt(1);
		sql = "select count(*) from admins";
		rs = TableQuery.execute(sql);
		int admins = rs.getInt(1);
		
		data.add(new Query3(companies, orders, offices, couriers, admins));
		
		companiesC.setCellValueFactory(new PropertyValueFactory<>("company"));
		ordersC.setCellValueFactory(new PropertyValueFactory<>("orders"));
		officesC.setCellValueFactory(new PropertyValueFactory<>("offices"));
		couriersC.setCellValueFactory(new PropertyValueFactory<>("couriers"));
		adminsC.setCellValueFactory(new PropertyValueFactory<>("admins"));
		
		this.table.setItems(data);
	}
	
	private void query4() throws SQLException {
		logger.info("In query4");
		
		TableColumn<Query, String> 
			idC = new TableColumn<>("ID куриер"),
			nameC = new TableColumn<>("Име на куриер"),
			officeC = new TableColumn<>("Офис"),
			ordersC = new TableColumn<>("Брой регистрирани поръчки");
		
		table.getColumns().addAll(List.of(idC, nameC, officeC, ordersC));
		
		final ObservableList<Query> data = FXCollections.observableArrayList();
		
		String sql = "select id_courier, name, id_office from couriers";
		ResultSet rs = TableQuery.execute(sql);
		if(rs == null) {
			Launch.alert("Няма куриери");
			return;
		}
		
		do {
			int id_courier = rs.getInt("id_courier");
			String office = TableQuery.getOffice(rs.getInt("id_office"));
			sql = "select count(*) from orders where id_courier='" + id_courier + "'";
			ResultSet orders = TableQuery.execute(sql);
			data.add(new Query4(id_courier, orders.getInt(1), rs.getString("name"), office));
			
		}while(rs.next());
		
		idC.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameC.setCellValueFactory(new PropertyValueFactory<>("name"));
		officeC.setCellValueFactory(new PropertyValueFactory<>("office"));
		ordersC.setCellValueFactory(new PropertyValueFactory<>("orders"));
		
		this.table.setItems(data);
	}
	
	private void query5() throws SQLException {
		logger.info("In query5");
		
		TableColumn<Query, String> 
			idC = new TableColumn<>("ID на клиент"),
			nameC = new TableColumn<>("Име"),
			phoneC = new TableColumn<>("Телефон"),
			cityC = new TableColumn<>("Град"),
			addressC = new TableColumn<>("Адрес");
		
		table.getColumns().addAll(List.of(idC, nameC, phoneC, cityC, addressC));
		
		final ObservableList<Query> data = FXCollections.observableArrayList();
		
		String sql = "select * from customers";
		ResultSet rs = TableQuery.execute(sql);
		
		if(rs == null) {
			Launch.alert("Няма клиенти");
			return;
		}
		
		do {
			Customer c = new Customer(rs.getInt("id_customer"), rs.getString("name"), rs.getString("phone"), rs.getString("password"), rs.getInt("id_city"), rs.getString("address"));
			c.setCity_name(TableQuery.cityIdToName(c.getId_city()));
			data.add(c);
			
		} while(rs.next());
		
		idC.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameC.setCellValueFactory(new PropertyValueFactory<>("name"));
		phoneC.setCellValueFactory(new PropertyValueFactory<>("phone"));
		cityC.setCellValueFactory(new PropertyValueFactory<>("city_name"));
		addressC.setCellValueFactory(new PropertyValueFactory<>("address"));
		
		this.table.setItems(data);
	}
}