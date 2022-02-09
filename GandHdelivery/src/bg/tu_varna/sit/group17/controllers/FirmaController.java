package bg.tu_varna.sit.group17.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import bg.tu_varna.sit.group17.application.FormName;
import bg.tu_varna.sit.group17.application.Load;
import bg.tu_varna.sit.group17.application.LoggerApp;
import bg.tu_varna.sit.group17.application.MessageBox;
import bg.tu_varna.sit.group17.database.Firma;
import bg.tu_varna.sit.group17.database.users.Consumer;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class FirmaController extends ControllerParent {
	private final LoggerApp logger = new LoggerApp(getClass().getName());
	private final MessageBox message = new MessageBox(logger);
	
	private Firma firma;
	@FXML
	public ComboBox<String> companies, offices, city, couriers;
	@FXML
	public TextField firmaName, address, courierTextField, phone, password, plik, kolet, paket, tovar;
	@FXML
	private MenuButton userName;
	@FXML
	private ImageView avatar;
	@FXML
	private ResourceBundle resources;
	@FXML
	private URL location;

	@Override
	public void initData(Load load, Consumer consumer) {
		this.load = load;
		this.consumer = consumer;
		this.firma = new Firma(this);
		
		this.userName.setText(consumer.getName());
		this.avatar.setImage(load.getAvatar().get());
		this.firma.prepareForm();
	}

	@FXML
	private void initialize() {
		logger.info("In firma form");
		logger.info("Logged admin: " + consumer);
	}

	@FXML
	private void queries() {
		load.form(FormName.home, consumer);
	}

	@FXML
	private void registerOrder() {
		load.form(FormName.pratkaRegister, consumer);
	}

	@FXML
	private void changeAvatar() {
		this.avatar.setImage(load.getAvatar().next());
	}

	@FXML
	private void logOut() {
		load.form(FormName.login, consumer);
	}

	@FXML
	private void notificationBellClick() {
		message.alert("Нямате известия");
	}

	@FXML
	private void addFirma() {
		logger.info("Clicked add firma");
		firma.addFirma();
	}

	@FXML
	private void changeFirma() {
		logger.info("Clicked change firma");
		firma.changeFirma();
	}

	@FXML
	private void deleteFirma() {
		logger.info("Clicked delete firma");
		firma.deleteFirma();
	}

	@FXML
	private void addOffice() {
		logger.info("Clicked add office");
		firma.addOffice();
	}

	@FXML
	private void changeOffice() {
		logger.info("Clicked change office");
		firma.changeOffice();
	}

	@FXML
	private void deleteOffice() {
		logger.info("Clicked delete office");
		firma.deleteFirma();
	}

	@FXML
	private void addCourier() {
		logger.info("Clicked add courier");
		firma.addCourier();
	}

	@FXML
	private void changeCourier() {
		logger.info("Clicked change courier");
		firma.changeCourier();
	}

	@FXML
	private void deleteCourier() {
		logger.info("Clicked delete courier");
		firma.deleteCourier();
	}
}
