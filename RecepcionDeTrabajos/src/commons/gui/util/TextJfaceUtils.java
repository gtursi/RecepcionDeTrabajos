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
 * $Id: TextJfaceUtils.java,v 1.3 2008/03/17 12:20:07 cvsmdiaz Exp $
 */
package commons.gui.util;

import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.RTFTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;

/**
 *
 * @author Gabriel Tursi
 * @version $Revision: 1.3 $ $Date: 2008/03/17 12:20:07 $
 */
public abstract class TextJfaceUtils {

	public static void copyToClipboard(String textToCopy) {
		Clipboard clipboard = new Clipboard(PageHelper.getDisplay());
		String plainText = textToCopy;
		String rtfText = textToCopy;
		TextTransfer textTransfer = TextTransfer.getInstance();
		RTFTransfer rftTransfer = RTFTransfer.getInstance();

		clipboard.setContents(new String[] { plainText, rtfText }, new Transfer[] { textTransfer,
				rftTransfer });
		clipboard.dispose();
	}

}

