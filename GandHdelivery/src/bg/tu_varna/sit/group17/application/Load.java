package bg.tu_varna.sit.group17.application;

import java.io.IOException;

import bg.tu_varna.sit.group17.controllers.InitializeData;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class Load {
	private final LoggerApp logger = new LoggerApp(getClass().getName());
	private Stage stage;
	private HostServices hostServices;
	private FXMLLoader loader;

	public Load(Stage stage, HostServices hostServices) {
		this.stage = stage;
		this.hostServices = hostServices;

	}

	public void form(FormName form, User user) {
		this.stage.hide();
		loader = new FXMLLoader(getClass().getResource("../fxml/" + form.toString() + ".fxml"));
		Scene scene = null;
		try {
			scene = switch (form) {
			case login -> loginScene();
			case register -> registerScene();
			case firma -> firmaScene();
			case home -> homeScene(user);
			case pratkaRegister -> pratkaScene();
			default -> throw new IllegalArgumentException("Unexpected value: " + form);
			};
		} catch(IOException | IllegalArgumentException e) {
			logger.error("Form " + form.toString() + "not found " + e.getMessage());
		}
		
		InitializeData controller = loader.getController();
		controller.initData(this);
		
		this.stage.setScene(scene);
		this.stage.show();
	}

	private Scene registerScene() throws IOException {
		Parent register = loader.load();
		Scene registerScene = new Scene(register);
		registerScene.getStylesheets().add(getClass().getResource("../css/login-register.css").toExternalForm());

		return registerScene;
	}

	public Scene loginScene() throws IOException {
		Parent login = loader.load();
		Scene loginScene = new Scene(login);
		loginScene.getStylesheets().add(getClass().getResource("../css/login-register.css").toExternalForm());

		return loginScene;
	}

	public Scene homeScene(User user) throws IOException, IllegalArgumentException {

		Parent home = loader.load();
		home.getStylesheets().add(getClass().getResource("../css/home.css").toExternalForm());

		switch (user) {
		case Admin:
			home.getStylesheets().add(getClass().getResource("../css/hide-pratka.css").toExternalForm());
			break;
		case Courier:
			home.getStylesheets().add(getClass().getResource("../css/hide-firma.css").toExternalForm());
			break;
		case Customer:
			home.getStylesheets().add(getClass().getResource("../css/hide-pratka.css").toExternalForm());
			home.getStylesheets().add(getClass().getResource("../css/hide-firma.css").toExternalForm());
			break;
		case Guest:
		default:
			throw new IllegalArgumentException("User not found and form home can't be opened");
		}

		Scene homeScene = new Scene(home);

		return homeScene;
	}

	public Scene pratkaScene() throws IOException {
		Parent pratka = loader.load();
		Scene pratkaScene = new Scene(pratka);
		pratkaScene.getStylesheets().add(getClass().getResource("../css/home.css").toExternalForm());
		pratkaScene.getStylesheets().add(getClass().getResource("../css/pratkaRegister.css").toExternalForm());

		return pratkaScene;
	}

	public Scene firmaScene() throws IOException {
		Parent firma = loader.load();
		Scene firmaScene = new Scene(firma);
		firmaScene.getStylesheets().add(getClass().getResource("../css/home.css").toExternalForm());
		firmaScene.getStylesheets().add(getClass().getResource("../css/hide-pratka.css").toExternalForm());

		return firmaScene;
	}

	public void openLink(String link) {
		hostServices.showDocument(link);
	}
}