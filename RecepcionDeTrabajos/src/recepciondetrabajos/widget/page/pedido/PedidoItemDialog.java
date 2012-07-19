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
 * $Id$
 */
package recepciondetrabajos.widget.page.pedido;

import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import recepciondetrabajos.domain.PedidoItem;

import commons.gui.Openable;
import commons.gui.util.TextFieldHelper;
import commons.gui.widget.creation.TextFieldListenerType;
import commons.gui.widget.creation.metainfo.StringValueMetaInfo;
import commons.gui.widget.creation.metainfo.TextFieldMetainfo;

/**
 * 
 * @author Gabriel Tursi
 * @version $Revision$ $Date$
 */
public class PedidoItemDialog extends TrayDialog implements Openable<PedidoItem> {

	private PedidoItem item;

	private boolean readOnly = false;

	public PedidoItemDialog() {
		super((Shell) null);
	}

	public boolean open(PedidoItem item) {
		this.item = item;
		// setBindings();
		int result = super.open();
		return result == OK;
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Item");
	}

	@Override
	protected Control createDialogArea(Composite parent) {

		TextFieldMetainfo metainfo = TextFieldMetainfo.create(parent, "cantidad",
				new StringValueMetaInfo(item, "cantidad"), this.readOnly, 5, false);
		metainfo.addListeners(TextFieldListenerType.INTEGER_FIELD_LISTENER);
		TextFieldHelper.createTextField(metainfo);

		metainfo = TextFieldMetainfo.create(parent, "detalle", new StringValueMetaInfo(item,
				"detalle"), this.readOnly, 250, true);
		TextFieldHelper.createTextField(metainfo);

		metainfo = TextFieldMetainfo.create(parent, "observaciones", new StringValueMetaInfo(item,
				"observaciones"), this.readOnly, 250, true);
		TextFieldHelper.createTextField(metainfo);
		return parent;
	}

}
