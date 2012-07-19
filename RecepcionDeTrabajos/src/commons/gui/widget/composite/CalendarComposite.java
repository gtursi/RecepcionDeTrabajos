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
 * $Id: CalendarComposite.java,v 1.5 2008/05/19 18:33:50 cvstursi Exp $
 */

package commons.gui.widget.composite;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import commons.gui.GuiStyle;
import commons.gui.util.LabelHelper;
import commons.gui.util.ListenerHelper;
import commons.gui.util.PageHelper;
import commons.gui.widget.dialog.SWTCalendarDialog;
import commons.util.ClassUtils;
import commons.util.DateUtils;

/**
 * Modela un calendario.
 * 
 * @author Jonathan Chiocchio
 * @version $Revision: 1.5 $ $Date: 2008/05/19 18:33:50 $
 */

public class CalendarComposite extends SimpleComposite {

	public CalendarComposite(Composite parent, final Object instance, final String nombreAtributo, boolean readOnly) {
		super(parent, readOnly, 2);
		this.instance = instance;
		this.nombreAtributo = nombreAtributo;
		Object selection = ClassUtils.getObject(instance, nombreAtributo);
		if (selection != null) {
			this.previousSelectedDate = getCalendar(selection);
		}

		if (readOnly) {
			this.createLabel(this);
		} else {
			this.createTextBox(this);
			this.createChangeDateButton(this);
		}
	}

	public Text getCalendarTextControl() {
		return this.calendarText;
	}

	private Calendar getCalendar(Object selection) {
		Calendar result = null;
		if (selection instanceof Calendar) {
			result = (Calendar) selection;
		} else if (selection instanceof Date) {
			result = DateUtils.getDateAsCalendar((Date) selection);
		}
		return result;
	}

	private void createLabel(CalendarComposite composite) {
		String value = ClassUtils.getValue(instance, nombreAtributo);
		LabelHelper.createValue(composite, value);
	}

	private void createTextBox(SimpleComposite parent) {
		this.calendarText = new Text(parent, GuiStyle.DEFAULT_TEXTBOX_STYLE);
		this.calendarText.setTextLimit(DateUtils.DATE_FORMAT.length());
		if (instance != null) {
			this.calendarText.setText(ClassUtils.obtenerValor(instance, nombreAtributo));
		}
		ListenerHelper.addDateKeyListener(this.calendarText);
		ListenerHelper.addDateModifyListener(this.calendarText);
		this.calendarText.addFocusListener(getFocusLostListener());
	}

	private FocusListener getFocusLostListener() {
		FocusListener focusListener = new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String text = calendarText.getText();
				if (calendarText.getForeground().equals(PageHelper.getInvalidColor()) || StringUtils.isBlank(text)) {
					ClassUtils.setValueByReflection(instance, nombreAtributo, null);
				} else {
					ClassUtils.setDateByReflection(instance, nombreAtributo, text);
				}
			}
		};
		return focusListener;
	}

	private void createChangeDateButton(SimpleComposite parent) {
		this.changeDateButton = new Button(parent, SWT.ARROW | SWT.RIGHT);

		this.changeDateButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				Point dialogLocation = PageHelper.getDisplay().map(changeDateButton, null, 0, 0);
				Calendar today = DateUtils.getCurrentDatetimeAsCalendar();
				SWTCalendarDialog calendarDialog = new SWTCalendarDialog(getShell(), dialogLocation,
						previousSelectedDate, today);

				if (calendarDialog.open() == Window.OK) {
					if (instance != null) {
						ClassUtils.setDateByReflection(instance, nombreAtributo, calendarDialog);
					}
					calendarText.setText(DateUtils.formatCalendar(calendarDialog.getSelectedCalendar()));
				}
			}
		});
	}

	@Override
	protected void applyLayout() {
		GridData gridData = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
		super.setLayoutData(gridData);
		GridLayout layout = new GridLayout(3, false);
		layout.marginLeft = 0;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		super.setLayout(layout);
	}

	private Object instance;

	private String nombreAtributo;

	private Calendar previousSelectedDate;

	private Text calendarText;

	private Button changeDateButton;

}