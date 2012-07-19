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
 * $Id: Preferences.java,v 1.10 2007/05/28 17:17:04 cvstursi Exp $
 */
package commons.pref;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import commons.gui.table.ColumnInfo;

/**
 * Esta clase representa un arbol de objetos din�micos con acceso r�pido a objetos previamente
 * accedidos
 * @author Margarita Buriano
 * @version $Revision: 1.10 $ $Date: 2007/05/28 17:17:04 $
 */
public class Preferences extends XMLMemento {

	/**
	 * @param document XML de preferencias por default
	 * @param element ???
	 */
	public Preferences(Document document, Element element, Preferences defaultUserPreferences) {
		super(document, element);
		this.defaultUserPreferences = defaultUserPreferences;
		initialize();
	}

	/**
	 * Establece nueva informacion sobre una columna de una tabla
	 * @param tableName
	 * @param info
	 */
	public void setColumnInfo(String tableName, ColumnInfo columnInfo) {
		IMemento root = this.getRootOfElement(XmlConstants.TABLES);
		PreferencesElementHelper.modifyColumnInfo(root, tableName, columnInfo);
	}
	
	/**
	 * Establece nueva informacion sobre una tabla
	 * @param tableName
	 * @param ordeColumnName
	 */
	public void setTableInfo(String tableName, String orderColumnName) {
		IMemento root = this.getRootOfElement(XmlConstants.TABLES);
		PreferencesElementHelper.modifyTableInfo(root, tableName, orderColumnName);
	}

	public TableInfo getTableInfo(String tableName) {
		TableInfo tableInfo;
		if (alreadyExistInfo(tableName)) {
			tableInfo = (TableInfo) this.getInfo(tableName);
		} else {
			tableInfo = PreferencesElementHelper.getTableInfo(this.getChild(XmlConstants.TABLES),
					tableName);
			this.putForFastAccess(tableName, tableInfo);			
		}
		return tableInfo;
	}

	private void mergeTables() {
		List<TableInfo> defaultUserPreferencesTables = defaultUserPreferences.getTables();
		for (TableInfo defaultTableInfo : defaultUserPreferencesTables) {
			PreferencesElementHelper.updateDefaultableInfo(this.getRootOfElement(XmlConstants.TABLES), defaultTableInfo);
			putForFastAccess(defaultTableInfo.getName(), defaultTableInfo);//TODO hace falta?
		}
		
	}

	private List<TableInfo> getTables() {
		List<TableInfo> result = new ArrayList<TableInfo>();
		IMemento tablesElement = this.getChild("tables");
		IMemento[] tables = tablesElement.getChildren("table");
		for (IMemento aTable : tables) {
			TableInfo table = PreferencesElementHelper.getTableInfo(tablesElement, aTable
					.getString(XmlConstants.NAME));
			result.add(table);
		}
		return result;
	}

	private Object getInfo(String key) {
		return this.fastAccess.get(key);
	}

	/**
	 * Inicializa informaci�n para el parseo
	 */
	private void initialize() {
		if (defaultUserPreferences != null) {
			mergeTables();
		}
	}

	private void putForFastAccess(String key, Object object) {
		this.fastAccess.put(key, object);
	}

	private boolean alreadyExistInfo(String key) {
		return (this.fastAccess.get(key) != null);
	}	
	
	private final Map<String, Object> fastAccess = new HashMap<String, Object>();

	private final Preferences defaultUserPreferences;
}