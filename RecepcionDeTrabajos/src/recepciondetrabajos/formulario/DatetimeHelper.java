package recepciondetrabajos.formulario;

import java.util.Calendar;

public class DatetimeHelper {

	public static String getDateAsString() {
		Calendar calendar = Calendar.getInstance();
		return getValueWithLeadingZero(Calendar.DAY_OF_MONTH) + "/"
				+ getValueWithLeadingZero(calendar.get(Calendar.MONTH)) + "/"
				+ calendar.get(Calendar.YEAR);
	}

	private static String getValueWithLeadingZero(int value) {
		return String.format("%02d", value);
	}

}
