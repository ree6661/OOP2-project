package bg.tu_varna.sit.group17.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;

import bg.tu_varna.sit.group17.application.Load;
import bg.tu_varna.sit.group17.application.LoggerApp;
import bg.tu_varna.sit.group17.application.MessageBox;
import bg.tu_varna.sit.group17.application.Notification;
import bg.tu_varna.sit.group17.controllers.HomeController;
import bg.tu_varna.sit.group17.controllers.HomeProperties;
import bg.tu_varna.sit.group17.database.queries.Query2;
import bg.tu_varna.sit.group17.database.users.Consumer;
import bg.tu_varna.sit.group17.database.users.User;
import bg.tu_varna.sit.group17.validation.Valid;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public final class Home {

	private final LoggerApp logger = new LoggerApp(getClass().getName());
	private final MessageBox message = new MessageBox(logger);

	private LinkedList<Query2> orders;
	public String[] quеryNames = { "Пратки", "Статус на пратка", "Статистика на фирма", "Куриери", "Клиенти" };
	private Load load;
	private Consumer consumer;

	private HomeProperties prop;

	public Home(HomeController contr) {
		orders = new LinkedList<>();
		this.load = contr.load;
		this.consumer = contr.consumer;
		this.prop = new HomeProperties(contr.phone, contr.IdOrder, contr.functions, contr.cancelOrderButton,
				contr.notificationBell, contr.table, contr.dateFrom, contr.dateTo, orders);
	}

	public void prepareForm() {
		if (consumer.getUser() == User.Customer) {
			logger.info("Logged customer: " + consumer);
			logger.info("Checking for updating order status");

			updateOrderStatus();
			prop.phone.setText(consumer.getPhone());
			prop.phone.setDisable(true);

			quеryNames = new String[] { "Статус на пратка" };
			prop.functions.setDisable(true);
		} else if (consumer.getUser() == User.Courier) {
			prop.cancelOrderButton.setText("Статус: Приета пратка");
		} else if (consumer.getUser() == User.Admin) {
			prop.IdOrder.setDisable(true);
			prop.cancelOrderButton.setDisable(true);
		}
		notificationCheck();

		prop.functions.getItems().addAll(Arrays.asList(quеryNames));
		prop.functions.getSelectionModel().selectFirst();

		prop.table.setEditable(true);
		prop.table.setPlaceholder(new Label("Няма данни"));
	}

	private void notificationCheck() {
		if (consumer.getUser() == User.Admin)
			return;
		if (consumer.getUser() == User.Courier) {
			courierNotification(prop.notificationBell);
			return;
		}
		String sql = "select id_office_recipient from orders where id_customer_recipient='" + consumer.getId()
				+ "' and id_status='" + Notification.getStatus(2) + "'";
		try {
			ResultSet rs = TableQuery.execute(sql);

			if (rs == null)
				return;
			load.getNotification().delivered = true;
			do {
				load.getNotification().addAlert(TableQuery.getOffice(rs.getInt("id_office_recipient")));
			} while (rs.next());
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}

		prop.notificationBell.setStyle(load.getNotification().getIconIzv2());
	}

	private void courierNotification(Button notificationBell) {
		try {
			load.getNotification().courier(consumer, notificationBell);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}

	public void updateOrderStatus() {
		try {
			TableQuery.checkOrderUpdate(consumer.getId());
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}

	public void cancelOrder(int queryIndex) {
		String idOrder = prop.IdOrder.getText(), phone = prop.phone.getText();
		if (consumer.getUser() != User.Customer) {

			int id_order = 0;

			try {
				id_order = Integer.parseInt(idOrder);
				if (id_order == 0)
					throw new NullPointerException("Невалидно ID");

				TableQuery customerQuery = new TableQuery("customers");
				if (!Valid.phoneNumber(phone) || !customerQuery.contains("phone", phone)) {
					throw new IllegalArgumentException("Невалиден телефон на клиент");
				}
				int id_customer = TableQuery.getCustomerId(phone);
				String sql = "select * from orders where id_customer_recipient='" + id_customer + "'";
				ResultSet rs = TableQuery.execute(sql);
				if (rs == null) {
					throw new NullPointerException("Клиента няма поръчки");
				}
				int id_status = rs.getInt("id_status");
				if (id_status == Notification.getStatus(1)) {
					throw new NullPointerException("Пратката вече е отказана");
				}
				if (id_status == Notification.getStatus(0)) {
					throw new NullPointerException("Пратката пратката още не е получена");
				}

				sql = "select * from orders where id_order='" + id_order + "' and id_customer_recipient='" + id_customer
						+ "'";
				rs = TableQuery.execute(sql);
				if (rs == null) {
					throw new NullPointerException("Клиента няма такава пратка с това ID");
				}

				Update.changeOrderStatus(id_order, Notification.getStatus(3));
				message.alert("Променен статус на пратката: взета от куриер");
				logger.info("Order accepted from courier");
				filter(queryIndex);

			} catch (NullPointerException | IllegalArgumentException e) {
				message.alert(e.getMessage());
				logger.error(e.getMessage());
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
			return;
		}
		try {
			if (orders.size() == 0) {
				throw new NullPointerException("Няма пратки");
			}
			int id_order = 0;

			try {
				id_order = Integer.parseInt(idOrder);
				if (id_order == 0)
					throw new NullPointerException();
			} catch (NullPointerException e) {
				throw new NullPointerException("Невалидно ID");
			}

			String sql = "select id_status from orders where id_order='" + id_order + "' and id_customer_recipient='"
					+ consumer.getId() + "'";
			ResultSet rs = TableQuery.execute(sql);

			if (rs == null) {
				throw new NullPointerException("Невалидно ID");
			}

			int id_status = rs.getInt("id_status");
			if (id_status == Notification.getStatus(1)) {
				throw new NullPointerException("Пратката вече е отказана");
			}
			if (id_status == Notification.getStatus(2)) {
				throw new NullPointerException("Пратката не може да бъде отказана, вече е получена");
			}

			Update.changeOrderStatus(id_order, Notification.getStatus(1));
			message.alert("Пратката е отказана");
			logger.info("Order cancelled");
			filter(queryIndex);
		} catch (NullPointerException e) {
			message.alert(e.getMessage());
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public void filter(int queryIndex) {
		try {
			if (consumer.getUser() == User.Customer) {
				orders.clear();
				prop.query2();
				return;
			}

			logger.info("Query index: " + queryIndex);
			switch (queryIndex) {
			case 0 -> prop.query1();
			case 1 -> prop.query2();
			case 2 -> prop.query3();
			case 3 -> prop.query4();
			case 4 -> prop.query5();
			}
		} catch (IllegalArgumentException e) {
			message.alert(e.getMessage());
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}
}
