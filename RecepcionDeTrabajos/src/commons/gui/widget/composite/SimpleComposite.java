package commons.gui.widget.composite;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import commons.gui.widget.DefaultLayoutFactory;

/**
 * Modela un composite básico de depositantes y cuentas.
 */
public class SimpleComposite extends Composite {

	public SimpleComposite(Composite parent, boolean readOnly, int numColumns) {
		super(parent, SWT.NONE);
		this.readOnly = readOnly;
		this.numColumns = numColumns;
		applyLayout();
	}

	/**
	 * Habilita todos los controles que tienen como padre a éste Composite.
	 */
	@Override
	public void setEnabled(boolean enabled) {
		Control[] children = getChildren();
		for (Control element : children) {
			element.setEnabled(enabled);
		}
		super.setEnabled(enabled);
	}

	/**
	 * Provee un layout por default, sobreescribir este método si se desea otro layout.
	 */
	protected void applyLayout() {
		DefaultLayoutFactory.setDefaultGridLayout(this, getNumColumns());
		GridLayout layout = (GridLayout) this.getLayout();
		layout.marginLeft = 0;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
	}

	protected int getNumColumns() {
		return numColumns;
	}

	protected boolean readOnly;

	private final int numColumns;

}
