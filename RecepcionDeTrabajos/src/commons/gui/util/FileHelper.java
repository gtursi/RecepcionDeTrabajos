package commons.gui.util;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

import recepciondetrabajos.Constants;

import commons.logging.AppLogger;

public abstract class FileHelper {

	public static Properties getProperties(Class clazz, String fileName) {
		Properties props = new Properties();
		try {
			props.load(clazz.getResourceAsStream(fileName));
		} catch (IOException exc) {
			AppLogger.getLogger().log(Level.SEVERE, null, exc);
		}
		return props;
	}

	public static String getFileSeparator() {
		return File.pathSeparator;
	}

	public static final String OUTPUT_DIR;

	static {
		// Lista de directorios en orden de preferencia
		final String[] dirs = {
				// User's application data directory (only under MS-Windows)
				System.getenv("APPDATA"),
				// User's home directory
				System.getProperty("user.home"),
				// User's current working directory
				System.getProperty("user.dir"),
				// Default temp file path
				System.getProperty("java.io.tmpdir"), };

		String outputDir = null;
		File file;
		for (String dir : dirs) {
			if (dir != null) {
				file = new File(dir);
				if (file.exists() && file.isDirectory()) {
					file = new File(file, Constants.APP_CODENAME);
					file.mkdir();
					if (file.exists() && file.isDirectory() && file.canWrite()) {
						outputDir = file.getAbsolutePath();
						break;
					}
				}

			}
		}
		OUTPUT_DIR = outputDir;
	}
}
