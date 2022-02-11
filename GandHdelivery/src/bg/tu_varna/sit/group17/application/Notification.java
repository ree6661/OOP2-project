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

/**
 * This class manages notifications. Checks if the user has notification.
 */
public final class Notification {
	private final LoggerApp logger = new LoggerApp(getClass().getName());
	private final MessageBox message = new MessageBox(logger);
	private static final int[] statusesId = { 1, 2, 3, 4, 5 };
	// 1 неполучен 2 отказана 3 получена 4 взета 5 отказана за постоянно

	private final String izv = "-fx-background-image: url("
			+ Property.class.getResource("../img/izv.png").toExternalForm() + ");";
	private final String izv2 = "-fx-background-image: url("
			+ Property.class.getResource("../img/izv2.png").toExternalForm() + ");";
	private Consumer consumer;

	private Set<String> alertSet;
	private Set<Integer> ordersIdSet;
	private boolean delivered = false;

	/**
	 * @param consumer consumer to be checked for notifications.
	 */
	public Notification(Consumer consumer) {
		this.consumer = consumer;
		alertSet = new TreeSet<>();
		ordersIdSet = new TreeSet<>();
	}

	/**
	 * @return the icon for no notifications.
	 */
	public String getDefaultIcon() {
		return izv;
	}

	/**
	 * @return the icon for notifications.
	 */
	public String getNotificationIcon() {
		return izv2;
	}

	/**
	 * @param consumer changes the customer for notifications.
	 */
	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}

	/**
	 * @param status status to be found.
	 * @return the id of representing status.
	 * @throws IllegalArgumentException if the argument status is invalid.
	 */
	public static int getStatus(int status) throws IllegalArgumentException {
		if (status < 0 || status > statusesId.length - 1)
			throw new IllegalArgumentException("Wrong status");

		return statusesId[status];
	}

	/**
	 * Adds notification text to the other notifications.
	 * 
	 * @param notification notification text to be added.
	 */
	public void addAlert(String notification) {
		alertSet.add(notification);
	}

	/**
	 * Adds id of the notification order.
	 * 
	 * @param id id of the order to be added for displaying.
	 */
	public void addOrder(int id) {
		ordersIdSet.add(id);
	}

	/**
	 * @return set of all notifications.
	 */
	public Set<String> getAlerts() {
		return alertSet;
	}

	/**
	 * @return set of all orders ids.
	 */
	public Set<Integer> getOrders() {
		return ordersIdSet;
	}

	/**
	 * @param delivered value representing if the order is delivered.
	 */
	public void setDelivered(boolean delivered) {
		this.delivered = delivered;
	}

	/**
	 * @param courier            courier which receives the notification.
	 * @param notificationButton button which icon will change to notification.
	 * @throws SQLException if a problem with the database occurs.
	 */
	public void courier(Consumer courier, Button notificationButton) throws SQLException {
		String sql = "select id_order, id_customer_recipient from orders where id_courier='" + courier.getId()
				+ "' and id_status='" + getStatus(1) + "'";

		ResultSet rs = TableQuery.execute(sql);

		if (rs == null)
			return;
		delivered = true;
		notificationButton.setStyle(getNotificationIcon());
		do {
			addAlert(TableQuery.getCustomer(rs.getInt("id_customer_recipient")));
			addOrder(rs.getInt("id_order"));
		} while (rs.next());
	}

	/**
	 * @param notificationBell button which icon will be changed to no notification.
	 */
	public void apply(Button notificationBell) {
		if (!delivered)
			message.alert("Нямате известия");
		else if (consumer.getUser() == User.Customer || consumer.getUser() == User.Courier) {

			notificationBell.setStyle(getDefaultIcon());
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

	/**
	 * Accepts current orders.
	 */
	public void acceptOrders() {
		try {
			for (int i : getOrders())
				Update.changeOrderStatus(i, Notification.getStatus(4));
		} catch (SQLException e) {
			logger.error("Order not accepted. ".concat(e.getMessage()));
		}
	}
}
