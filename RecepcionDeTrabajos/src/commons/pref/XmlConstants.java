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
 * $Id: XmlConstants.java,v 1.5 2007/05/28 17:17:04 cvstursi Exp $
 */
package commons.pref;

/**
 * Definimos constantes utilizadas en el xml de preferencias
 * @author Margarita Buriano
 * @version $Revision: 1.5 $ $Date: 2007/05/28 17:17:04 $
 */
public class XmlConstants {

	// Elementos primarios del xml(hijos directos de root)
	public static final String TABLES = "tables";

	// Elementos secundarios
	public static final String TABLE = "table";

	public static final String COLUMN = "column";

	// Atributos
	public static final String WIDTH = "width";

	public static final String ALIGNMENT = "alignment";

	public static final String NAME = "name";

	public static final String ORDER_BY = "order";

}
