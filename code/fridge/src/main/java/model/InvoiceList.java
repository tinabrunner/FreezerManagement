package model;

import java.util.ArrayList;

/**
 * @author Marius Koch
 *
 */
public class InvoiceList {

	public InvoiceList() {
	} // keep

	private ArrayList<Invoice> list;

	public ArrayList<Invoice> getList() {
		return list;
	}

	public void setList(ArrayList<Invoice> list) {
		this.list = list;
	}

}
