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
 * $Id: ComboHelper.java,v 1.39 2007/09/27 20:25:13 cvstursi Exp $
 */

package commons.gui.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;

import commons.gui.widget.creation.metainfo.ComboMetainfo;
import commons.gui.widget.creation.metainfo.ComboValuesMetainfo;
import commons.gui.widget.creation.metainfo.StringValueMetaInfo;
import commons.util.ClassUtils;

/**
 * @author Jonathan Chiocchio
 * @version $Revision: 1.39 $ $Date: 2007/09/27 20:25:13 $
 */
public abstract class ComboHelper {

	public static Control createCombo(ComboMetainfo metainfo) {
		final Control result;
		String currentValue = metainfo.valueMetaInfo.getValue();
		if (metainfo.readOnly) {
			currentValue = findDescriptionForCode(metainfo, currentValue);
			result = LabelHelper.createReadOnlyField(metainfo.composite, currentValue,
					metainfo.labelKey);
			if (metainfo.comboValuesMI.useCodedItems()) {
				addCodedItemsData(result, metainfo.comboValuesMI.codedItems);
			}
		} else {
			LabelHelper.createLabel(metainfo.composite, metainfo.labelKey);
			result = new Combo(metainfo.composite, SWT.READ_ONLY);
			Combo combo = (Combo) result;
			completeCombo(combo, metainfo);

			if (StringUtils.isBlank(currentValue)) {
				if (StringUtils.isNotBlank(metainfo.defaultOption)) {
					combo.setText(metainfo.defaultOption);
				}
			} else {
				if (!metainfo.comboValuesMI.useCodedItems()) {
					combo.setText(currentValue);
				}
			}
		}
		metainfo.restoreDefaults();
		return result;
	}

	public static Control createComboB(ComboMetainfo metainfo) {
		final Control result;
		String currentValue = metainfo.bindingInfo.getValue();
		if (metainfo.readOnly) {
			currentValue = findDescriptionForCode(metainfo, currentValue);
			result = LabelHelper.createReadOnlyField(metainfo.composite, currentValue,
					metainfo.labelKey);
			if (metainfo.comboValuesMI.useCodedItems()) {
				addCodedItemsData(result, metainfo.comboValuesMI.codedItems);
			}
		} else {
			LabelHelper.createLabel(metainfo.composite, metainfo.labelKey);
			result = new Combo(metainfo.composite, SWT.READ_ONLY);
			Combo combo = (Combo) result;

			ComboValuesMetainfo comboValuesMetainfo = metainfo.comboValuesMI;
			if (comboValuesMetainfo.useCodedItems()) {
				completeComboData4CodedItems(combo, comboValuesMetainfo.codedItems);
				metainfo.bindingInfo.bind(combo, false);
				String descripcion = (String) combo.getData(metainfo.bindingInfo.getValue());
				if (descripcion != null) {
					combo.setText(descripcion);
				}
			} else if (comboValuesMetainfo.useEnums()) {
				completeCombo4EnumItems(combo, comboValuesMetainfo);
				metainfo.bindingInfo.bind(combo, true);
			} else if (comboValuesMetainfo.useStringItems()) {
				completeCombo4StringItems(combo, comboValuesMetainfo);
			}
			if (comboValuesMetainfo.addEmptyOption) {
				combo.add(EMPTY_ITEM, 0);
			}
			combo.setVisibleItemCount(comboValuesMetainfo.visibleItemCount);

			if (StringUtils.isBlank(currentValue)) {
				if (StringUtils.isNotBlank(metainfo.defaultOption)) {
					combo.setText(metainfo.defaultOption);
				}
			} else {
				if (!metainfo.comboValuesMI.useCodedItems()) {
					combo.setText(currentValue);
				}
			}
		}
		metainfo.restoreDefaults();
		return result;
	}

	/**
	 * Método encargado de buscar la descripción cuando el <code>value</code> es un código
	 * 
	 * @param metainfo
	 * @param value
	 * @return
	 */
	private static String findDescriptionForCode(ComboMetainfo metainfo, String value) {
		String result = value;
		if (StringUtils.isNotBlank(value) && StringUtils.isNumeric(value)
				&& (metainfo.comboValuesMI.codedItems != null)) {
			Set<Entry<String, Object>> entries = metainfo.comboValuesMI.codedItems.entrySet();
			for (Entry<String, Object> entry : entries) {
				if (entry.getValue().equals(Long.valueOf(value))) {
					result = entry.getKey();
					break;
				}
			}
		}
		return result;
	}

