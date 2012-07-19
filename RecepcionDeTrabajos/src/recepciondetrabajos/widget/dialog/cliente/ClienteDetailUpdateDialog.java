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
package recepciondetrabajos.widget.dialog.cliente;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import recepciondetrabajos.domain.Cliente;
import recepciondetrabajos.service.ClienteService;
import recepciondetrabajos.widget.page.cliente.ClienteExistentePage;

import commons.gui.widget.dialog.BasePreferenceDialog;

/**
 * 
 * @author Gabriel Tursi
 * @version $Revision: 1.19 $ $Date: 2011/01/03 14:18:41 $
 */
public class ClienteDetailUpdateDialog extends BasePreferenceDialog {

	public ClienteDetailUpdateDialog(Cliente cliente, boolean readOnly) {
		super(null, "Cliente", readOnly);
		this.cliente = cliente;
	}

	@Override
	protected Control createContents(Composite parent) {

		ClienteExistentePage clientePage = new ClienteExistentePage(cliente);
		addNode(null, "clientePage", clientePage);

		return super.createContents(parent);
	}

	@Override
	protected boolean performOK() {
		ClienteService.persistir(cliente);
		return true;
	}

	private final Cliente cliente;

}