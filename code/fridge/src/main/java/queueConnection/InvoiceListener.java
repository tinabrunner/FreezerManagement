package queueConnection;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import com.google.gson.Gson;

import db_communication.DB_Invoices;
import model.Invoice;

@Stateless
public class InvoiceListener implements MessageListener {

	@EJB
	DB_Invoices db_invoices;

	public void onMessage(Message m) {
		try {
			TextMessage msg = (TextMessage) m;

			Gson gson = new Gson();
			System.out.println(msg.getText());
			// 2. JSON to Java object, read it from a Json String.
			String jsonInString = msg.getText();
			Invoice i = gson.fromJson(jsonInString, Invoice.class);
			System.out.println(i.getId() + i.getName() + i.getBillingDate() + i.getOrderDate() + i.getTotalPrice()
					+ i.getInvoiceURL());
			db_invoices.insertInvoiceToDB(i);

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
