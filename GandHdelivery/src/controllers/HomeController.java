package controllers;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import application.Launch;
import database.Order;
import database.TableQuery;
import database.users.Admin;
import database.users.Courier;
import database.users.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public final class HomeController {
	
	private String[] quеryNames = {
		"Пратки", "Статус на пратка", "Статистика на фирма",
		"Куриери", "Клиенти"
	};
	
	public static Admin admin;
	public static Courier courier;
	public static Customer customer;
	public static int user = 0;//1 admin 2 courier 3 customer
	@FXML
	private ComboBox<String> functions;
	
	@FXML
	private DatePicker dateFrom, dateTo;
	
	@FXML
	private TableView<Order> table;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private void filter() {
    	
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
    	
    	int queryIndex = functions.getSelectionModel().getSelectedIndex();
    	
    	switch(queryIndex) {
    	case 0: 
    	case 1:
    	case 2:
    	case 3:
    	case 4:
    	}
    }
    
	@FXML
    void initialize() throws SQLException {
    	functions.getItems().addAll(Arrays.asList(quеryNames));
    	functions.getSelectionModel().selectFirst();;
    	
		this.table.setEditable(true);
		this.table.setPlaceholder(new Label("Няма данни"));
		
		TableColumn<Order, String> idColumn = new TableColumn<Order, String>("ID Поръчка"),
					category = new TableColumn<Order, String>("Категория"),
					officeSender = new TableColumn<Order, String>("Изпращащ офис"),
					officeRecipient = new TableColumn<Order, String>("Получаващ офис"),
					customerSender = new TableColumn<Order, String>("Клиент изпращач"),
					customerRecipient = new TableColumn<Order, String>("Клиент получател"),
					courierC = new TableColumn<Order, String>("Обслужващ куриер"),
					statusC = new TableColumn<Order, String>("Статус"),
					fragileC = new TableColumn<Order, String>("Чупливо"),
					paidC = new TableColumn<Order, String>("Наложен платеж"),
					cashDelivery = new TableColumn<Order, String>("Цена на доставка"),
					deliveryToAddress = new TableColumn<Order, String>("Досавяне до адрес"),
					addressC = new TableColumn<Order, String>("Адрес на доставяне"),
					acceptanceSender = new TableColumn<Order, String>("Дата на изпращане"),
					customerDelivery = new TableColumn<Order, String>("Дата на получаване");
		
		table.getColumns().addAll(List.of(idColumn, category, officeSender, officeRecipient, 
				customerSender, customerRecipient, courierC, statusC, fragileC, paidC,
				cashDelivery, deliveryToAddress, addressC, acceptanceSender, customerDelivery));
		
		final ObservableList<Order> data = FXCollections.observableArrayList();
		
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
					cash_on_delivery = rs.getBoolean("cash_on_delivery"),
					delivery_to_address = rs.getBoolean("delivery_to_address");
			String address = rs.getString("address");
			Date acceptance_by_sender = rs.getDate("acceptance_by_sender"),
					customer_delivery = rs.getDate("customer_delivery");
			
			data.add(new Order(id_order, categoryName, office_sender, office_recipient,
					customer_sender, customer_recipient, courier, status, address,
					fragile, paid, cash_on_delivery, delivery_to_address, acceptance_by_sender, customer_delivery));
			
		} while(rs.next());
		
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id_order"));
		category.setCellValueFactory(new PropertyValueFactory<>("category"));
		officeSender.setCellValueFactory(new PropertyValueFactory<>("office_sender"));
		officeRecipient.setCellValueFactory(new PropertyValueFactory<>("office_recipient"));
		customerSender.setCellValueFactory(new PropertyValueFactory<>("customer_sender"));
		customerRecipient.setCellValueFactory(new PropertyValueFactory<>("customer_recipient"));
		courierC.setCellValueFactory(new PropertyValueFactory<>("courier"));
		statusC.setCellValueFactory(new PropertyValueFactory<>("status"));
		fragileC.setCellValueFactory(new PropertyValueFactory<>("address"));
		paidC.setCellValueFactory(new PropertyValueFactory<>("fragile"));
		cashDelivery.setCellValueFactory(new PropertyValueFactory<>("paid"));
		deliveryToAddress.setCellValueFactory(new PropertyValueFactory<>("cash_on_delivery"));
		addressC.setCellValueFactory(new PropertyValueFactory<>("delivery_to_address"));
		acceptanceSender.setCellValueFactory(new PropertyValueFactory<>("acceptance_by_sender"));
		customerDelivery.setCellValueFactory(new PropertyValueFactory<>("customer_delivery"));
		
		table.setItems(data);
    }
}