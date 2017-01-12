import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import controller.*;
import queueConnection.InvoiceReceiver;
import scheduleTasks.OrderTimer;

/**
 * User: phi Date: 04.01.17
 *  .___.
 *  {o,o}
 * /)___)
 * --"-"--
 */
@ApplicationPath("/api")
public class ApplicationConfig extends Application {
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();

		// hier klassen auflisten, die GET/POST/...-Anfragen entgegennehmen
		// können sollen (pfad: /fridge/api/[@Path..])
		classes.add(ShoppingCartController.class);
		classes.add(InventoryController.class);
		classes.add(InvoiceController.class);
		classes.add(LoginController.class);
		classes.add(AccountController.class);
		classes.add(ShoppingListController.class);
		classes.add(InvoiceReceiver.class);
		classes.add(SettingsController.class);
		
		classes.add(OrderTimer.class); // todo

		return classes;
	}

}
