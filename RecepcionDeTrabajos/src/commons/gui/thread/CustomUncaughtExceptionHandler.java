package commons.gui.thread;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.logging.Level;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;

import commons.gui.util.PageHelper;
import commons.logging.AppLogger;

/**
 * Clase que maneja las excepciones no chequeadas en la GUI de Depositantes y Cuentas.
 * 
 */
public class CustomUncaughtExceptionHandler implements UncaughtExceptionHandler {

	public synchronized void uncaughtException(Thread thread, Throwable throwable) {
		try {
			System.err.print((new StringBuilder()).append("Exception in thread \"")
					.append(thread.getName()).append("\" ").toString());
			throwable.printStackTrace(System.err);
			// logea al archivo, lo muestra por pantalla solo en desarrollo
			AppLogger.getLogger().log(Level.SEVERE, throwable.getMessage(), throwable);
			String localizedMessage = StringUtils.isBlank(throwable.getLocalizedMessage()) ? DEFAULT_ERROR_MESSAGE
					: throwable.getLocalizedMessage();
			final Status status = new Status(IStatus.ERROR, "dummy plugin", IStatus.OK,
					localizedMessage, throwable.getCause());

			Runnable runnable = new Runnable() {

				public void run() {
					ErrorDialog.openError(null, "Error", "Ha ocurrido un error en la aplicación",
							status);
				}
			};

			PageHelper.getDisplay().syncExec(runnable);

		} catch (Exception e) {
			System.err.print((new StringBuilder()).append("Exception in thread \"")
					.append(thread.getName()).append("\" ").toString());
			e.printStackTrace(System.err);
		}
	}

	private static final String DEFAULT_ERROR_MESSAGE = "Ha ocurrido un error en la aplicación.";

}
