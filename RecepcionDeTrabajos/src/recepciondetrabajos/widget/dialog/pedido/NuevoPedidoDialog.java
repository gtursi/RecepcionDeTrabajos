package recepciondetrabajos.widget.dialog.pedido;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import recepciondetrabajos.domain.Pedido;
import recepciondetrabajos.formulario.PdfGenerator;
import recepciondetrabajos.service.PedidoService;
import recepciondetrabajos.widget.page.pedido.NuevoPedidoPage;

import commons.gui.widget.dialog.BasePreferenceDialog;

/**
 * 
 * @author Gabriel Tursi
 * @version $Revision: 1.19 $ $Date: 2011/01/03 14:18:41 $
 */
public class NuevoPedidoDialog extends BasePreferenceDialog {

	private Button printButton;

	public NuevoPedidoDialog(Pedido pedido) {
		super(null, "Nuevo Pedido", false);
		this.pedido = pedido;
	}

	@Override
	protected Control createContents(Composite parent) {

		NuevoPedidoPage pedidoPage = new NuevoPedidoPage(pedido);
		addNode(null, "pedidoPage", pedidoPage);

		return super.createContents(parent);
	}

	@Override
	protected boolean performOK() {
		if (pedido.nuevo()) {
			PedidoService.crearPedido(pedido);
		} else {
			PedidoService.actualizarPedido(pedido);
		}

		return true;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		printButton = createButton(parent, 100, "Imprimir", false);
		printButton.addSelectionListener(getPrintButtonSelectionListener());
		super.createButtonsForButtonBar(parent);
	}

	private SelectionListener getPrintButtonSelectionListener() {
		return new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				performOK();
				PdfGenerator.generate(pedido);
			}
		};
	}

	private final Pedido pedido;

}