package Model;

/**
 * @author Marius Koch
 *
 */
public class Product {

	private String id;
	private String name;
	private int verpackungsGroesse;
	private double preis;
	private int calories;
	
	public Product() { /* keep */ }

	public Product(String id, String name, int verpackungsGroesse, double preis, int calories) {
		this.id = id;
		this.name = name;
		this.verpackungsGroesse = verpackungsGroesse;
		this.preis = preis;
		this.calories = calories;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPreis() {
		return preis;
	}

	public void setPreis(double preis) {
		this.preis = preis;
	}

	public int getCalories() {
		return calories;
	}

	public void setCalories(int calories) {
		this.calories = calories;
	}

}
