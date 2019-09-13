package deleter;

import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class ConsoleController implements Initializable {
	
	@FXML
	private ListView<String> console;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		final PrintStream ps = new PrintStream(new Console(this.console), false);
		System.setOut(ps);
		System.setErr(ps);
	}

}
