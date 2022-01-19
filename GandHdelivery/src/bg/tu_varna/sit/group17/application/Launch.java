package bg.tu_varna.sit.group17.application;

import java.sql.SQLException;

import bg.tu_varna.sit.group17.database.users.Consumer;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public final class Launch extends Application {
	
	private final LoggerApp logger = new LoggerApp(getClass().getName());
	private Load load;
	
	private void init(Stage stage) {
		stage.setResizable(false);
		stage.getIcons().add(new Image(getClass().getResourceAsStream("../img/logoAvatar.png")));
		this.load = new Load(stage, getHostServices());
	}
	
	@Override
	public void start(Stage stage) {
		logger.info("Starting application");
		
		init(stage);

		try {
			Property.initAll();
			
			load.form(FormName.login, new Consumer());

		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}