package commons.gui.table.handler;

import java.util.logging.Level;

import commons.gui.Openable;
import commons.gui.command.RowSelectionHandlerCommand;
import commons.logging.AppLogger;
import commons.util.ClassUtils;

public class RowSelectionHandler<T extends Object, COMMAND extends RowSelectionHandlerCommand<T>> {

	public RowSelectionHandler(Class<T> aClass, Openable<T> dialog, COMMAND command) {
		super();
		this.command = command;
		this.clazz = aClass;
		this.dialog = dialog;
	}

	public boolean handleView(T element) {
		return dialog.open(element);
	}

	public boolean handleCreate() {
		boolean handleCreate = false;
		T element;
		try {
			element = ClassUtils.newInstance(clazz);
			if (dialog.open(element)) {
				command.store(element);
				handleCreate = true;
			}
		} catch (Exception e) {
			AppLogger.getLogger().log(Level.SEVERE, e.getMessage(), e);
		}
		return handleCreate;
	}

	public boolean handleUpdate(int rowIndex, T element) {
		boolean handleUpdate = false;
		element = command.cloneElement(element);
		if (dialog.open(element)) {
			command.update(rowIndex, element);
			handleUpdate = true;
		}
		return handleUpdate;
	}

	public COMMAND command;

	private final Class<T> clazz;

	public Openable<T> dialog;
	
}