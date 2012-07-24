package commons.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public abstract class DateUtils {

	public static final String DATE_FORMAT = "dd/MM/yyyy";

	public static Calendar toCalendar(java.util.Date date) {
		Calendar cal;
		if (date == null) {
			cal = null;
		} else {
			cal = newEpochCalendar();
			cal.setTime(date);
		}
		return cal;
	}

	public static String formatCalendarAsDateTime(Calendar cal) {
		synchronized (dateTimeFormat) {
			return dateTimeFormat.format(cal.getTime());
		}
	}

	public static String formatDateAsDateTime(Date date) {
		synchronized (dateTimeFormat) {
			return dateTimeFormat.format(date);
		}
	}

	public static String formatDate(Date date) {
		synchronized (dateFormat) {
			return dateFormat.format(date);
		}
	}

	public static String formatCalendar(Calendar cal) {
		String result = "";
		if (cal != null) {
			synchronized (dateFormat) {
				result = dateFormat.format(cal.getTime());
			}
		}
		return result;
	}

	public static Calendar parseStringDateAsCalendar(String date) {
		try {
			synchronized (dateTimeFormat) {
				Date time = dateTimeFormat.parse(date);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(time);
				return calendar;
			}
		} catch (ParseException e) {
			throw new RuntimeException("No se pudo parsear la fecha " + date);
		}
	}

	public static String getCurrentDatetimeAsString() {
		return formatCalendarAsDateTime(getCurrentDatetimeAsCalendar());
	}

	// TODO Tomar hora del sistema del servidor
	public static Calendar getCurrentDatetimeAsCalendar() {
		return Calendar.getInstance();
	}

	public static Calendar parseDMY(String stringDate) {
		try {
			synchronized (dateFormat) {
				Date date = dateFormat.parse(stringDate);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				// Para evitar errores por el timezone
				calendar.set(Calendar.HOUR_OF_DAY, 12);
				return calendar;
			}
		} catch (ParseException e) {
			throw new RuntimeException("No se pudo parsear la fecha " + stringDate);
		}
	}

	/**
	 * Dado un <code>Date</code>, retorna un <code>Calendar</code> con una fecha equivalente
	 * 
	 * @param date
	 *            Fecha a convertir
	 * @return Calendar con fecha equivalente al Date recibido
	 */
	public static Calendar getDateAsCalendar(Date date) {
		Calendar result = Calendar.getInstance();
		result.setTime(date);
		return result;
	}

	/**
	 * El texto debe contener exactamente 10 caracteres
	 * 
	 */
	public static boolean isValidDate(String date) {
		boolean result = true;
		if ((date == null) || (date.trim().length() != 10)) {
			result = false;
		} else {
			dateFormat.setLenient(false);
			try {
				dateFormat.parse(date);
			} catch (ParseException e) {
				result = false;
			}
		}
		return result;
	}

	public static Calendar newEpochCalendar() {
		// Se evita usar el constructor default por performance, ya que el mismo
		// obtiene la fecha y hora del sistema (que no utilizamos para nada)
		return new GregorianCalendar(EPOCH_YEAR, EPOCH_MONTH, EPOCH_DAY);
	}

	// TODO Hacer configurables el formato de la fecha usando un preferences
	private static DateFormat dateTimeFormat = new SimpleDateFormat(DATE_FORMAT + " HH:mm");

	private static DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

	public static final int EPOCH_YEAR = 1970;

	public static final int EPOCH_MONTH = 0;

	public static final int EPOCH_DAY = 1;

}