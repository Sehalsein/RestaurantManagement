package com.seindev.sehalsein.restaurantmanagement;

/**
 * Created by sehalsein on 02/02/16.
 */
public class Order {
    private int sno;
    private String orderid;
    private String dishid;
    private String dishname;
    private int quanity;
    private int price;
    private String tableid;


    public Order() {
    }

    public Order(int sno, String orderid, String dishid, String dishname, int quanity, int price, String tableid) {
        this.sno = sno;
        this.orderid = orderid;
        this.dishid = dishid;
        this.dishname = dishname;
        this.quanity = quanity;
        this.price = price;
        this.tableid = tableid;
    }


    public int getSno() {
        return sno;
    }

    public String getOrderid() {
        return orderid;
    }

    public String getDishid() {
        return dishid;
    }

    public String getDishname() {
        return dishname;
    }

    public int getQuanity() {
        return quanity;
    }

    public int getPrice() {
        return price;
    }

    public String getTableid() {
        return tableid;
    }
}
