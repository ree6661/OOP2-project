package bg.tu_varna.sit.group17.application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.TreeSet;

import bg.tu_varna.sit.group17.database.TableQuery;
import bg.tu_varna.sit.group17.database.Update;
import bg.tu_varna.sit.group17.database.users.Consumer;
import bg.tu_varna.sit.group17.database.users.User;
import javafx.scene.control.Button;

public final class Notification {
	private final LoggerApp logger = new LoggerApp(getClass().getName());
	private final MessageBox message = new MessageBox(logger);
	private static final int[] statusesId = { 1, 2, 3, 4, 5 };
	// 1 неполучен 2 отказана 3 получена 4 взета 5 отказана за постоянно

	private final String izv = "-fx-background-color: black;" + "-fx-background-image: url("
			+ Property.class.getResource("../img/izv.png").toExternalForm() + ");"
			+ "	-fx-background-position:  center, center;" + "	-fx-background-repeat: no-repeat;"
			+ "	-fx-background-size: 70% 90%;" + "	-fx-border-radius: 50;";
	private final String izv2 = "-fx-background-color: black;" + "-fx-background-image: url("
			+ Property.class.getResource("../img/izv2.png").toExternalForm() + ");"
			+ "	-fx-background-position:  center, center;" + "	-fx-background-repeat: no-repeat;"
			+ "	-fx-background-size: 70% 90%;" + "	-fx-border-radius: 50;";
	private Consumer consumer;

	private Set<String> alertList;
	private Set<Integer> ordersIdList;
	public boolean delivered = false;

	public Notification(Consumer consumer) {
		this.consumer = consumer;
		alertList = new TreeSet<>();
		ordersIdList = new TreeSet<>();
	}

	public String getIconIzv() {
		return izv;
	}

	public String getIconIzv2() {
		return izv2;
	}

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}

	public static int getStatus(int status) throws IllegalArgumentException {
		if (status < 0 || status > statusesId.length - 1)
			throw new IllegalArgumentException("Wrong status");

		return statusesId[status];
	}

	public void clearNotifications() {
		alertList.clear();
		ordersIdList.clear();
	}

	public void addAlert(String notification) {
		alertList.add(notification);
	}

	public void addOrder(int id) {
		ordersIdList.add(id);
	}

	public Set<String> getAlerts() {
		return alertList;
	}

	public Set<Integer> getOrders() {
		return ordersIdList;
	}

	public void courier(Consumer consumer, Button button) throws SQLException {
		String sql = "select id_order, id_customer_recipient from orders where id_courier='" + consumer.getId()
				+ "' and id_status='" + Notification.getStatus(1) + "'";

		ResultSet rs = TableQuery.execute(sql);

		if (rs == null)
			return;
		delivered = true;
		button.setStyle(getIconIzv2());
		do {
			addAlert(TableQuery.getCustomer(rs.getInt("id_customer_recipient")));
			addOrder(rs.getInt("id_order"));
		} while (rs.next());
	}

	public void apply(Button notificationBell) {
		if (!delivered)
			message.alert("Нямате известия");
		else if (consumer.getUser() == User.Customer || consumer.getUser() == User.Courier) {

			notificationBell.setStyle(getIconIzv());
			delivered = false;

			String mess = "";
			for (String s : getAlerts())
				mess += s + " ";

			if (consumer.getUser() == User.Customer) {
				message.alert("Имате пратка в офис " + mess);
			} else {
				message.alert("Отказани пратки от клиенти: " + mess);
				this.acceptOrders();
			}
		}
	}

	public void acceptOrders() {
		try {
			for (int i : getOrders())
				Update.changeOrderStatus(i, Notification.getStatus(4));
		} catch (SQLException e) {
			logger.error("Order not accepted. ".concat(e.getMessage()));
		}
	}
}
