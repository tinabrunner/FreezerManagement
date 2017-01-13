package queueConnection;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.naming.NamingException;

import com.google.gson.Gson;

import db_communication.DB_Invoices;
import model.Invoice;

@Stateless(name = "deliveryListener")
public class DeliveryListener {

	@EJB
	private DB_Invoices dbinvoice;

	public DeliveryListener() throws NamingException {

	}

	public void onMessage(Message m) {
		try {
			TextMessage msg = (TextMessage) m;
			Invoice i = messageToInvoice(msg.getText());
			dbinvoice.insertInvoiceToDB(i);

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public Invoice messageToInvoice(String m) {
		Gson gson = new Gson();
		String jsonInString = m;
		Invoice i = gson.fromJson(jsonInString, Invoice.class);
		return i;
	}

}
