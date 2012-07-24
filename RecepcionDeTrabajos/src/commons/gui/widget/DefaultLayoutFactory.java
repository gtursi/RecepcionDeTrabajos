package commons.gui.widget;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;

public abstract class DefaultLayoutFactory {

	public static void setDefaultGridLayout(Composite composite) {
		setDefaultGridLayout(composite, 2);
	}

	public static void setDefaultGridLayout(Composite composite, int numColumns, GridData gridData) {
		boolean makeColumnsEqualWidth = false;
		GridLayout layout = new GridLayout(numColumns, makeColumnsEqualWidth);
		layout.marginWidth = 5;
		layout.marginHeight = 5;
		layout.verticalSpacing = 6;
		composite.setLayout(layout);
		composite.setLayoutData(gridData);
	}

	public static void setDefaultGridLayout(Composite composite, int numColumns) {
		DefaultLayoutFactory.setDefaultGridLayout(composite, numColumns, getDefaultGridData());
	}

	private static GridData getDefaultGridData() {
		return new GridData(DEFAULT_GRID_DATA_STYLE);
	}

	public static void setDefaultRowLayout(Composite composite) {
		RowLayout layout = new RowLayout(SWT.HORIZONTAL);
		layout.marginWidth = 5;
		layout.marginHeight = 10;
		throw new RuntimeException("Metodo no implementado");
	}

	public static final int DEFAULT_GRID_DATA_STYLE = GridData.GRAB_HORIZONTAL
			| GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_BEGINNING;

}
