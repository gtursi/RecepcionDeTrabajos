/*
 * $Id: CrudTable.java,v 1.22 2007/08/30 19:29:34 cvsmdiaz Exp $
 */
package commons.gui.table;

import java.util.Properties;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import commons.gui.table.handler.RowSelectionHandler;

/**
 * Modela una tabla con botones para un CRUD (Create Read Update Delete).
 * 
 * @author Gabriel Tursi
 */
public class CrudTable extends GenericTable {

	public CrudTable(final Composite parent, final Class clazz, String tableName,
			Properties labels, final RowSelectionHandler rowSelectionHandler, boolean sorteable,
			boolean readOnly) {
		this(parent, clazz, tableName, labels, rowSelectionHandler, sorteable, readOnly,
				DEFAULT_TABLE_STYLE);
	}

	public CrudTable(final Composite parent, final Class clazz, String tableName,
			Properties labels, final RowSelectionHandler rowSelectionHandler, boolean sorteable,
			boolean readOnly, int style) {
		super(parent, clazz, tableName, labels, rowSelectionHandler.command.findAll(), sorteable,
				style);

		this.readOnly = readOnly;
		buttonsComposite = new Composite(parent, SWT.NONE);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.CENTER;
		buttonsComposite.setLayoutData(gridData);

		if (readOnly) {
			buttonsComposite.setLayout(new GridLayout(1, false));
			viewButton = createButton("Ver", "Ver");
			viewButton
					.addSelectionListener(getViewSelectionAdapter(rowSelectionHandler, viewButton));
			addDoubleClickListener(getReadOnlyDoubleClickListener(rowSelectionHandler));
		} else {
			buttonsComposite.setLayout(new GridLayout(getButtonBarCount(), false));
			createButtonBarAndListeners(parent, rowSelectionHandler);
			getAddButton().addSelectionListener(getAddButtonListener(rowSelectionHandler));
			getAddButton().setEnabled(true);

			// SelectionAdapter that enables/disables buttons
			super.getTable().addSelectionListener(getTableSelectionListener());
		}
		refresh();
	}

	public Button getAddButton() {
		return addButton;
	}

	public Button getEditButton() {
		return editButton;
	}

	public Button getDeleteButton() {
		return deleteButton;
	}

	protected SelectionAdapter getViewSelectionAdapter(
			final RowSelectionHandler rowSelectionHandler, final Button view) {
		return new SelectionAdapter() {

			@Override
			@SuppressWarnings("unchecked")
			public void widgetSelected(SelectionEvent event) {
				int index = getTable().getSelectionIndex();
				rowSelectionHandler.handleView(getElementAt(index));
			}
		};
	}

