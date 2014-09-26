package commons.logging;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import commons.gui.util.FileHelper;

public class AppLogger {

	public static Logger getLogger() {
		return sm_logger;
	}

	private AppLogger() {
		super();
	}

	private static void createConsoleLogger(Logger logger) {
		Handler consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(consoleHandler);
	}

	private static final Level LOG_LEVEL = Level.INFO;

	private static final String LOG_FILE_NAME_PATTERN = FileHelper.OUTPUT_DIR
			+ "/ClientesYPedidos_%g.log";

	private static final int LOG_FILE_LIMIT = 512 * 1024;

	private static final int LOG_FILE_COUNT = 3;

	private static final boolean LOG_FILE_APPEND = true;

	private static final Logger sm_logger;

	static {
		sm_logger = Logger.getLogger(AppLogger.class.getName());
		sm_logger.setLevel(LOG_LEVEL);
		sm_logger.setUseParentHandlers(false);
		createConsoleLogger(sm_logger);
		sm_logger.log(Level.INFO, "Ubicación del archivo de log: " + LOG_FILE_NAME_PATTERN);

		try {
			Handler fileHandler = new FileHandler(LOG_FILE_NAME_PATTERN, LOG_FILE_LIMIT,
					LOG_FILE_COUNT, LOG_FILE_APPEND);
			fileHandler.setFormatter(new SimpleFormatter());
			sm_logger.addHandler(fileHandler);
		} catch (IOException exc) {
			sm_logger.log(Level.SEVERE,
					"Error al crear el archivo de log, se logueará por consola solamente", exc);
		}
	}

}
