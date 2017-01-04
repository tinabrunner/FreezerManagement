package controller;

import java.util.List;

import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import model.Invoice;

@Stateless
@Path("/invoices")
@Produces(MediaType.APPLICATION_JSON)
public class InvoiceController {

	private List<Invoice> invoices;


	@POST
	public static void addInvoice(Invoice invoice) {
		// invoices.add(invoice);
	}
	@POST
	public List<Invoice> getInvoices() {
		return invoices;
	}


}
