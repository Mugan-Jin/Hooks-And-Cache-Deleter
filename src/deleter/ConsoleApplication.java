package deleter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ConsoleApplication extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		final FXMLLoader loader = new FXMLLoader(getClass().getResource("/deleter/fxml.fxml"));
		final Parent root = (Parent) loader.load();
		stage.setTitle("Console");
		stage.setOnHidden(e -> System.exit(0));
		stage.setScene(new Scene(root));
		stage.show();
		FileDeleter.consoleStarted.countDown();
	}

}
