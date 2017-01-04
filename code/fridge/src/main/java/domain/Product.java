package domain;

import java.util.Date;

/**
 * Created by JD on 07.12.2016.
 */
public class Product extends BaseProduct {

    private String id;
    private static int counter_id = 1;
    private int mindestBestand;
    private int hoechstBestand;
    private int aktuellerBestand;
    private boolean regelmaessig;
    private Date ablaufDatum;

    // id wird automatisch erstellt, falls es ein neues Produkt ist
    public Product (double preis, String name, int verpackungsGroesse,
    		int mindestbestand, int hoechstbestand, int aktuellerbestand,
    		boolean regelmaessig, Date ablaufdatum) {
    	
        super(preis, name, verpackungsGroesse);
        
        this.id = "p" + counter_id;
        counter_id ++;
        
        this.mindestBestand = mindestbestand;
        this.hoechstBestand = hoechstbestand;
        this.aktuellerBestand = aktuellerbestand;
        this.regelmaessig = regelmaessig;
        this.ablaufDatum = ablaufdatum;
    }

    // id wird mitgegeben, falls ein Produkt aus der DB geholt wird
    public Product (double preis, String name, int verpackungsGroesse,
    		int mindestbestand, int hoechstbestand, int aktuellerbestand,
    		boolean regelmaessig, Date ablaufdatum, String id) {
    	
        super(preis, name, verpackungsGroesse);
        
        this.mindestBestand = mindestbestand;
        this.hoechstBestand = hoechstbestand;
        this.aktuellerBestand = aktuellerbestand;
        this.regelmaessig = regelmaessig;
        this.ablaufDatum = ablaufdatum;
        this.id = id;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMindestBestand() {
        return mindestBestand;
    }

    public void setMindestBestand(int mindestBestand) {
        this.mindestBestand = mindestBestand;
    }

    public int getHoechstBestand() {
        return hoechstBestand;
    }

    public void setHoechstBestand(int hoechstBestand) {
        this.hoechstBestand = hoechstBestand;
    }

    private int getAktuellerBestand() {
        return aktuellerBestand;
    }

    private void setAktuellerBestand(int aktuellerBestand) {
        this.aktuellerBestand = aktuellerBestand;
    }

    public boolean isRegelmaessig() {
        return regelmaessig;
    }

    public void setRegelmaessig(boolean regelmaessig) {
        this.regelmaessig = regelmaessig;
    }

    public Date getAblaufDatum() {
        return ablaufDatum;
    }

    public void setAblaufDatum(Date ablaufDatum) {
        this.ablaufDatum = ablaufDatum;
    }

    public boolean isMindesetBestandUnterschritten() {    	
    	return this.getAktuellerBestand() < this.getMindestBestand();
    }

    public int reduceStock(int reduceBy) {
        this.setAktuellerBestand(this.getAktuellerBestand() - reduceBy);
        return this.getAktuellerBestand();
    }

    public int raiseStock(int raiseBy) {
        this.setAktuellerBestand(this.getAktuellerBestand() + raiseBy);
        return this.getAktuellerBestand();
    }
}
