package recepciondetrabajos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import commons.logging.AppLogger;
import commons.util.SbaStringUtils;

public class ApplicationContext extends ClassPathXmlApplicationContext {

	public <T> T getBean(Class<T> beanClass) {
		Map<String, Object> beans = getBeansOfType(beanClass);
		String canonicalName = beanClass.getCanonicalName();
		assert !beans.isEmpty() : SbaStringUtils.concat("No existe bean para la clase ",
				canonicalName);
		assert beans.size() < 2 : SbaStringUtils.concat(
				"Existe mas de un bean para la misma clase", canonicalName);
		return (T) beans.values().toArray()[0];
	}

	public static ApplicationContext getInstance() {
		if (context == null) {
			try {
				context = new ApplicationContext();
			} catch (Exception ex) {
				AppLogger.getLogger().log(Level.SEVERE, ex.getLocalizedMessage(), ex);
				throw new RuntimeException("No se pudo crear el application context", ex);
			}
		}
		return context;
	}

	/**
	 * Constructor privado.
	 */
	private ApplicationContext() {
		super(locations.toArray(new String[locations.size()]));
	}

	private static List<String> locations;

	private static ApplicationContext context;

	static {
		locations = new ArrayList<String>();
		locations.add("spring/applicationContext.xml");
	}
}