package queueConnection;

import javax.ejb.Stateless;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Stateless
@Path("/receiver")
public class InvoiceReceiver {

	@GET
	@Path("/start")
	public void run() {
		System.out.println("started");
		try {
			// 1) Create and start connection
			InitialContext ctx = new InitialContext();
			QueueConnectionFactory f = (QueueConnectionFactory) ctx.lookup("jms/fridgeConnectionFactory");
			QueueConnection con = f.createQueueConnection();
			con.start();

			// 2) create Queue session
			QueueSession ses = con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

			// 3) get the Queue object
			Queue t = (Queue) ctx.lookup("jms/invoiceQueue/invoices");

			// 4)create QueueReceiver
			QueueReceiver receiver = ses.createReceiver(t);

			InvoiceListener listener = new InvoiceListener();

			// 5) register the listener object with receiver
			receiver.setMessageListener(listener);

			while (true) {
				Thread.sleep(1000);
				System.out.println("sleep");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// public void onMessage(Message m) {
	// try {
	// TextMessage msg = (TextMessage) m;
	// System.out.println("Fridge:" + msg);
	//
	// Gson gson = new Gson();
	//
	// // 2. JSON to Java object, read it from a Json String.
	// String jsonInString = msg.getText();
	// Invoice invoice = gson.fromJson(jsonInString, Invoice.class);
	// InvoiceController.addInvoice(invoice);
	//
	// } catch (JMSException e) {
	// System.out.println(e);
	// }
}
