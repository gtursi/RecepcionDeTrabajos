package commons.logging;

import java.util.logging.Logger;

public class AppLogger {

	private static final Logger fLogger = Logger.getLogger(AppLogger.class.getPackage().getName());

	public static Logger getLogger() {
		return fLogger;
	}

	private AppLogger() {
		super();
	}

}
