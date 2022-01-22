package bg.tu_varna.sit.group17.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.ResourceBundle;

import bg.tu_varna.sit.group17.application.Avatar;
import bg.tu_varna.sit.group17.application.FormName;
import bg.tu_varna.sit.group17.application.Load;
import bg.tu_varna.sit.group17.application.LoggerApp;
import bg.tu_varna.sit.group17.application.MessageBox;
import bg.tu_varna.sit.group17.application.Notification;
import bg.tu_varna.sit.group17.application.Property;
import bg.tu_varna.sit.group17.database.Add;
import bg.tu_varna.sit.group17.database.TableQuery;
import bg.tu_varna.sit.group17.database.Update;
import bg.tu_varna.sit.group17.database.property.Company;
import bg.tu_varna.sit.group17.database.users.Consumer;
import bg.tu_varna.sit.group17.validation.Valid;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class PratkaRegisterController extends ControllerParent {

	public LinkedList<Company> companies;
	public Company company;

	private final LoggerApp logger = new LoggerApp(getClass().getName());
	private final MessageBox message = new MessageBox(logger);
	@FXML
	private ComboBox<String> category, officeSender, officeReceiver, companySender;
	@FXML
	private CheckBox fragile, sendToAddress, isPaid;

	@FXML
	private TextField phoneSender, phoneReceiver, sendPrice, address;
	@FXML
	private MenuButton userName;
	@FXML
	public ImageView avatar;

	@FXML
	private DatePicker receiveDate, clientReceiveDate;

	@FXML
	private Button notificationBell;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private void queries() throws SQLException, IOException {
		load.form(FormName.home, consumer);
	}

	@Override
	public void initData(Load load, Consumer consumer) {
		this.load = load;
		this.consumer = consumer;

		this.avatar.setImage(Avatar.get());
		try {
			courierNotification();
			this.userName.setText(consumer.getName());
			companies = TableQuery.allCompanies();
			if (companySender.getItems().size() == 0) {
				for (Company c : companies)
					if (c.offices.size() != 0)
						companySender.getItems().add(c.getName());

				companySender.valueProperty().addListener(firmaListener());
			}
			String sql = "select category from categories";
			ResultSet rs = TableQuery.execute(sql);
			if (rs == null) {
				message.alert("Не е избрана категория");
				return;
			}
			this.category.valueProperty().addListener(categoryListener());
			do
				this.category.getItems().add(rs.getString("category"));
			while (rs.next());
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}

	@FXML
	void initialize() throws SQLException {
		logger.info("In pratka register form");
		logger.info("Logged courier: " + consumer);

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
	private void notificationBellClick() throws SQLException {
		if (load.notification.delivered) {

			this.notificationBell.setStyle(load.notification.getIconIzv());
			load.notification.delivered = false;
			String customers = "";
			for (String s : load.notification.getAlerts())
				customers += s + " ";

			message.alert("Отказани пратки от клиенти: " + customers);

			for (int i : load.notification.getOrders())
				Update.changeOrderStatus(i, Notification.getStatus(4));
			return;
		}
		message.alert("Нямате известия");
	}

	private void courierNotification() throws SQLException {
		String sql = "select id_order, id_customer_recipient from orders where id_courier='" + consumer.getId()
				+ "' and id_status='" + Notification.getStatus(1) + "'";
		ResultSet rs = TableQuery.execute(sql);

		if (rs == null)
			return;
		load.notification.delivered = true;

		do {
			load.notification.addAlert(TableQuery.getCustomer(rs.getInt("id_customer_recipient")));
			load.notification.addOrder(rs.getInt("id_order"));
		} while (rs.next());

		notification();
	}

	private void notification() {
		this.notificationBell.setStyle(load.notification.getIconIzv2());
	}

	@FXML
	void registerOrder() throws SQLException {
		logger.info("Clicked register order");

		String phoneSender = this.phoneSender.getText(), phoneReceiver = this.phoneReceiver.getText(),
				address = this.address.getText();
		LocalDate receiveDate = this.receiveDate.getValue(), clientLocalDate = this.clientReceiveDate.getValue();

		if (receiveDate == null || clientLocalDate == null) {
			message.alert("Полетата за дати не може да са празни");
			return;
		}

		boolean fragile = this.fragile.isSelected(), sendToAddress = this.sendToAddress.isSelected(),
				isPaid = this.isPaid.isSelected();
		double price = 0;
		if (this.sendPrice == null) {
			message.alert("Полето за цена не може да е празно");
			return;
		}
		try {
			price = Double.parseDouble(this.sendPrice.getText());
		} catch (Exception e) {
			return;
		}
		if (!Valid.order(phoneSender, phoneReceiver, this.receiveDate.getValue().toString(),
				this.clientReceiveDate.getValue().toString())) {
			message.alert("Невалиден телефон на клиент");
			return;
		}

		if (!sendToAddress && officeSender.getSelectionModel().getSelectedItem()
				.equals(officeReceiver.getSelectionModel().getSelectedItem())) {
			message.alert("Не може да се достави до същия офис");
			return;
		}
		if (sendToAddress && address.equals("")) {
			message.alert("Полето адрес не може да е празно");
			return;
		}

		if (Date.valueOf(receiveDate).after(Date.valueOf(clientLocalDate))) {
			message.alert("Не може да се изпрати, преди дата на получаване");
			return;
		}

		int id_category = TableQuery.getCategoryId(category.getSelectionModel().getSelectedItem());
		int id_office_sender = TableQuery.getOfficeId(officeSender.getSelectionModel().getSelectedItem()),
				id_office_recipient = TableQuery.getOfficeId(officeReceiver.getSelectionModel().getSelectedItem()),
				id_customer_sender = TableQuery.getCustomerId(phoneSender),
				id_customer_recipient = TableQuery.getCustomerId(phoneReceiver), id_courier = consumer.getId();

		final int id_status = 1;

		if (id_category == 0) {
			System.out.println("Can't find ID");
			return;
		}
		Add.order(id_category, id_office_sender, id_office_recipient, id_customer_sender, id_customer_recipient,
				id_courier, id_status, fragile, isPaid, price, sendToAddress, address, Date.valueOf(receiveDate),
				Date.valueOf(clientLocalDate));
		message.alert("Успешно добавена поръчка");
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

				if (newValue == null)
					return;

				company.setId(Property.companiesMap.get(newValue));
				company.setName(newValue);

				try {
					for (int i = 0; i < companies.size(); ++i) {
						for (int j = 0; j < companies.get(i).offices.size(); ++j)
							officeSender.getItems().add(companies.get(i).offices.get(j).getFullAddress());
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

				int id_category = category.getSelectionModel().getSelectedIndex() + 1;

				try {
					String sql = "select price from price_list where id_company='" + company.getId()
							+ "' and id_category='" + id_category + "'";
					ResultSet rs = TableQuery.execute(sql);
					if (rs == null) {
						message.alert("Фирмата няма цени");
						return;
					}

					sendPrice.setText(Double.toString(rs.getDouble("price")));

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		};
	}
}
