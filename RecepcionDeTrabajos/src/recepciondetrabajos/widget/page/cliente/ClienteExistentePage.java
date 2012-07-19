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
 * $Id: ClienteExistentePage.java,v 1.4 2010/12/30 16:38:48 cvsmvera Exp $
 */
package recepciondetrabajos.widget.page.cliente;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import recepciondetrabajos.domain.Cliente;

import commons.gui.util.LabelHelper;
import commons.gui.util.TextFieldHelper;
import commons.gui.widget.creation.metainfo.StringValueMetaInfo;
import commons.gui.widget.creation.metainfo.TextFieldMetainfo;
import commons.gui.widget.page.BasePreferencesPage;

/**
 * Página que muestra la información de un cliente ya existente
 * 
 * @author Gabriel Tursi
 * @version $Revision: 1.4 $ $Date: 2010/12/30 16:38:48 $
 */
public class ClienteExistentePage extends BasePreferencesPage<recepciondetrabajos.domain.Cliente> {

	public ClienteExistentePage(Cliente cliente) {
		super(cliente, "Información del Cliente", false);
	}

	@Override
	protected void addFields(Composite parent) {
		Cliente cliente = this.getModel();
		String codigo = cliente.getCodigo() == null ? "-" : cliente.getCodigo().toString();
		LabelHelper.createReadOnlyField(parent, String.valueOf(codigo), "codigo");
		createText(parent, cliente, "denominacion", cliente.getDenominacion());
		createText(parent, cliente, "direccion", cliente.getDireccion());
		createText(parent, cliente, "email", cliente.getEmail());
	}

	private Text createText(Composite parent, Cliente cliente, String label, String value) {
		value = StringUtils.isBlank(value) ? " - " : value;
		TextFieldMetainfo metainfo = TextFieldMetainfo.create(parent, label,
				new StringValueMetaInfo(cliente, label), false, 100, false);
		return (Text) TextFieldHelper.createTextField(metainfo);
	}

}
