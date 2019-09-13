package deleter;

import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;

public class ConsoleController implements Initializable {
	
	@FXML
	private ListView<String> console;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		final PrintStream ps = new PrintStream(new Console(this.console), false);
		System.setOut(ps);
		System.setErr(ps);
		final ContextMenu cm = new ContextMenu();
		final MenuItem copy = new MenuItem("Copy Selected");
		copy.setOnAction(e -> {
			final String s = this.console.getSelectionModel().getSelectedItems().stream().collect(Collectors.joining(System.lineSeparator()));
			final ClipboardContent content = new ClipboardContent();
			content.put(DataFormat.PLAIN_TEXT, s);
			Clipboard.getSystemClipboard().setContent(content);
		});
		final MenuItem copyAll = new MenuItem("Copy All");
		copyAll.setOnAction(e -> {
			final String s = this.console.getItems().stream().collect(Collectors.joining(System.lineSeparator()));
			final ClipboardContent content = new ClipboardContent();
			content.put(DataFormat.PLAIN_TEXT, s);
			Clipboard.getSystemClipboard().setContent(content);
		});
		cm.getItems().addAll(copy, copyAll);
		this.console.setContextMenu(cm);
	}

}
