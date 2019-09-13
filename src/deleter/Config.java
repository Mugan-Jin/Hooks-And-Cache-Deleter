package deleter;

import com.beust.jcommander.Parameter;

public class Config {

	@Parameter(names = "-nogui", description = "Don't display a GUI")
	private boolean disableGUI = false;

	public boolean isDisableGUI() {
		return this.disableGUI;
	}
	
}
