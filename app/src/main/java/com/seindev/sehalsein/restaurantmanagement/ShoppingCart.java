package com.seindev.sehalsein.restaurantmanagement;

/**
 * Created by sehalsein on 31/01/16.
 */
public class ShoppingCart {

    String name;

    private String dishname;
    private String tableno;
    private int quantity;
    private int price;
    private int sno;
    private String billno;
    private String orderno;

    public ShoppingCart() {
    }

    public ShoppingCart(String dishname, int price, int sno, int quantity) {
        this.dishname = dishname;
        this.price = price;
        this.sno = sno;
        this.quantity = quantity;
    }

    public ShoppingCart(String name, String dishname, String tableno, int quantity, int price, int sno, String billno, String orderno) {
        this.name = name;
        this.dishname = dishname;
        this.tableno = tableno;
        this.quantity = quantity;
        this.price = price;
        this.sno = sno;
        this.billno = billno;
        this.orderno = orderno;
    }

    public String getDishname() {
        return dishname;
    }

    public String getTableno() {
        return tableno;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public int getSno() {
        return sno;
    }

    public String getBillno() {
        return billno;
    }

    public String getOrderno() {
        return orderno;
    }
}
