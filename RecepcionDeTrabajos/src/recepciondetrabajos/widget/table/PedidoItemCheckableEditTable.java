package recepciondetrabajos.widget.table;

import java.util.Collection;
import java.util.Properties;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;

import recepciondetrabajos.domain.PedidoItem;

import commons.gui.table.handler.RowSelectionHandler;

/**
 * @author Jonathan Chiocchio
 * @version $Revision: 1.4 $ $Date: 2007/07/26 17:53:07 $
 */

public class PedidoItemCheckableEditTable
		extends
		CheckableCrudTable<PedidoItem, RowSelectionHandler<PedidoItem, PedidoItemRowSelectionHandlerCommand>> {

	public PedidoItemCheckableEditTable(
			Composite parent,
			Class clazz,
			String tableName,
			Properties labels,
			RowSelectionHandler<PedidoItem, PedidoItemRowSelectionHandlerCommand> rowSelectionHandler,
			boolean sorteable, boolean readOnly) {
		super(parent, clazz, tableName, labels, rowSelectionHandler, sorteable, readOnly);
		this.readOnly = readOnly;
	}

	/**
	 * Setea la colección de Cuentas Update Data a partir de la cual se determinará si los ítems
	 * deben ser "checkeados" o no.
	 * 
	 * @param items
	 *            la lista de CuentasUD (normalmente proveniente del backend)
	 */
	public void setSubyacentModel(Collection<PedidoItem> items) {
		this.subyacentModel = items;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<PedidoItem> getInput() {
		return (Collection<PedidoItem>) super.getInput();
	}

	@Override
	protected boolean hasToBeChecked(PedidoItem item) {
		boolean result = false;
		if (this.subyacentModel != null) {
			result = this.subyacentModel.contains(item);
		}
		return result;
	}

	@Override
	protected ISelectionChangedListener getCheckedListener() {
		return new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				TableItem[] items = getTable().getItems();
				subyacentModel.clear();
				for (TableItem item : items) {
					if (item.getChecked()) {
						PedidoItem pedidoItem = (PedidoItem) item.getData();
						subyacentModel.add(pedidoItem);
					}
				}
			}
		};
	}

	private Collection<PedidoItem> subyacentModel;

}