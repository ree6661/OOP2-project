package bg.tu_varna.sit.group17.application;

import bg.tu_varna.sit.group17.database.users.Consumer;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Class using to prepare and start the application.
 */
public final class Launch extends Application {

	private final LoggerApp logger = new LoggerApp(getClass().getName());
	private Load load;

	private void init(Stage stage) {
		stage.setResizable(false);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("../img/logoAvatar.png")));
		this.load = new Load(stage, getHostServices(), new Consumer());
	}

	@Override
	public void start(Stage stage) {
		logger.info("Starting application");
		init(stage);

		load.form(FormName.login, new Consumer());
	}

	/**
	 * @param args default argument used for starting the application.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}