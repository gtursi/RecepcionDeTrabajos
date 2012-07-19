package recepciondetrabajos;

import java.util.Properties;

import commons.gui.util.FileHelper;

public abstract class Tooltips {

	public static final String ABOUT = "ABOUT";

	public static final String EXIT = "EXIT";

	public static final String HELP = "HELP";

	public static String getString(String key) {
		return properties.getProperty(key);
	}

	private static Properties properties = FileHelper.getProperties(Labels.class,
			Constants.TOOLTIPS_FILE);

}
