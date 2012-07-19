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
 * $Id: XMLMemento.java,v 1.13 2007/06/27 18:30:51 cvschioc Exp $
 */
package commons.pref;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import commons.logging.AppLogger;

/**
 * @author Margarita Buriano
 * @version $Revision: 1.13 $ $Date: 2007/06/27 18:30:51 $
 */
public class XMLMemento implements IMemento {

	private static final class DOMWriter extends PrintWriter {

		public void print(Element element) {
			boolean hasChildren = element.hasChildNodes();
			startTag(element, hasChildren);
			if (hasChildren) {
				boolean prevWasText = false;
				NodeList children = element.getChildNodes();
				for (int i = 0; i < children.getLength(); i++) {
					Node node = children.item(i);
					if (node instanceof Element) {
						if (!prevWasText) {
							println();
							printTabulation();
						}
						print((Element) children.item(i));
						prevWasText = false;
					} else if (node instanceof Text) {
						print(node.getNodeValue());
						prevWasText = true;
					}
				}

				if (!prevWasText) {
					println();
					printTabulation();
				}
				endTag(element);
			}
		}

		private void printTabulation() {
			// nothing
		}

		private void startTag(Element element, boolean hasChildren) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append('<');
			stringBuilder.append(element.getTagName());
			NamedNodeMap attributes = element.getAttributes();
			for (int i = 0; i < attributes.getLength(); i++) {
				Attr attribute = (Attr) attributes.item(i);
				stringBuilder.append(' ');
				stringBuilder.append(attribute.getName());
				stringBuilder.append("=\"");
				stringBuilder.append(getEscaped(String.valueOf(attribute.getValue())));
				stringBuilder.append('"');
			}

			stringBuilder.append(hasChildren ? '>' : "/>");
			print(stringBuilder.toString());
		}

		private void endTag(Element element) {
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("</");
			stringBuilder.append(element.getNodeName());
			stringBuilder.append('>');
			print(stringBuilder.toString());
		}

		private static void appendEscapedChar(StringBuilder builder, char character) {
			String replacement = getReplacement(character);
			if (replacement == null) {
				builder.append(character);
			} else {
				builder.append('&');
				builder.append(replacement);
				builder.append(';');
			}
		}

		private static String getEscaped(String text) {
			StringBuilder result = new StringBuilder(text.length() + 10);
			for (int i = 0; i < text.length(); i++) {
				appendEscapedChar(result, text.charAt(i));
			}

			return result.toString();
		}

		private static String getReplacement(char character) {
			String replacement;
			switch (character) {
				case 60: // '<'
					replacement = "lt";
					break;
				case 62: // '>'
					replacement = "gt";
					break;
				case 34: // '"'
					replacement = "quot";
					break;
				case 39: // '\''
					replacement = "apos";
					break;
				case 38: // '&'
					replacement = "amp";
					break;
				case 13: // '\r'
					replacement = "#x0D";
					break;
				case 10: // '\n'
					replacement = "#x0A";
					break;
				case 9: // '\t'
					replacement = "#x09";
					break;
				default:
					replacement = null;
					break;
			}
			return replacement;
		}

