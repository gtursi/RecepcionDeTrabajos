package commons.gui.form;

import java.util.ArrayList;
import java.util.List;

public class BasicPublisher implements Publisher {

	public void subscribe(Subscriber subscriber) {
		subscribers.add(subscriber);
	}

	protected final List<Subscriber> subscribers = new ArrayList<Subscriber>();
}