	protected SelectionAdapter getTableSelectionListener() {
		return new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				Table table = (Table) event.widget;
				boolean enableButtons = false;
				if (table.getSelectionCount() != 0) {
					enableButtons = true;
				}
				enableButtons(enableButtons);
			}
		};
	}

	protected void createButtonBarAndListeners(Composite parent,
			RowSelectionHandler rowSelectionHandler) {
		addButton = createButton("Agregar", "Agregar nuevo");
		editButton = createButton("Editar", "Editar item seleccionado");
		deleteButton = createButton("Eliminar", "Eliminar item seleccionado");

		// SelectionAdapter that enables/disables buttons
		getTable().addSelectionListener(getEnableButtonsListener());

		getEditButton().addSelectionListener(getEditButtonListener(rowSelectionHandler));

		getDeleteButton()
				.addSelectionListener(getDeleteButtonListener(parent, rowSelectionHandler));

		addDoubleClickListener(getDoubleClickListener(rowSelectionHandler));
	}

	protected IDoubleClickListener getDoubleClickListener(
			final RowSelectionHandler rowSelectionHandler) {
		return new IDoubleClickListener() {

			@SuppressWarnings("unchecked")
			public void doubleClick(DoubleClickEvent event) {
				int index = getTable().getSelectionIndex();
				if (rowSelectionHandler.handleUpdate(index, getElementAt(index))) {
					refresh();
					getTable().select(index);
					enableButtons(true);
				}
			}
		};
	}

	protected SelectionAdapter getEnableButtonsListener() {
		return new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				Table table = (Table) event.widget;
				enableButtons(table.getSelectionCount() != 0);
			}
		};
	}

	protected Button createButton(String text, String toolTipText) {
		GridData gridData = new GridData();
		gridData.widthHint = 100;
		Button button = new Button(buttonsComposite, SWT.PUSH | SWT.CENTER);
		button.setText(text);
		button.setToolTipText(toolTipText);
		button.setLayoutData(gridData);
		applyEnabledState(button);
		addAdditionalButton(button);
		return button;
	}

	public Composite getButtonsComposite() {
		return buttonsComposite;
	}

	protected IDoubleClickListener getReadOnlyDoubleClickListener(
			final RowSelectionHandler rowSelectionHandler) {
		return new IDoubleClickListener() {

			public void doubleClick(DoubleClickEvent event) {
				viewButton.notifyListeners(SWT.Selection, null);
			}
		};
	}

	protected SelectionAdapter getAddButtonListener(final RowSelectionHandler rowSelectionHandler) {
		return new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent event) {
				if (rowSelectionHandler.handleCreate()) {
					setInput(rowSelectionHandler.command.findAll());
					refresh();
					enableButtons(true);
					getTable().setSelection(0);
					getTable().showSelection();
					getTable().notifyListeners(SWT.Selection, null);
				}
			}
		};
	}

	protected SelectionAdapter getEditButtonListener(final RowSelectionHandler rowSelectionHandler) {
		return new SelectionAdapter() {

			@Override
			@SuppressWarnings("unchecked")
			public void widgetSelected(SelectionEvent event) {
				int index = getTable().getSelectionIndex();
				if (rowSelectionHandler.handleUpdate(index, getElementAt(index))) {
					setInput(rowSelectionHandler.command.findAll());
					refresh();
					getTable().setSelection(index);
					getTable().showSelection();
					getTable().notifyListeners(SWT.Selection, null);
					enableButtons(true);
				}
			}
		};
	}

	protected SelectionAdapter getDeleteButtonListener(final Composite parent,
			final RowSelectionHandler rowSelectionHandler) {
		return new SelectionAdapter() {

			@SuppressWarnings("unchecked")
			@Override
			public void widgetSelected(SelectionEvent event) {
				int index = getTable().getSelectionIndex();
				if ((index != -1)
						&& MessageDialog.openQuestion(parent.getShell(), "Confirmar eliminaci�n",
								"�Confirma que desea eliminar el elemento seleccionado?")) {

					// Nota: Es necesario hacer esto por el ordenamiento
					rowSelectionHandler.command.delete(getElementAt(index));
					setInput(rowSelectionHandler.command.findAll());
					refresh();
					index = (index < getTable().getItemCount()) ? index
							: getTable().getItemCount() - 1;
					if (index != -1) {
						getTable().select(index);
					}
					boolean enableButtons = index != -1;
					enableButtons(enableButtons);
				}
			}
		};
	}

	public void removeAddButton() {
		if (!readOnly) {
			super.removeAdditionalButton(addButton);
			addButton.dispose();
		}
	}

	public void removeEditButton() {
		if (!readOnly) {
			removeAdditionalButton(editButton);
			editButton.dispose();
		}
	}

	public void removeDeleteButton() {
		if (!readOnly) {
			removeAdditionalButton(deleteButton);
			deleteButton.dispose();
		}
	}

	@Override
	public void enableButtons(boolean enableButtons) {
		super.enableButtons(enableButtons);
		if ((addButton != null) && !addButton.isDisposed()) {
			addButton.setEnabled(true);
		}
	}

	protected int getButtonBarCount() {
		return 3;
	}

	private Button addButton;

	protected boolean readOnly;

	protected Button editButton;

	protected Button deleteButton;

	protected Button viewButton;

	private final Composite buttonsComposite;
}