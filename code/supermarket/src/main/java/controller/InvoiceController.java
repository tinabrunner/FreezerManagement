package controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

import Model.Invoice;

public class InvoiceController {

	public void sendInvoice(Invoice invoice) {
		try { // Create and start connection
			InitialContext ctx = new InitialContext();
			QueueConnectionFactory f = (QueueConnectionFactory) ctx.lookup("myQueueConnectionFactory");
			QueueConnection con = f.createQueueConnection();
			con.start();
			// 2) create queue session
			QueueSession ses = con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			// 3) get the Queue object
			Queue t = (Queue) ctx.lookup("myQueue");
			// 4)create QueueSender object
			QueueSender sender = ses.createSender(t);
			// 5) create TextMessage object
			TextMessage msg = ses.createTextMessage();

			// 6) write message
			BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
			while (true) {
				System.out.println("Enter Msg, end to terminate:");
				String s = b.readLine();
				if (s.equals("end"))
					break;
				msg.setText(s);
				// 7) send message
				sender.send(msg);
				System.out.println("Message successfully sent.");
			}
			// 8) connection close
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
