package com.freezer.domain;

/**
 * Created by JD on 07.12.2016.
 */
public class BaseProduct {
    private double preis;
    private String name;
    private int verpackungsGroesse;

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
}
