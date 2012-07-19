/*
 * $Id: Binding.java,v 1.5 2009/09/16 19:15:41 cvsgalea Exp $
 *
 * Copyright (c) 2003 Caja de Valores S.A.
 * 25 de Mayo 362, Buenos Aires, Rep�blica Argentina.
 * Todos los derechos reservados.
 *
 * Este software es informaci�n confidencial y propietaria de Caja de Valores
 * S.A. ("Informaci�n Confidencial"). Usted no divulgar� tal Informaci�n
 * Confidencial y solamente la usar� conforme a los terminos del Acuerdo que Ud.
 * posee con Caja de Valores S.A.
 */

package commons.gui.widget.creation.binding;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import commons.gui.form.Publisher;
import commons.gui.form.Subscriber;

/**
 * @author Rodrigo Alonso
 * @version $Revision: 1.5 $
 */

public interface Binding extends Publisher, Subscriber{

	boolean getEnabled();
	void setEnabled(boolean enabled);
	void setVisible(boolean enabled);

	String getValue();
	
	void bind(final Control control);
	void bind(final Label label);
	void bind(final Text text);
	void bind(final Combo combo, final boolean useEnums);
	void bind(final Button button);

	String EMPTY_ITEM = "";
}