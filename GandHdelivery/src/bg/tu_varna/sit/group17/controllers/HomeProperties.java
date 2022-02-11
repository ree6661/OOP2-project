package bg.tu_varna.sit.group17.controllers;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import bg.tu_varna.sit.group17.application.LoggerApp;
import bg.tu_varna.sit.group17.application.MessageBox;
import bg.tu_varna.sit.group17.database.TableQuery;
import bg.tu_varna.sit.group17.database.property.Order;
import bg.tu_varna.sit.group17.database.queries.Query;
import bg.tu_varna.sit.group17.database.queries.Query2;
import bg.tu_varna.sit.group17.database.queries.Query3;
import bg.tu_varna.sit.group17.database.queries.Query4;
import bg.tu_varna.sit.group17.database.users.Customer;
import bg.tu_varna.sit.group17.validation.Valid;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * This class is used for executing the queries form the home form.
 */
public final class HomeProperties {
	private final LoggerApp logger = new LoggerApp(getClass().getName());
	private final MessageBox message = new MessageBox(logger);

	public TextField phone, IdOrder;
	public ComboBox<String> functions;
	public Button cancelOrderButton, notificationBell;
	public TableView<Query> table;
	public DatePicker dateFrom, dateTo;
	private LinkedList<Query2> orders;

	public HomeProperties(TextField phone, TextField idOrder, ComboBox<String> functions, Button cancelOrderButton,
			Button notificationBell, TableView<Query> table, DatePicker dateFrom, DatePicker dateTo,
			LinkedList<Query2> orders) {

		this.phone = phone;
		this.IdOrder = idOrder;
		this.functions = functions;
		this.cancelOrderButton = cancelOrderButton;
		this.notificationBell = notificationBell;
		this.table = table;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		this.orders = orders;
	}

	/**
	 * Query for displaying packages and information about them.
	 * 
	 * @throws SQLException         if problem with the database occurs.
	 * @throws NullPointerException if the assigned dates are invalid.
	 */
	public void query1() throws SQLException, NullPointerException {
		logger.info("In query1");
		LocalDate from = dateFrom.getValue(), to = dateTo.getValue();
		if (from == null || to == null) {
			throw new NullPointerException("Полетата за дати не може да са празни");
		}
		if (Date.valueOf(from).after(Date.valueOf(to))) {
			throw new NullPointerException("Началната дата не може да е преди крайната дата");
		}

		TableColumn<Query, String> idColumn = new TableColumn<>("ID Поръчка"),
				category = new TableColumn<>("Категория"), officeSender = new TableColumn<>("Изпращащ офис"),
				officeRecipient = new TableColumn<>("Получаващ офис"),
				customerSender = new TableColumn<>("Клиент изпращач"),
				customerRecipient = new TableColumn<>("Клиент получател"),
				courierC = new TableColumn<>("Обслужващ куриер"), statusC = new TableColumn<>("Статус"),
				fragileC = new TableColumn<>("Чупливо"), paidC = new TableColumn<>("Наложен платеж"),
				cashDelivery = new TableColumn<>("Цена на доставка"),
				deliveryToAddress = new TableColumn<>("Досавяне до адрес"),
				addressC = new TableColumn<>("Адрес на доставяне"),
				acceptanceSender = new TableColumn<>("Дата на изпращане"),
				customerDelivery = new TableColumn<>("Дата на получаване");

		table.getColumns()
				.addAll(List.of(idColumn, category, officeSender, officeRecipient, customerSender, customerRecipient,
						courierC, statusC, fragileC, paidC, cashDelivery, deliveryToAddress, addressC, acceptanceSender,
						customerDelivery));

		final ObservableList<Query> data = FXCollections.observableArrayList();

		String sql = "select * from orders";
		ResultSet rs = TableQuery.execute(sql);
		if (rs == null)
			return;

		do {
			int id_order = rs.getInt("id_order");
			String categoryName = TableQuery.categoryIdToString(rs.getInt("id_category"));
			String office_sender = TableQuery.getOffice(rs.getInt("id_office_sender")),
					office_recipient = TableQuery.getOffice(rs.getInt("id_office_recipient"));
			String customer_sender = TableQuery.getCustomer(rs.getInt("id_customer_sender")),
					customer_recipient = TableQuery.getCustomer(rs.getInt("id_customer_recipient")),
					courier = TableQuery.getCourier(rs.getInt("id_courier")),
					status = TableQuery.getStatus(rs.getInt("id_status"));
			boolean fragile = rs.getBoolean("fragile"), paid = rs.getBoolean("paid"),
					delivery_to_address = rs.getBoolean("delivery_to_address");
			String address = rs.getString("address");
			Date acceptance_by_sender = rs.getDate("acceptance_by_sender"),
					customer_delivery = rs.getDate("customer_delivery");
			if (acceptance_by_sender.before(Date.valueOf(from)) || customer_delivery.after(Date.valueOf(to)))
				continue;

			double payPrice = rs.getDouble("cash_on_delivery");

			data.add(new Order(id_order, categoryName, office_sender, office_recipient, customer_sender,
					customer_recipient, courier, status, address, fragile, paid, delivery_to_address, payPrice,
					acceptance_by_sender, customer_delivery));

		} while (rs.next());

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

		table.setItems(data);
	}

