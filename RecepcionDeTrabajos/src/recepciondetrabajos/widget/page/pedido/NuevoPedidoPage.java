package recepciondetrabajos.widget.page.pedido;

import org.eclipse.swt.widgets.Composite;

import recepciondetrabajos.Constants;
import recepciondetrabajos.Labels;
import recepciondetrabajos.domain.Pedido;
import recepciondetrabajos.domain.PedidoItem;
import recepciondetrabajos.widget.table.PedidoItemCheckableEditTable;
import recepciondetrabajos.widget.table.PedidoItemRowSelectionHandlerCommand;

import commons.gui.table.handler.RowSelectionHandler;
import commons.gui.util.LabelHelper;
import commons.gui.widget.page.BasePreferencesPage;

public class NuevoPedidoPage extends BasePreferencesPage<Pedido> {

	private PedidoItemCheckableEditTable itemsTable;

	private final RowSelectionHandler<PedidoItem, PedidoItemRowSelectionHandlerCommand> rowSelectionHandler

	= new RowSelectionHandler<PedidoItem, PedidoItemRowSelectionHandlerCommand>(PedidoItem.class,
			new PedidoItemDialog(), new PedidoItemRowSelectionHandlerCommand(itemsTable));

	public NuevoPedidoPage(Pedido pedido) {
		super(pedido, "Pedido", false);
	}

	@Override
	protected void addFields(Composite parent) {
		Pedido pedido = getModel();
		LabelHelper.createReadOnlyField(parent, pedido.getCliente().toString(), "cliente");

		this.itemsTable = new PedidoItemCheckableEditTable(parent, PedidoItem.class,
				Constants.CONSULTA_PEDIDO_ITEMS, Labels.getLabels(), this.rowSelectionHandler,
				true, super.readOnly);
	}

	@Override
	protected int getNumColumns() {
		return 1;
	}

}