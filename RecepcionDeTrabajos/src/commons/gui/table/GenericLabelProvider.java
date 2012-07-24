package commons.gui.table;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Text;

import commons.logging.AppLogger;
import commons.util.ClassUtils;
import commons.util.DateUtils;

class GenericLabelProvider<T> implements ITableLabelProvider {

	GenericLabelProvider(Field[] fields, ColumnInfo[] columnsInfo) {
		super();
		this.m_fields = fields;
		this.columnsInfo = columnsInfo;
	}

	public String getColumnText(Object element, int index) {
		String result;
		if (m_fields == null) {
			result = getColumnTextFromObjectArray(element, index);
		} else {
			result = getColumnTextFromObject(element, index);
		}
		return result;
	}

	private String getColumnTextFromObject(Object element, int index) {
		String value = "";
		if ((m_fields != null) && (m_fields.length != 0)) {
			try {
				Object obj = null;
				if (m_fields[index] == null) {
					obj = ClassUtils.getObject(element, columnsInfo[index].fieldName);
				} else {
					obj = m_fields[index].get(element);
				}
				value = getObjectAsString(obj);
			} catch (Exception exc) {
				AppLogger.getLogger().log(
						Level.SEVERE,
						"Class: " + element.getClass().getCanonicalName() + ", Field index: "
								+ index + "", exc);
			}
		}
		return value;
	}

	public String getColumnTextFromObjectArray(Object element, int index) {
		Object[] list = (Object[]) element;

		String value = "";
		try {
			index = index + 1; // pq en la primera posicion esta el id de persistencia
			if (list[index] != null) {
				Object obj = list[index];
				value = getObjectAsString(obj);
			}
		} catch (Exception exc) {
			String msg = "Field index: " + index;
			if (element != null) {
				msg = "Class: " + element.getClass().getCanonicalName() + ", " + msg;
			}
			AppLogger.getLogger().log(Level.SEVERE, msg, exc);
		}
		return value;
	}

	private String getObjectAsString(Object obj) {
		String value = "";
		if (obj != null) {
			if (obj instanceof Calendar) {
				Calendar cal = (Calendar) obj;
				value = DateUtils.formatCalendarAsDateTime(cal);
			} else if (obj instanceof Date) {
				value = DateUtils.formatDate((Date) obj);
			} else {
				value = obj.toString();
				value = removeMultilines(value);
			}
		}
		return value;
	}

	private String removeMultilines(String value) {
		String result = value;
		if (result != null) {
			result = result.replaceAll(Text.DELIMITER, " ");
		}
		return result;
	}

	public void addListener(ILabelProviderListener arg0) {
		// NOTE: Unsupported
	}

	public void removeListener(ILabelProviderListener arg0) {
		// NOTE: Unsupported
	}

	public boolean isLabelProperty(Object arg0, String arg1) {
		// NOTE: Unsupported
		return false;
	}

	public Image getColumnImage(Object arg0, int arg1) {
		// NOTE: Unsupported
		return null;
	}

	public void dispose() {
		// NOTE: Already implemented by IStructuredContentProvider
	}

	private final Field[] m_fields;

	private final ColumnInfo[] columnsInfo;

}