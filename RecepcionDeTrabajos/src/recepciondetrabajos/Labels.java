/*
 * $Id: Labels.java,v 1.3 2007/04/16 18:04:32 cvschioc Exp $
 */
package recepciondetrabajos;

import java.util.Properties;

import commons.gui.util.FileHelper;

public abstract class Labels {

	// Only useful for class-relative resource loading and caching properties file

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}

	public static Properties getLabels() {
		return properties;
	}

	private static Properties properties = FileHelper.getProperties(Labels.class,
			Constants.GUI_LABELS_FILE);

}
