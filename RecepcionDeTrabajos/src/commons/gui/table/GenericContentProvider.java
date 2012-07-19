/**
 *
 */
package commons.gui.table;

import java.util.Collection;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 *
 * @author Gabriel Tursi
 *
 * @param <T>
 */
public class GenericContentProvider implements IStructuredContentProvider {

	public Object[] getElements(Object elements) {
		Object[] result;
		if(elements instanceof Collection){
			result = ((Collection) elements).toArray();
		} else {
			throw new IllegalArgumentException("Tipo de dato no soportado por GenericTable: "
					+ elements.getClass().getName());
		}
		return result;
	}

	public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		// NOTE: Unsupported
	}

	public void dispose() {
		//nothing to do
	}


}