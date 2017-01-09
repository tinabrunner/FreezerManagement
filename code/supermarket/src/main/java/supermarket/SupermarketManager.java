package supermarket;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.jms.JMSException;

/**
 * @author Marius Koch
 *
 */
@Singleton
@Startup
public class SupermarketManager {

	/**
	 * @param args
	 * @throws JMSException
	 */

	public static void main(String[] args) throws JMSException {
		System.out.println("Main läuft");
		// OrderReceiver orderReceiver = new OrderReceiver();
	}

}
