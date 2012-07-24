package commons.gui.util;

import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class PageHelper {

	/**
	 * Configura PageHelper.
	 * 
	 * @param mainShell
	 *            shell de la ventana Principal de la aplicación
	 * @return
	 */
	public static void init(Shell mainShell) {
		GC graphicsContext = new GC(mainShell);
		graphicsContext.setFont(JFaceResources.getDialogFont());
		fontMetrics = graphicsContext.getFontMetrics();
		initFonts();
	}

	private static void initFonts() {
		fontRegistry = new FontRegistry(getDisplay());
		fontRegistry.put("non-editable", new FontData[] { new FontData("Arial", 9, SWT.BOLD) });
	}

	public static Font getNonEditableFont() {
		return fontRegistry.get("non-editable");
	}

	/**
	 * @param width
	 * @param height
	 * @return
	 */
	public static Rectangle getCenterLocation(int width, int height) {
		Rectangle screenSize = Display.getDefault().getBounds();
		int xCoord = (screenSize.width - width) / 2;
		int yCoord = (screenSize.height - height) / 2;
		return new Rectangle(xCoord, yCoord, width, height);
	}

	public static int getCantidadDePixels(int cantChars) {
		return cantChars * getPixelsPerChar();
	}

	public static int getPixelsPerChar() {
		return fontMetrics.getAverageCharWidth() + 2;
	}

	public static int getHeightPerChars(int cantChars) {
		return (fontMetrics.getHeight() + 2) * cantChars;
	}

	public static int getMinimunCharHeight() {
		return fontMetrics.getHeight();
	}

	public static Display getDisplay() {
		return Display.getDefault();
	}

	public static Color getValidColor() {
		return getDisplay().getSystemColor(SWT.COLOR_BLACK);
	}

	public static Color getInvalidColor() {
		return getDisplay().getSystemColor(SWT.COLOR_RED);
	}

	public static Color getWidgetBackgroundColor() {
		return getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
	}

	private static FontMetrics fontMetrics;

	private static FontRegistry fontRegistry;

}