package recepciondetrabajos.widget.table;

import java.util.Properties;

import org.eclipse.swt.widgets.Composite;

import recepciondetrabajos.domain.PedidoItem;

import commons.gui.table.CrudTable;
import commons.gui.table.handler.RowSelectionHandler;

public class PedidoItemCRUDTable extends CrudTable {

	public PedidoItemCRUDTable(
			Composite parent,
			Class clazz,
			String tableName,
			Properties labels,
			RowSelectionHandler<PedidoItem, PedidoItemRowSelectionHandlerCommand> rowSelectionHandler,
			boolean sorteable, boolean readOnly) {
		super(parent, clazz, tableName, labels, rowSelectionHandler, sorteable, readOnly);
		this.readOnly = readOnly;
	}

}