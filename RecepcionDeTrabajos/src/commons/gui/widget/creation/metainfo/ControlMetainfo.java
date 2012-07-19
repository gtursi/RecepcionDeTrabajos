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
 * $Id: ControlMetainfo.java,v 1.3 2007/04/12 12:53:03 cvstursi Exp $
 */

package commons.gui.widget.creation.metainfo;

import org.eclipse.swt.widgets.Composite;

/**
 * Modela la meta informaci�n inherente a un Control visual.
 * 
 * @author Jonathan Chiocchio
 * @version $Revision: 1.3 $ $Date: 2007/04/12 12:53:03 $
 */

public class ControlMetainfo {

	public ControlMetainfo(Composite composite, String labelKey, boolean readOnly) {
		super();
		this.composite = composite;
		this.labelKey = labelKey;
		this.readOnly = readOnly;
	}

	public Composite composite;

	public String labelKey;

	public boolean readOnly = false;

	public void restoreDefaults() {
		this.labelKey = null;
	}

	protected ControlMetainfo() {
		super();
	}

	public static void setValues(ControlMetainfo instance, Composite composite, String labelKey,
			boolean readOnly) {
		instance.composite = composite;
		instance.labelKey = labelKey;
		instance.readOnly = readOnly;
	}

}
