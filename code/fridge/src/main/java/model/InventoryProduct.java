package model;

import java.util.Date;

public class InventoryProduct {

	private String prodCategoryId;
	private String name;
	private Date expiryDate;

	public InventoryProduct() {
	} // keep
	
	public InventoryProduct( String prodCategoryId, String name, Date expiryDate) {
		this.name = name;
		this.prodCategoryId = prodCategoryId;
		this.expiryDate = expiryDate;
	}

	public String getProdCategoryId() {
		return prodCategoryId;
	}

	public void setProdCategoryId(String prodCategoryId) {
		this.prodCategoryId = prodCategoryId;
	}
	
	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