		public DOMWriter(Writer output, String version) {
			super(output);
			println("<?xml version=\"" + version + "\" encoding=\"ISO-8859-1\"?>");
		}
	}

	public XMLMemento(Document document, Element element) {
		factory = document;
		this.element = element;

	}

	public IMemento createChild(String type) {
		Element child = factory.createElement(type);
		element.appendChild(child);
		return new XMLMemento(factory, child);
	}

	public IMemento createChild(String type, String identif) {
		Element child = factory.createElement(type);
		child.setAttribute(TAG_ID, identif == null ? "" : identif);
		element.appendChild(child);
		return new XMLMemento(factory, child);
	}

	public IMemento copyChild(IMemento child) {
		Element childElement = ((XMLMemento) child).element;
		Element newElement = (Element) factory.importNode(childElement, true);
		element.appendChild(newElement);
		return new XMLMemento(factory, newElement);
	}

	public IMemento getChild(String type) {
		IMemento memento = null;
		NodeList nodes = this.element.getChildNodes();
		int size = nodes.getLength();
		if (size != 0) {
			for (int nX = 0; nX < size; nX++) {
				Node node = nodes.item(nX);
				if (node instanceof Element) {
					Element elem = (Element) node;
					if (elem.getNodeName().equals(type)) {
						memento = new XMLMemento(factory, elem);
						break;
					}
				}
			}
		}
		return memento;
	}

	public IMemento[] getChildren(String type) {
		IMemento results[] = new IMemento[0];
		NodeList nodes = this.element.getChildNodes();
		int size = nodes.getLength();
		if (size != 0) {
			ArrayList<Element> list = new ArrayList<Element>(size);
			for (int nX = 0; nX < size; nX++) {
				Node node = nodes.item(nX);
				if (node instanceof Element) {
					Element elem = (Element) node;
					if (elem.getNodeName().equals(type)) {
						list.add(elem);
					}
				}
			}

			size = list.size();
			results = new IMemento[size];
			for (int x = 0; x < size; x++) {
				results[x] = new XMLMemento(factory, list.get(x));
			}
		}

		return results;
	}

	public Float getFloat(String key) {
		Attr attr = element.getAttributeNode(key);
		Float result = null;
		if (attr != null) {
			String strValue = attr.getValue();
			try {
				result = new Float(strValue);
			} catch (NumberFormatException exception) {
				AppLogger.getLogger().finer(
						"Memento problem - Invalid float for key: " + key + " value: " + strValue);
			}
		}
		return result;
	}

	public String getID() {
		return element.getAttribute(TAG_ID);
	}

	public Integer getInteger(String key) {
		Integer result = null;
		Attr attr = element.getAttributeNode(key);
		if (attr != null) {
			String strValue = attr.getValue();
			try {
				result = Integer.valueOf(strValue);
			} catch (NumberFormatException e) {
				AppLogger.getLogger().finer(
						"Memento problem - " + "Invalid integer for key: " + key + " value: "
								+ strValue);
			}
		}

		return result;
	}

	public String getString(String key) {
		Attr attr = element.getAttributeNode(key);
		return attr == null ? null : attr.getValue();
	}

	public String getTextData() {
		Text textNode = getTextNode();
		return textNode == null ? null : textNode.getData();
	}

	@SuppressWarnings("unused")
	private Text getTextNode() {
		Text result = null;
		NodeList nodes = element.getChildNodes();
		int size = nodes.getLength();
		if (size != 0) {
			for (int nX = 0; nX < size; nX++) {
				Node node = nodes.item(nX);
				if (node instanceof Text) {
					result = (Text) node;
				}
				break;
			}
		}
		return result;
	}

	private void putElement(Element elem) {
		NamedNodeMap nodeMap = elem.getAttributes();
		int size = nodeMap.getLength();
		for (int i = 0; i < size; i++) {
			Attr attr = (Attr) nodeMap.item(i);
			putString(attr.getName(), attr.getValue());
		}

		NodeList nodes = elem.getChildNodes();
		size = nodes.getLength();
		for (int i = 0; i < size; i++) {
			Node node = nodes.item(i);
			if (node instanceof Element) {
				XMLMemento child = (XMLMemento) createChild(node.getNodeName());
				child.putElement((Element) node);
			}
		}

	}

	public void putFloat(String key, float floatValue) {
		element.setAttribute(key, String.valueOf(floatValue));
	}

	public void putInteger(String key, int intValue) {
		element.setAttribute(key, String.valueOf(intValue));
	}

	public void putMemento(IMemento memento) {

		putElement(((XMLMemento) memento).element);
	}

	public void putString(String key, String value) {
		if (value != null) {
			element.setAttribute(key, value);
		}
	}

	public void putTextData(String data) {
		Text textNode = getTextNode();
		if (textNode == null) {
			textNode = factory.createTextNode(data);
			element.insertBefore(textNode, element.getFirstChild());
		} else {
			textNode.setData(data);
		}
	}

	public void save(Writer writer) {
		String version = factory.getXmlVersion();
		DOMWriter out = new DOMWriter(writer, version);
		try {
			out.print(element);
		} finally {
			out.close();
		}
	}

	public Element getElement() {
		return element;
	}

	protected IMemento getRootOfElement(String elem) {
		return this.getChild(elem);
	}

	private final Document factory;

	private final Element element;

	public static final String TAG_ID = "IMemento.internal.id";
}
