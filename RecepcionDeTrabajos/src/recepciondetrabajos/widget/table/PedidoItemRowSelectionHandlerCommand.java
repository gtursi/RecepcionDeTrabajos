package recepciondetrabajos.widget.table;

import java.util.Collection;

import recepciondetrabajos.domain.PedidoItem;

import commons.gui.command.RowSelectionHandlerCommand;
import commons.gui.table.GenericTable;

/**
 * 
 * @author Jonathan Chiocchio
 * @version $Revision: 1.5 $ $Date: 2007/05/31 16:04:32 $
 */

public class PedidoItemRowSelectionHandlerCommand implements RowSelectionHandlerCommand<PedidoItem> {

	public PedidoItemRowSelectionHandlerCommand(GenericTable pedidoItemsTable) {
		this.pedidoItemsTable = pedidoItemsTable;
	}

	public PedidoItem store(PedidoItem item) {
		pedidoItemsTable.add(item);
		return item;
	}

	@SuppressWarnings("unchecked")
	public PedidoItem update(int rowIndex, PedidoItem item) {
		PedidoItem itemDesactualizado = (PedidoItem) pedidoItemsTable.getElementAt(rowIndex);
		Collection<PedidoItem> items = (Collection<PedidoItem>) pedidoItemsTable.getInput();
		items.remove(itemDesactualizado);
		items.add(item);
		return item;
	}

	@SuppressWarnings("unchecked")
	public Collection<PedidoItem> findAll() {
		Collection<PedidoItem> cuentasUD = null;
		if (this.pedidoItemsTable != null) {
			cuentasUD = (Collection<PedidoItem>) pedidoItemsTable.getInput();
		}
		return cuentasUD;
	}

	public PedidoItem cloneElement(PedidoItem item) {
		return new PedidoItem(item);
	}

	public void delete(PedidoItem item) {
		pedidoItemsTable.remove(item);
	}

	public void setPedidoItemsTable(GenericTable table) {
		this.pedidoItemsTable = table;
	}

	private GenericTable pedidoItemsTable;
}