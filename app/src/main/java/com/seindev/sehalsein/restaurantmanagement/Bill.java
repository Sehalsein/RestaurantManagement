package com.seindev.sehalsein.restaurantmanagement;

/**
 * Created by sehalsein on 09/02/16.
 */
public class Bill {

    private int sno;
    private String billid;
    private String orderid;
    private float price;
    private String tableid;
    private String date;

    public Bill() {
    }

    public Bill(int sno, String billid, String orderid, float price, String tableid, String date) {

        this.sno = sno;
        this.billid = billid;
        this.orderid = orderid;
        this.price = price;
        this.tableid = tableid;
        this.date = date;
    }

    public int getSno() {
        return sno;
    }

    public String getBillid() {
        return billid;
    }

    public String getOrderid() {
        return orderid;
    }

    public float getPrice() {
        return price;
    }

    public String getTableid() {
        return tableid;
    }

    public String getDate() {
        return date;
    }


}