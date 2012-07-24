package commons.gui.form;

public interface Subscriber {

	void change(String key, Object value);

	String getId();
}