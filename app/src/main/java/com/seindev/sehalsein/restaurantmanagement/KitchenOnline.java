package com.seindev.sehalsein.restaurantmanagement;

/**
 * Created by sehalsein on 13/03/16.
 */
public class KitchenOnline {

    private String dishid;
    private String tableid;
    private int quantity;

    public KitchenOnline() {
    }

    public KitchenOnline(String dishid, int quantity, String tableid) {
        this.dishid = dishid;
        this.quantity = quantity;
        this.tableid = tableid;
    }

    public String getDishid() {
        return dishid;
    }

    public String getTableid() {
        return tableid;
    }

    public int getQuantity() {
        return quantity;
    }
}