	private static void completeCombo(Combo combo, ComboMetainfo comboMetainfo) {
		ComboValuesMetainfo comboValuesMetainfo = comboMetainfo.comboValuesMI;
		if (comboValuesMetainfo.useCodedItems()) {
			completeComboData4CodedItems(combo, comboValuesMetainfo.codedItems);
			addListener2Combo(combo, comboMetainfo, false);
			obtenerValorActual(combo, comboMetainfo.valueMetaInfo);
		} else if (comboValuesMetainfo.useEnums()) {
			completeCombo4EnumItems(combo, comboValuesMetainfo);
			addListener2Combo(combo, comboMetainfo, true);
		} else if (comboValuesMetainfo.useStringItems()) {
			completeCombo4StringItems(combo, comboValuesMetainfo);
		}
		if (comboValuesMetainfo.addEmptyOption) {
			combo.add(EMPTY_ITEM, 0);
		}
		combo.setVisibleItemCount(comboValuesMetainfo.visibleItemCount);
	}

	private static void obtenerValorActual(Combo combo, StringValueMetaInfo valueMetaInfo) {
		if (!valueMetaInfo.isLiteral()) {
			String descripcion = (String) combo.getData(valueMetaInfo.getValue());
			if (descripcion != null) {
				combo.setText(descripcion);
			}
		}
	}

	private static void addListener2Combo(final Combo combo, ComboMetainfo comboMetainfo,
			final boolean useEnums) {
		final String propertyName = comboMetainfo.valueMetaInfo.getPropertyName();
		final Object model = comboMetainfo.valueMetaInfo.getModel();

		ModifyListener modifyListener = new ModifyListener() {

			public void modifyText(ModifyEvent event) {
				Object data = combo.getData(combo.getText());
				if (useEnums && (data == null)) {
					data = EMPTY_ITEM;
				}
				if (model != null) {
					ClassUtils.setValueByReflection(model, propertyName, data);
				}
			}
		};
		combo.addModifyListener(modifyListener);
	}

	private static void completeCombo4StringItems(Combo combo,
			ComboValuesMetainfo comboValuesMetainfo) {
		Arrays.sort(comboValuesMetainfo.items);
		combo.setItems(comboValuesMetainfo.items);
	}

	private static void completeCombo4EnumItems(Combo combo, ComboValuesMetainfo comboValuesMetainfo) {
		String[] items = null;
		if (comboValuesMetainfo.enumsMostrables == null) {
			items = getItems4Combo(comboValuesMetainfo.enumClass);
		} else {
			items = getItems4ComboConFiltro(comboValuesMetainfo.enumClass,
					comboValuesMetainfo.enumsMostrables);
		}
		combo.setItems(items);
		for (Enum e : comboValuesMetainfo.enumClass.getEnumConstants()) {
			combo.setData(e.toString(), e);
		}
		combo.setData(EMPTY_ITEM, null);
	}

	public static void completeComboData4CodedItems(Combo combo,
			LinkedHashMap<String, Object> codedItems) {
		String[] tmpItems = codedItems.keySet().toArray(new String[codedItems.size()]);
		Arrays.sort(tmpItems);
		combo.setItems(tmpItems);
		addCodedItemsData(combo, codedItems);
	}

	/*
	 * Se factorizo pues se agrega las descripciones de los items también cuando se trata de un
	 * objeto de solo lectura, así que el label también deberá conocer las descripciones de los
	 * items
	 */
	private static void addCodedItemsData(Control control, LinkedHashMap<String, Object> codedItems) {
		Iterator<Entry<String, Object>> iter = codedItems.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, Object> item = iter.next();
			control.setData(item.getKey(), item.getValue());
			control.setData(item.getValue().toString(), item.getKey());
		}
	}

	private static String[] getItems4Combo(Class<? extends Enum> enumeracion) {
		Enum[] enums = enumeracion.getEnumConstants();
		String[] items = new String[enums.length];
		for (int i = 0; i <= (enums.length - 1); i++) {
			items[i] = enums[i].toString();
		}
		Arrays.sort(items);
		return items;
	}

	/**
	 * Idem getItems4Combo pero solo retorna los enums incluidos en la lista dada.
	 * 
	 * @param enumeracion
	 * @return
	 */
	private static String[] getItems4ComboConFiltro(Class<? extends Enum> enumeracion,
			List<? extends Enum> enumFiltrado) {
		Enum[] enums = enumeracion.getEnumConstants();
		String[] items = new String[enumFiltrado.size()];
		int index = 0;
		for (Enum e : enums) {
			if (enumFiltrado.contains(e)) {
				items[index] = e.toString();
				index++;
			}
		}
		Arrays.sort(items);
		return items;
	}

	public static final int DEFAULT_COMBO_STYLE = SWT.DROP_DOWN;

	// para los filtros que usan combo permitan elegir la opcion "vacia"
	public static final String EMPTY_ITEM = "";

}
