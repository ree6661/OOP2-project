package bg.tu_varna.sit.group17.application;

import java.io.IOException;

import bg.tu_varna.sit.group17.controllers.InitializeData;
import bg.tu_varna.sit.group17.database.users.Consumer;
import bg.tu_varna.sit.group17.database.users.User;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class used to load a specific form when it is requested.
 */
public final class Load {
	private final LoggerApp logger = new LoggerApp(getClass().getName());

	private HostServices hostServices;
	private FXMLLoader loader;

	private Stage stage;
	private Parent parent;
	private Scene scene;

	private Notification notification;
	private Property property;
	private Avatar avatar;

	/**
	 * The constructor for initializing the fields for loading every form.
	 * 
	 * @param stage        the stage needed to constuct the application.
	 * @param hostServices service needed for opening a web page.
	 * @param consumer     the consumer object which will be used across the
	 *                     application.
	 */
	public Load(Stage stage, HostServices hostServices, Consumer consumer) {
		this.stage = stage;
		this.hostServices = hostServices;

		notification = new Notification(consumer);
		notification.setDelivered(false);
		this.property = new Property();
		this.property.initCitiesAndCompanies();
		this.avatar = new Avatar();
	}

	/**
	 * @return the notification object used for accessing a notification.
	 */
	public Notification getNotification() {
		return notification;
	}

	/**
	 * @return the property object used for storing information for the application
	 *         properties.
	 */
	public Property getProperty() {
		return property;
	}

	/**
	 * @return the avatar object of the application.
	 */
	public Avatar getAvatar() {
		return avatar;
	}

	/**
	 * Opens a link in default browser.
	 * 
	 * @param link link to be opened in the browser
	 */
	public void link(String link) {
		hostServices.showDocument(link);
	}

	/**
	 * Closes current form and opens a new form with specified user information.
	 * 
	 * @param form     the form to be launched.
	 * @param consumer the user which will use the form.
	 */
	public void form(FormName form, Consumer consumer) {
		this.stage.hide();
		notification.setConsumer(consumer);
		loader = new FXMLLoader(getClass().getResource("../fxml/" + form.toString() + ".fxml"));
		try {
			this.parent = loader.load();
			this.scene = new Scene(this.parent);
			switch (form) {
			case login -> loginScene();
			case register -> registerScene();
			case firma -> firmaScene();
			case home -> homeScene(consumer.getUser());
			case pratkaRegister -> pratkaScene();
			default -> throw new IllegalArgumentException("Unexpected value: " + form);
			}
		} catch (IOException | IllegalArgumentException e) {
			logger.error("Form " + form.toString() + "not found " + e.getMessage());
			this.stage.show();
			return;
		}
		InitializeData controller = loader.getController();
		controller.initData(this, consumer);

		this.stage.setScene(scene);
		this.stage.show();
	}

	private void addStyleSheets(String styleSheets) {
		scene.getStylesheets().add(Load.class.getResource(styleSheets).toExternalForm());
	}

	private void registerScene() {
		addStyleSheets("../css/home.css");
		addStyleSheets("../css/hide-firma.css");
	}

	private void loginScene() {
		addStyleSheets("../css/login-register.css");
	}

	private void homeScene(User user) throws IllegalArgumentException {

		addStyleSheets("../css/home.css");

		switch (user) {
		case Admin:
			addStyleSheets("../css/hide-pratka.css");
			addStyleSheets("../css/hide-klient.css");
			break;
		case Courier:
			addStyleSheets("../css/hide-firma.css");
			break;
		case Customer:
			addStyleSheets("../css/hide-pratka.css");
			addStyleSheets("../css/hide-firma.css");
			addStyleSheets("../css/hide-klient.css");
			break;
		default:
			throw new IllegalArgumentException("User not found and form home can't be opened");
		}
	}

	private void pratkaScene() {
		addStyleSheets("../css/home.css");
		addStyleSheets("../css/pratkaRegister.css");
	}

	private void firmaScene() {
		addStyleSheets("../css/home.css");
		addStyleSheets("../css/hide-pratka.css");
		addStyleSheets("../css/hide-klient.css");
	}
}