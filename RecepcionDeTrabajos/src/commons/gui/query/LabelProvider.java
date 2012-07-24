package commons.gui.query;

import java.lang.reflect.Field;
import java.util.logging.Level;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;

import recepciondetrabajos.Labels;

import commons.logging.AppLogger;
import commons.util.ClassUtils;

public class LabelProvider implements ITableLabelProvider {

	public Image getColumnImage(Object arg0, int arg1) {
		return null;
	}

	public String getColumnText(Object instance, int columnIndex) {
		String propKey = String.valueOf(columnIndex);
		String fieldName = Labels.getProperty(propKey);
		Field field = ClassUtils.getField(instance.getClass(), fieldName);
		String value = "<ERROR>";
		Exception thrownException = null;
		try {
			value = (String) field.get(instance);
		} catch (IllegalArgumentException ex) {
			thrownException = ex;
		} catch (IllegalAccessException ex) {
			thrownException = ex;
		}
		if (thrownException != null) {
			AppLogger.getLogger().log(Level.SEVERE, null, thrownException);
		}
		return value;
	}

	public void addListener(ILabelProviderListener arg0) {
		// Vacío
	}

	public void dispose() {
		// Vacío
	}

	public boolean isLabelProperty(Object arg0, String arg1) {
		return false;
	}

	public void removeListener(ILabelProviderListener arg0) {
		// Vacío
	}
}
