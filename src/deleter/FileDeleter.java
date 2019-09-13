package deleter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.io.FileUtils;

import com.beust.jcommander.JCommander;

import javafx.application.Application;

public class FileDeleter {
	
	public static final CountDownLatch consoleStarted = new CountDownLatch(1);
	
	private static final boolean windows = System.getProperty("os.name").toLowerCase().contains("win");
	
	private static final Config config = new Config();
	
	public static void main(String[] args) {
		
		JCommander.newBuilder().addObject(config).build().parse(args);
		displayGUI();
		
		final boolean deleteHooks = deleteHooks();
		System.out.println("Deleted hooks: " + deleteHooks);
		final boolean deleteJagexCache = deleteJagexCache();
		System.out.println("Deleted jagex cache: " + deleteJagexCache);
		final boolean deleteRandom = deleteRandom();
		System.out.println("Deleted random.dat: " + deleteRandom);
		final boolean deleteLookingGlassJars = deleteLookingGlassJars();
		System.out.println("Deleted looking glass agents: " + deleteLookingGlassJars);
		final boolean deleteOsbuddyJagexCache = deleteOsbuddyJagexCache();
		System.out.println("Deleted osbuddy jagex cache: " + deleteOsbuddyJagexCache);
		
		System.out.println("File deletion finished");
	}

	private static void displayGUI() {
		if (config.isDisableGUI())
			return;
		new Thread(() -> Application.launch(ConsoleApplication.class)).start();
		try {
			consoleStarted.await();
		} 
		catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static boolean deleteHooks() {
		final File f = new File(FileUtil.getTribotSettingsDirectory().getAbsolutePath() + File.separator + "hooks.dat");
		System.out.println("Hooks path: " + f.getAbsolutePath());
		if (!f.exists())
			return true;
		return f.delete();
	}
	
	private static boolean deleteJagexCache() {
		final String s = windows ? System.getenv("USERPROFILE") : System.getProperty("user.home");
		final File f = new File(s, "jagexcache");
		try {
			System.out.println("Jagex cache path: " + f.getAbsolutePath());
			FileUtils.deleteDirectory(f);
			return true;
		} 
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private static boolean deleteRandom() {
		
		final String s = windows ? System.getenv("USERPROFILE") : System.getProperty("user.home");
		
		final File f = new File(s, "random.dat");
		System.out.println("Random.dat path: " + f.getAbsolutePath());
		if (!f.exists())
			return true;
		
		return f.delete();
	}
	
	private static boolean deleteLookingGlassJars() {
	
		final String s = System.getProperty("java.io.tmpdir");
		
		final File file = new File(s);
		
		return Arrays.stream(file.listFiles())
			.filter(f -> f.getName().toLowerCase().contains("tribot") || f.getName().toLowerCase().contains("t1_agent"))
			.filter(f -> f.getName().contains(".jar"))
			.peek(f -> System.out.println("Looking glass agent detected: " + f.getAbsolutePath()))
			.allMatch(f -> f.delete());
	}
	
	private static boolean deleteOsbuddyJagexCache() {

		final String path = windows
				? System.getenv("USERPROFILE") + File.separator + "OSBuddy" + File.separator + "jagexcache"
				: System.getProperty("user.home") + File.separator + ".osbuddy" + File.separator + "jagexcache";
		
		final File file = new File(path);
		
		System.out.println("Osbuddy jagex cache path: " + file.getAbsolutePath());
		
		try {
			FileUtils.deleteDirectory(file);
			return true;
		} 
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}

