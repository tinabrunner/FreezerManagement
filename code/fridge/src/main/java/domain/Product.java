package domain;

import java.util.Date;

/**
 * Created by JD on 07.12.2016.
 */
public class Product extends BaseProduct {

    private String id;
    private int mindestBestand;
    private int hoechstBestand;
    private int aktuellerBestand;
    private boolean regelmaessig;
    private Date ablaufDatum;
    private boolean mindesetBestandUnterschritten;

    public Product() {
        this.mindesetBestandUnterschritten = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private int getMindestBestand() {
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
        return mindesetBestandUnterschritten;
    }

    public int reduceStock(int reduceBy) {
        this.setAktuellerBestand(this.getAktuellerBestand() - reduceBy);
        this.mindesetBestandUnterschritten = this.getAktuellerBestand() < this.getMindestBestand();
        return this.getAktuellerBestand();
    }

    public int raiseStock(int raiseBy) {
        this.setAktuellerBestand(this.getAktuellerBestand() + raiseBy);
        return this.getAktuellerBestand();
    }
}
