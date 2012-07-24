package commons.gui.widget.creation.metainfo;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author Gabriel Tursi
 */
public abstract class AbstractValueMetaInfo<T> {

	public AbstractValueMetaInfo(Object model, String propertyName) {
		super();
		if (StringUtils.isBlank(propertyName)) {
			throw new IllegalArgumentException("El atributo propertyName no puede ser nulo");
		}
		this.model = model;
		this.propertyName = propertyName;
	}

	public AbstractValueMetaInfo() {
		super();
	}

	public abstract T getValue();

	public Object getModel() {
		return this.model;
	}

	public String getPropertyName() {
		return this.propertyName;
	}

	protected Object model;

	protected String propertyName;

}
