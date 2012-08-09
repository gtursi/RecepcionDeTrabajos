package recepciondetrabajos.widget.page.pedido;

import java.util.List;
import java.util.logging.Level;

import org.eclipse.swt.widgets.Composite;

import recepciondetrabajos.Constants;
import recepciondetrabajos.Labels;
import recepciondetrabajos.domain.Pedido;
import recepciondetrabajos.domain.PedidoItem;
import recepciondetrabajos.widget.table.PedidoItemCRUDTable;
import recepciondetrabajos.widget.table.PedidoItemRowSelectionHandlerCommand;

import commons.gui.table.handler.RowSelectionHandler;
import commons.gui.util.LabelHelper;
import commons.gui.widget.page.BasePreferencesPage;
import commons.logging.AppLogger;

public class NuevoPedidoPage extends BasePreferencesPage<Pedido> {

	private RowSelectionHandler<PedidoItem, PedidoItemRowSelectionHandlerCommand> rowSelectionHandler;

	public NuevoPedidoPage(Pedido pedido) {
		super(pedido, "Pedido", false);
	}

	@Override
	protected void addFields(Composite parent) {
		final Pedido pedido = getModel();
		LabelHelper.createReadOnlyField(parent, pedido.getCliente().toString(), "cliente");

		List<PedidoItem> items = pedido.getItems();
		this.rowSelectionHandler = new RowSelectionHandler<PedidoItem, PedidoItemRowSelectionHandlerCommand>(
				PedidoItem.class, new PedidoItemDialog(), new PedidoItemRowSelectionHandlerCommand(
						items)) {

			@Override
			public boolean handleCreate() {
				boolean handleCreate = false;
				PedidoItem item;
				try {
					item = new PedidoItem(pedido);
					if (dialog.open(item)) {
						handleCreate = true;
					}
				} catch (Exception e) {
					AppLogger.getLogger().log(Level.SEVERE, e.getMessage(), e);
				}
				return handleCreate;
			}
		};

		new PedidoItemCRUDTable(parent, PedidoItem.class, Constants.CONSULTA_PEDIDO_ITEMS,
				Labels.getLabels(), this.rowSelectionHandler, false, super.readOnly);
	}

}