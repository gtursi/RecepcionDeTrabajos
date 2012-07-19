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
 * $Id: GuiStyle.java,v 1.9 2009/04/16 20:09:19 cvsgalea Exp $
 */

package commons.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;

/**
 * Clase que posee información sobre estilos de la GUI.
 * @author Jonathan Chiocchio
 * @version $Revision: 1.9 $ $Date: 2009/04/16 20:09:19 $
 */

public abstract class GuiStyle {

	public static final int DEFAULT_TEXTBOX_STYLE = SWT.BORDER;

	public static final int DEFAULT_MULTILINE_TEXTBOX_STYLE = SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL;

	public static final int DEFAULT_MULTILINE_TEXTBOX_MINIMUN_LINES = 3;
	
	public static final int DEFAULT_GRID_DATA_STYLE = GridData.FILL_HORIZONTAL;

	public static final int PASSWORD_TEXTBOX_STYLE = SWT.BORDER | SWT.PASSWORD;

	// TODO Hacer que la precision sea configurable usando preferences
	public static final int DEFAULT_SCALE = 2;
	
}
