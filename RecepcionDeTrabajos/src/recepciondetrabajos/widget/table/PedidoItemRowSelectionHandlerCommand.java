package recepciondetrabajos.widget.table;

import java.util.Collection;
import java.util.List;

import recepciondetrabajos.domain.PedidoItem;

import commons.gui.command.RowSelectionHandlerCommand;

public class PedidoItemRowSelectionHandlerCommand implements RowSelectionHandlerCommand<PedidoItem> {

	public PedidoItemRowSelectionHandlerCommand(List<PedidoItem> items) {
		this.items = items;
	}

	public PedidoItem store(PedidoItem item) {
		items.add(item);
		return item;
	}

	public PedidoItem update(int rowIndex, PedidoItem item) {
		PedidoItem itemDesactualizado = items.get(rowIndex);
		items.remove(itemDesactualizado);
		items.add(item);
		return item;
	}

	public Collection<PedidoItem> findAll() {
		return items;
	}

	public PedidoItem cloneElement(PedidoItem item) {
		return new PedidoItem(item);
	}

	public void delete(PedidoItem item) {
		items.remove(item);
	}

	private List<PedidoItem> items;
}