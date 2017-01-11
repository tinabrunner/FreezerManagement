package queueConnection;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.naming.NamingException;

import com.google.gson.Gson;

import db_communication.DB_Invoices;
import model.Invoice;

public class InvoiceListener implements MessageListener {

	@Resource(name = "dbinvoices")
	DB_Invoices dbinvoice;

	public InvoiceListener() throws NamingException {

	}

	public void onMessage(Message m) {
		try {
			TextMessage msg = (TextMessage) m;

			Gson gson = new Gson();
			System.out.println(msg.getText());
			// 2. JSON to Java object, read it from a Json String.
			String jsonInString = msg.getText();
			Invoice i = gson.fromJson(jsonInString, Invoice.class);
			dbinvoice.insertInvoiceToDB(i);

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
