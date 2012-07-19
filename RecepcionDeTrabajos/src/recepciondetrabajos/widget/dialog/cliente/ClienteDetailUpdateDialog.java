/*
 * Licencia de Caja de Valores S.A., Versi�n 1.0
 *
 * Copyright (c) 2006 Caja de Valores S.A.
 * 25 de Mayo 362, Ciudad Aut�noma de Buenos Aires, Rep�blica Argentina
 * Todos los derechos reservados.
 *
 * Este software es informaci�n confidencial y propietaria de Caja de Valores S.A. ("Informaci�n
 * Confidencial"). Usted no divulgar� tal Informaci�n Confidencial y la usar� solamente de acuerdo a
 * los t�rminos del acuerdo de licencia que posee con Caja de Valores S.A.
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