	/**
	 * Query for displaying the status of a package
	 * 
	 * @throws SQLException if problem with the database occurs.
	 */
	public void query2() throws SQLException {
		logger.info("In query2");

		String phone = this.phone.getText();
		if (!Valid.phoneNumber(phone)) {
			message.alert("Въведете правилен телефонен номер на клиент", "Телефона трябва да съдържа 12 цифри");
			return;
		}

		LocalDate from = dateFrom.getValue(), to = dateTo.getValue();
		if (from == null || to == null) {
			message.alert("Полетата за дати не може да са празни");
			return;
		}
		if (Date.valueOf(from).after(Date.valueOf(to))) {
			message.alert("Началната дата не може да е преди крайната дата");
			return;
		}

		TableColumn<Query, String> numOrder = new TableColumn<>("№ пратка"), nameC = new TableColumn<>("Име на клиент"),
				phoneC = new TableColumn<>("Телефон"), statusC = new TableColumn<>("Статус"),
				fromC = new TableColumn<>("Дата на приемане"), toC = new TableColumn<>("Дата на пристигане");

		table.getColumns().addAll(List.of(numOrder, nameC, phoneC, statusC, fromC, toC));

		final ObservableList<Query> data = FXCollections.observableArrayList();

		String sql = "select id_customer, name, phone from customers where phone='" + phone + "'";
		ResultSet customerSet = TableQuery.execute(sql);
		if (customerSet == null) {
			message.alert("Няма клиенти");
			return;
		}

		do {
			int customerId = TableQuery.getCustomerId(phone);
			sql = "select id_order, id_status, acceptance_by_sender, customer_delivery from orders where "
					+ "id_customer_recipient='" + customerId + "'";
			ResultSet orderSet = TableQuery.execute(sql);
			if (orderSet == null) {
				message.alert("Клиента с този номер няма поръчки");
				return;
			}
			do {
				String status = TableQuery.getStatus(orderSet.getInt("id_status"));
				Query2 query = new Query2(orderSet.getInt("id_order"), customerSet.getString("name"),
						customerSet.getString("phone"), status, orderSet.getDate("acceptance_by_sender"),
						orderSet.getDate("customer_delivery"));
				data.add(query);
				orders.add(query);
			} while (orderSet.next());

		} while (customerSet.next());

		numOrder.setCellValueFactory(new PropertyValueFactory<>("number"));
		nameC.setCellValueFactory(new PropertyValueFactory<>("name"));
		phoneC.setCellValueFactory(new PropertyValueFactory<>("phone"));
		statusC.setCellValueFactory(new PropertyValueFactory<>("status"));
		fromC.setCellValueFactory(new PropertyValueFactory<>("from"));
		toC.setCellValueFactory(new PropertyValueFactory<>("to"));

		table.setItems(data);
	}

	/**
	 * Query for displaying statistic of the current company.
	 * 
	 * @throws SQLException if problem with the database occurs.
	 */
	public void query3() throws SQLException {
		logger.info("In query3");

		TableColumn<Query, String> companiesC = new TableColumn<>("Брой фирми"),
				ordersC = new TableColumn<>("Брой поръчки"), officesC = new TableColumn<>("Брой офиси"),
				couriersC = new TableColumn<>("Брой куриери"), adminsC = new TableColumn<>("Брой администратори");

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

		table.setItems(data);
	}

	/**
	 * Query for displaying couriers work history.
	 * 
	 * @throws SQLException if problem with the database occurs.
	 */
	public void query4() throws SQLException {
		logger.info("In query4");

		TableColumn<Query, String> idC = new TableColumn<>("ID куриер"), nameC = new TableColumn<>("Име на куриер"),
				officeC = new TableColumn<>("Офис"), ordersC = new TableColumn<>("Брой регистрирани поръчки");

		table.getColumns().addAll(List.of(idC, nameC, officeC, ordersC));

		final ObservableList<Query> data = FXCollections.observableArrayList();

		String sql = "select id_courier, name, id_office from couriers";
		ResultSet rs = TableQuery.execute(sql);
		if (rs == null) {
			message.alert("Няма куриери");
			return;
		}

		do {
			int id_courier = rs.getInt("id_courier");
			String office = TableQuery.getOffice(rs.getInt("id_office"));
			sql = "select count(*) from orders where id_courier='" + id_courier + "'";
			ResultSet orders = TableQuery.execute(sql);
			data.add(new Query4(id_courier, orders.getInt(1), rs.getString("name"), office));

		} while (rs.next());

		idC.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameC.setCellValueFactory(new PropertyValueFactory<>("name"));
		officeC.setCellValueFactory(new PropertyValueFactory<>("office"));
		ordersC.setCellValueFactory(new PropertyValueFactory<>("orders"));

		table.setItems(data);
	}

	/**
	 * Query for displaying statistic of all customers.
	 * 
	 * @throws SQLException if problem with the database occurs.
	 */
	public void query5() throws SQLException {
		logger.info("In query5");

		TableColumn<Query, String> idC = new TableColumn<>("ID на клиент"), nameC = new TableColumn<>("Име"),
				phoneC = new TableColumn<>("Телефон"), cityC = new TableColumn<>("Град"),
				addressC = new TableColumn<>("Адрес");

		table.getColumns().addAll(List.of(idC, nameC, phoneC, cityC, addressC));

		final ObservableList<Query> data = FXCollections.observableArrayList();

		String sql = "select * from customers";
		ResultSet rs = TableQuery.execute(sql);

		if (rs == null) {
			message.alert("Няма клиенти");
			return;
		}

		do {
			Customer c = new Customer(rs.getInt("id_customer"), rs.getString("name"), rs.getString("phone"),
					rs.getString("password"), rs.getInt("id_city"), rs.getString("address"));
			c.setCity_name(TableQuery.cityIdToName(c.getId_city()));
			data.add(c);

		} while (rs.next());

		idC.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameC.setCellValueFactory(new PropertyValueFactory<>("name"));
		phoneC.setCellValueFactory(new PropertyValueFactory<>("phone"));
		cityC.setCellValueFactory(new PropertyValueFactory<>("city_name"));
		addressC.setCellValueFactory(new PropertyValueFactory<>("address"));

		table.setItems(data);
	}
}
