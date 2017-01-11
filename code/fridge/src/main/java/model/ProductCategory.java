package model;

/**
 * User: phi
 * Date: 07.01.17
 * .___.
 * {o,o}
 * /)___)
 * --"-"--
 */
public class ProductCategory {
	private String id = "";
	private double preis = 0;
	private String name = "";
	private int verpackungsGroesse = 0;
	
	public ProductCategory() {}; // für JaxRS Serialisierung
	
	protected ProductCategory (String id, double preis, String name, int verpackungsGroesse) {
		this.id = id;
		this.preis = preis;
		this.name = name;
		this.verpackungsGroesse = verpackungsGroesse;
	}
	
	protected ProductCategory(String id) {
		this.id = id;
	}
	
	public double getPreis() {
		return preis;
	}
	
	public void setPreis(double preis) {
		this.preis = preis;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getVerpackungsGroesse() {
		return verpackungsGroesse;
	}
	
	public void setVerpackungsGroesse(int verpackungsGroesse) {
		this.verpackungsGroesse = verpackungsGroesse;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
}
