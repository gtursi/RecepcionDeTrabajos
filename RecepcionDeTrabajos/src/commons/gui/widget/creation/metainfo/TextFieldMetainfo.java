package commons.gui.widget.creation.metainfo;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import commons.gui.util.PageHelper;
import commons.gui.util.TextFieldHelper;
import commons.gui.widget.creation.TextFieldListenerType;
import commons.gui.widget.creation.binding.Binding;

/**
 * Modela la meta-información necesaria para crear un Campo de Texto.<br>
 * Nota: gracias al uso del objeto "valueMetaInfo", notar que es posible crear un Text field sin necesidad de atarlo a
 * ningún modelo.
 *
 * @author Gabriel Tursi
 */
public class TextFieldMetainfo extends ControlMetainfo {

	private static TextFieldMetainfo instance = new TextFieldMetainfo();

	public static TextFieldMetainfo create(Composite composite, String labelKey, StringValueMetaInfo valueMetaInfo,
			boolean readOnly, Integer maxLength, boolean multiline) {
		ControlMetainfo.setValues(instance, composite, labelKey, readOnly);
		instance.bindingInfo = valueMetaInfo;
		if (maxLength != null) {
			instance.maxLength = maxLength;
		} else {
			instance.maxLength = DEFAULT_MAX_LENGTH;
		}
		instance.multiline = multiline;
		instance.listeners = new HashSet<TextFieldListenerType>();
		instance.visibleSize = DEFAULT_VISIBLE_SIZE;
		return instance;
	}

	public static TextFieldMetainfo create(Composite composite, String labelKey, Binding binding, boolean readOnly,
			Integer maxLength, boolean multiline) {
		ControlMetainfo.setValues(instance, composite, labelKey, readOnly);
		instance.binding = binding;
		instance.maxLength = maxLength;
		instance.multiline = multiline;
		instance.listeners = new HashSet<TextFieldListenerType>();
		return instance;
	}

	public TextFieldMetainfo() {
		super();
		this.applyDefaults();
	}

	public TextFieldMetainfo composite(Composite comp) {
		this.composite = comp;
		return this;
	}

	public TextFieldMetainfo labelKey(String key) {
		this.labelKey = key;
		return this;
	}

	public TextFieldMetainfo label(String aLabel) {
		this.label = aLabel;
		return this;
	}

	public TextFieldMetainfo binding(Binding bindInfo) {
		this.binding = bindInfo;
		return this;
	}

	public TextFieldMetainfo maxLength(int max) {
		this.maxLength = max;
		return this;
	}

	public TextFieldMetainfo visibleSize(int size) {
		this.visibleSize = size;
		return this;
	}

	public TextFieldMetainfo multiline(boolean isMultiline) {
		this.multiline = isMultiline;
		return this;
	}

	public TextFieldMetainfo readOnly(boolean isReadOnly) {
		this.readOnly = isReadOnly;
		return this;
	}

	public TextFieldMetainfo password(boolean isPassword) {
		this.password = isPassword;
		return this;
	}

	public Control create() {
		return TextFieldHelper.createTextFieldB(this);
	}

	@Override
	public void restoreDefaults() {
		super.restoreDefaults();
		applyDefaults();
	}

	/**
	 * Agrega los listenes especificados a la caja de texto. Estos listeners son reseteados una vez creado el campo.
	 *
	 * @param newListeners
	 */
	public TextFieldMetainfo addListeners(TextFieldListenerType... newListeners) {
		for (TextFieldListenerType listener : newListeners) {
			this.listeners.add(listener);
		}
		return this;
	}

	private void applyDefaults() {
		this.readOnly = false;
		this.bindingInfo = null;
		this.binding = null;
		this.maxLength = DEFAULT_MAX_LENGTH;
		this.visibleSize = DEFAULT_VISIBLE_SIZE;
		this.multiline = false;
		this.password = false;
		this.listeners = new HashSet<TextFieldListenerType>();
	}

	/**
	 * Cantidad máxima de carácteres que permite este campo.
	 *
	 * @see Text#setTextLimit(int)
	 */
	public Integer maxLength;

	/**
	 * Ancho del campo de texto en cantidad de letras.
	 *
	 * @see PageHelper#getCantidadDePixels(int)
	 */
	public Integer visibleSize;

	public boolean multiline;

	public String label;

	// FIXME la existencia de este atributo conflictúa con "binding". Decidir por uno de los dos y eliminar el otro?
	public StringValueMetaInfo bindingInfo;

	public Binding binding;

	// Se usa un conjunto para evitar que se apliquen listeners repetidos
	public Set<TextFieldListenerType> listeners;

	public boolean password;

	private static final Integer DEFAULT_VISIBLE_SIZE = 60;

	private static final Integer DEFAULT_MAX_LENGTH = 255;

}