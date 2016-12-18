package com.freezer.domain;

import java.util.List;

/**
 * Created by JD on 08.12.2016.
 */
class Freezer {

    private final int id;
    private List<Product> inventory;

    public Freezer(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public List<Product> getInventory() {
        return inventory;
    }

    public void setInventory(List<Product> inventory) {
        this.inventory = inventory;
    }
}
