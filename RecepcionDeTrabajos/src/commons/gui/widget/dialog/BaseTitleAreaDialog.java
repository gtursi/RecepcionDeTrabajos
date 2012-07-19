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
 * $Id: BaseTitleAreaDialog.java,v 1.2 2007/04/16 18:04:28 cvschioc Exp $
 */
package commons.gui.widget.dialog;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Clase Base de la que extienden todos los di�logos. Esto permite implementar m�todos comunes 
 * a todos los di�logos desarrollados en Caja de Valores, manteniendo toda la funcionalidad de 
 * un {@link TitleAreaDialog}
 * 
 * 
 * @author Gabriel Tursi
 * @version $Revision: 1.2 $ $Date: 2007/04/16 18:04:28 $
 */
public class BaseTitleAreaDialog extends TitleAreaDialog {

	public BaseTitleAreaDialog(Shell parentShell) {
		super(parentShell);
	}
	
	protected void setNotNullValidationListener(final Text control) {
		control.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent event) {
				boolean estaEnBlanco = StringUtils.isBlank(control.getText()); 
				if (estaEnBlanco) {
					Button botonAceptar = BaseTitleAreaDialog.super.getButton(0); 
					botonAceptar.setEnabled(false);
					BaseTitleAreaDialog.this.setErrorMessage("El campo no puede ser nulo");
				}
			}

			public void focusLost(FocusEvent event) {
				BaseTitleAreaDialog.this.setErrorMessage(null);
			}
		});

		control.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				boolean estaEnBlanco = StringUtils.isBlank(control.getText());
				if (estaEnBlanco) {
					BaseTitleAreaDialog.super.getButton(0).setEnabled(false);
					BaseTitleAreaDialog.this.setErrorMessage("El campo no puede ser nulo");
				} else {
					BaseTitleAreaDialog.super.getButton(0).setEnabled(true);
					BaseTitleAreaDialog.this.setErrorMessage(null);
				}
			}
		});
	}
}