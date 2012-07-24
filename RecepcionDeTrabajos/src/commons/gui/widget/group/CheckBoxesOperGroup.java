package commons.gui.widget.group;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import commons.gui.widget.creation.binding.FakeBinding;
import commons.gui.widget.creation.metainfo.BooleanFieldMetainfo;

/**
 * Modela un grupo con checkboxs.
 */
public class CheckBoxesOperGroup<T> extends SimpleGroup {

	public CheckBoxesOperGroup(Composite parent, String groupName, boolean readOnly,
			int numColumns, final Collection<T> subconjunto,
			Iterable<IdentifiableElement<T>> conjunto) {

		super(parent, groupName, readOnly, numColumns);

		for (IdentifiableElement<T> elemento : conjunto) {
			final T element = elemento.getModel();

			boolean check = false;
			Iterator<T> iterator = subconjunto.iterator();
			while (iterator.hasNext()) {
				Object e = iterator.next();
				if (e.toString().equals(element.toString())) {
					check = true;
					break;
				}
			}

			FakeBinding binding = new FakeBinding(check, element.toString()) {

				@Override
				public void doProcess(String agregar) {
					boolean add = Boolean.parseBoolean(agregar);
					if (add) {
						subconjunto.add(element);
					} else {
						for (Iterator<T> iter = subconjunto.iterator(); iter.hasNext();) {
							Object s = iter.next();
							if (s.toString().equals(element.toString())) {
								iter.remove();
							}
						}
					}
				}
			};

			buttons.put(
					elemento.getDescription(),
					new BooleanFieldMetainfo().composite(this.getSwtGroup())
							.label(elemento.getDescription()).readOnly(readOnly).binding(binding)
							.create());
		}
	}

	public Map<String, Button> getButtons() {
		return buttons;
	}

	private final Map<String, Button> buttons = new HashMap<String, Button>();
}