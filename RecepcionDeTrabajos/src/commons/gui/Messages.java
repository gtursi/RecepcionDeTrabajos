package commons.gui;

import java.util.ResourceBundle;

import recepciondetrabajos.Constants;

public abstract class Messages {

	public static String getString(String key) {
		return msgs.getString(key);
	}

	private static ResourceBundle msgs = ResourceBundle
			.getBundle(Constants.MESSAGES_RESOURCE_BUNDLE);
}