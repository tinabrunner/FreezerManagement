package model;

import java.util.Date;

public class InventoryProduct {

	private int prodCategoryId;
	private String name;
	private Date expiryDate;

	public InventoryProduct(int prodCategoryId, String name, Date expiryDate) {
		this.prodCategoryId = prodCategoryId;
		this.name = name;
		this.expiryDate = expiryDate;
	}

	public int getProdCategoryId() {
		return prodCategoryId;
	}

	public void setProdCategoryId(int prodCategoryId) {
		this.prodCategoryId = prodCategoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

}
