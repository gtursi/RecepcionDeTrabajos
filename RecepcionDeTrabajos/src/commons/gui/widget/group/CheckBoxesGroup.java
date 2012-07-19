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
 *
 * $Id: CheckBoxesGroup.java,v 1.11 2009/11/12 17:23:34 cvsmvera Exp $
 */

package commons.gui.widget.group;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import commons.gui.widget.creation.binding.FakeBinding;
import commons.gui.widget.creation.metainfo.BooleanFieldMetainfo;

/**
 * Modela un grupo con checkboxs.
 *
 * @author ralonso
 * @version $Revision: 1.11 $ $Date: 2009/11/12 17:23:34 $
 */

public class CheckBoxesGroup<T> extends SimpleGroup {

	public CheckBoxesGroup(Composite parent, String groupName, boolean readOnly, int numColumns,
			final Collection<T> subconjunto, Iterable<IdentifiableElement<T>> conjunto) {

		super(parent, groupName, readOnly, numColumns);

		for (IdentifiableElement<T> elemento : conjunto) {
			final T element = elemento.getModel();

			FakeBinding binding = new FakeBinding(subconjunto.contains(element), element.toString()) {

				@Override
			 	public void doProcess(String agregar) {
					boolean add = Boolean.parseBoolean(agregar);
					if (add) {
						subconjunto.add(element);
					} else {
						subconjunto.remove(element);
					}
				}
			};
			buttons.put(elemento.getDescription(), new BooleanFieldMetainfo().composite(
					this.getSwtGroup()).label(elemento.getDescription()).readOnly(readOnly)
					.binding(binding).create());
		}
		
	}

	public Map<String, Button> getButtons() {
		return buttons;
	}

	private final Map<String, Button> buttons = new HashMap<String, Button>();
}