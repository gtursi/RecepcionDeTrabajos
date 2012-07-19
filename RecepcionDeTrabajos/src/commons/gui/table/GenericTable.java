/*
 * $Id: GenericTable.java,v 1.27 2010/12/23 14:23:50 cvsmvera Exp $
 */
package commons.gui.table;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import recepciondetrabajos.Labels;




import commons.pref.PreferencesManager;
import commons.pref.TableInfo;
import commons.util.ClassUtils;
import commons.util.SbaStringUtils;

/**
 * Modela una tabla de datos genérica.
 * 
 * @author Gabriel Tursi
 */
public class GenericTable extends TableViewer {

	/**
	 * Permite no especificar ni los labels ni el estilo
	 */
	public GenericTable(Composite parent, Class clazz, String tableName, Object elements,
			boolean sorteable) {
		this(parent, clazz, tableName, Labels.getLabels(), elements, sorteable, DEFAULT_TABLE_STYLE);
	}

	/**
	 * Permite no especificar el estilo
	 */
	public GenericTable(Composite parent, Class clazz, String tableName, Properties labels,
			Object elements, boolean sorteable) {
		this(parent, clazz, tableName, labels, elements, sorteable, DEFAULT_TABLE_STYLE);
	}

	/**
	 * Permite no especificar los labels
	 */
	public GenericTable(final Composite parent, final Class clazz, String tableName,
			Object elements, boolean sorteable, int style) {
		this(parent, clazz, tableName, Labels.getLabels(), elements, sorteable, style);
	}

	/**
	 * 
	 * @param parent
	 *            Composite donde se ubicara la tabla
	 * @param clazz
	 *            Clase que manejara la tabla
	 * @param tableName
	 *            Nombre de la tabla en el descriptor de preferencias
	 * @param labels
	 *            Etiquetas de la tabla
	 * @param elements
	 *            Registros de la tabla
	 * @param sorteable
	 *            indica si la tabla puede ser ordenada o no por campo
	 * @param style
	 *            indica el estilo de la tabla
	 */
	public GenericTable(final Composite parent, final Class clazz, String tableName,
			Properties labels, Object elements, boolean sorteable, int style) {
		super(parent, style);
		parent.setLayout(new GridLayout(1, false));
		setContentProvider(new GenericContentProvider());
		TableInfo tableInfo = PreferencesManager.getInstance().getTableInfo(tableName);
		ColumnInfo[] columnsInfo = tableInfo.getColumnsInfo();
		Field[] fields = getFieldsSubset(clazz, columnsInfo);
		setLabelProvider(new GenericLabelProvider(fields, columnsInfo));
		setInput(elements);

		if (sorteable) {
			this.setSorter(new GenericTableViewerSorter(tableInfo) {

				@Override
				protected Class getOrderedClass() {
					return clazz;
				}
			});
		}

		getTable().setLayoutData(new GridData(GridData.FILL_BOTH));
		getTable().setHeaderVisible(true);
		getTable().setLinesVisible(true);

		TableColumn column;
		ColumnInfo columnInfo;
		for (int i = 0; i < columnsInfo.length; i++) {
			columnInfo = columnsInfo[i];
			column = new TableColumn(getTable(), columnInfo.style);
			if (columnInfo.width > 0) {
				column.setWidth(columnInfo.width);
			}
			column.setText(getLabel(labels, columnInfo.fieldName));
			column.setData(columnInfo.fieldName);
			if (getSorter() != null) {
				final int columnIndex = i;
				column.addSelectionListener(new SelectionColumnAdapter(tableName, columnIndex, this));
				column.addControlListener(new SizeControlAdapter(tableName));
			}
		}

		// SelectionAdapter that enables/disables buttons
		getTable().addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				Table table = (Table) event.widget;
				boolean enableButtons = false;
				if (table.getSelectionCount() != 0) {
					enableButtons = true;
				}
				enableButtons(enableButtons);
			}
		});

		// Si la tabla tiene un único ítem, entonces seleccionarlo
		if (getTable().getItemCount() > 0) {
			getTable().select(0);
		}
		refresh();

		addPostSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				boolean enabled = getSelectedElement() != null;
				enableButtons(enabled);
			}
		});
	}

	public void addAdditionalButton(Button button) {
		additionalButtons.add(button);
		applyEnabledState(button);
	}

	protected void removeAdditionalButton(Button button) {
		additionalButtons.remove(button);
	}

	protected void applyEnabledState(Button additionalButton) {
		boolean enabled = getTable().getSelectionCount() != 0;
		additionalButton.setEnabled(enabled);
	}

	public void dispose() {
		// NOTE: Empty
	}

	protected void enableButtons(boolean enableButtons) {
		for (Button button : additionalButtons) {
			button.setEnabled(enableButtons);
		}
	}

	protected static String getLabel(Properties labels, String fieldName) {
		String key = SbaStringUtils.concat(fieldName, ".columnLabel");
		String label = labels.getProperty(key);
		if (label == null) {
			label = labels.getProperty(fieldName);
		}
		return (label == null) ? "" : label;
	}

	protected static Field[] getFieldsSubset(Class clazz, ColumnInfo[] columnsInfo) {
		Field[] fields = ClassUtils.getFields(clazz);
		ArrayList<Field> fieldsArray = new ArrayList<Field>();
		String fieldName;
		for (ColumnInfo element : columnsInfo) {
			fieldName = element.fieldName;
			if (fieldName.contains(".") || fieldName.contains("()")) {
				// No es un atributo, por lo tanto debo obtenerlo en runtime
				fieldsArray.add(null);
			} else {
				for (Field field : fields) {
					if (fieldName.equals(field.getName())) {
						fieldsArray.add(field);
						break;
					}
				}

			}
		}
		fields = new Field[fieldsArray.size()];
		return fieldsArray.toArray(fields);
	}

	/**
	 * Devuelve el primer elemento seleccionado o null si no hay ningún elemento seleccionado
	 */
	public Object getSelectedElement() {
		Object result = null;
		List selectionFromWidget = this.getSelectionFromWidget();
		if (!selectionFromWidget.isEmpty()) {
			result = selectionFromWidget.get(0);
		}
		return result;
	}

	/**
	 * Devuelve todos los elementos seleccionados
	 * 
	 * @return
	 */
	public List getSelectedElements() {
		return this.getSelectionFromWidget();
	}

	public static final int DEFAULT_TABLE_STYLE = SWT.FULL_SELECTION | SWT.BORDER | SWT.MULTI
			| SWT.V_SCROLL | SWT.H_SCROLL;

	private final List<Button> additionalButtons = new ArrayList<Button>();
}
