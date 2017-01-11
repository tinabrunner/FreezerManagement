import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import controller.ProductListController;

/**
 * User: phi Date: 04.01.17 .___. {o,o} /)___) --"-"--
 */
@ApplicationPath("/api")
public class ApplicationConfig extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();

		classes.add(ProductListController.class);
		classes.add(CrossOriginFilter.class);

		return classes;
	}

}
