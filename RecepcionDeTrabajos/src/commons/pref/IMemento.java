package commons.pref;

import java.io.Writer;

public interface IMemento {

	IMemento createChild(String string);

	IMemento createChild(String string1, String string2);

	IMemento getChild(String string);

	IMemento[] getChildren(String string);

	Float getFloat(String string);

	String getID();

	Integer getInteger(String string);

	String getString(String string);

	String getTextData();

	void putFloat(String string, float floatValue);

	void putInteger(String string, int intValue);

	void putMemento(IMemento imemento);

	void putString(String string1, String string2);

	void putTextData(String string);

	void save(Writer writer);
}