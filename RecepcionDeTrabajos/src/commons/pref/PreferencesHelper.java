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
 * $Id: PreferencesHelper.java,v 1.8 2007/06/28 20:17:35 cvstursi Exp $
 */
package commons.pref;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import commons.logging.AppLogger;

/**
 * @author Margarita Buriano
 * @version $Revision: 1.8 $ $Date: 2007/06/28 20:17:35 $
 */
public abstract class PreferencesHelper {

	public static Preferences readPreferences(Reader prefsInputReader, Preferences preferences) {
		// TODO revisar manejo de excepciones
		String errorMessage = null;
		Exception exception = null;

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder parser = factory.newDocumentBuilder();
			InputSource source = new InputSource(prefsInputReader);
			Document document = parser.parse(source);
			NodeList list = document.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node instanceof Element) {
					return new Preferences(document, (Element) node, preferences);
				}

			}
			prefsInputReader.close();

		} catch (ParserConfigurationException e) {
			exception = e;
			errorMessage = "XMLMemento_parserConfigError";
		} catch (IOException e) {
			exception = e;
			errorMessage = "XMLMemento_ioError";
		} catch (SAXException e) {
			exception = e;
			errorMessage = "XMLMemento_formatError";
		}
		String problemText = null;
		if (exception != null) {
			problemText = exception.getMessage();
		}

		if (StringUtils.isEmpty(problemText)) {
			problemText = errorMessage == null ? "XMLMemento_noElement" : errorMessage;
		}
		AppLogger.getLogger().log(Level.SEVERE,
				"Las preferencias por default están corruptas (" + problemText + ")", exception);
		return null;
	}

	public static int getStyleCode(String style) {
		return ALIGNMENT_CODE_MAP.get(style);
	}

	public static String getStyle(int code) {
		return CODE_ALIGNMENT_MAP.get(code);

	}

	public static final Map<Integer, String> CODE_ALIGNMENT_MAP;

	public static final Map<String, Integer> ALIGNMENT_CODE_MAP;

	static {
		CODE_ALIGNMENT_MAP = new HashMap<Integer, String>();
		ALIGNMENT_CODE_MAP = new HashMap<String, Integer>();
		CODE_ALIGNMENT_MAP.put(SWT.RIGHT, "RIGHT");
		CODE_ALIGNMENT_MAP.put(SWT.LEFT, "LEFT");
		CODE_ALIGNMENT_MAP.put(SWT.CENTER, "CENTER");
		ALIGNMENT_CODE_MAP.put("RIGHT", SWT.RIGHT);
		ALIGNMENT_CODE_MAP.put("LEFT", SWT.LEFT);
		ALIGNMENT_CODE_MAP.put("CENTER", SWT.CENTER);
	}
}