/*
 * Licencia de Caja de Valores S.A., Versión 1.0
 *
 * Copyright (c) 2006 Caja de Valores S.A.
 * 25 de Mayo 362, Ciudad Autónoma de Buenos Aires, República Argentina
 * Todos los derechos reservados.
 *
 * Este software es información confidencial y propietaria de Caja de Valores S.A. ("Información
 * Confidencial"). Usted no divulgará tal Información Confidencial y la usará solamente de acuerdo a
 * los términos del acuerdo de licencia que posee con Caja de Valores S.A.
 */

/*
 * $Id: ClienteDetailUpdateDialog.java,v 1.19 2011/01/03 14:18:41 cvsmvera Exp $
 */
package recepciondetrabajos.widget.dialog.pedido;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import recepciondetrabajos.domain.Pedido;
import recepciondetrabajos.service.PedidoService;
import recepciondetrabajos.widget.page.pedido.NuevoPedidoPage;


import commons.gui.widget.dialog.BasePreferenceDialog;

/**
 * 
 * @author Gabriel Tursi
 * @version $Revision: 1.19 $ $Date: 2011/01/03 14:18:41 $
 */
public class NuevoPedidoDialog extends BasePreferenceDialog {

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
		PedidoService.crearPedido(pedido);
		return true;
	}

	private final Pedido pedido;

}