package com.seindev.sehalsein.restaurantmanagement;

/**
 * Created by sehalsein on 02/02/16.
 */
public class Order {
    private int sno;
    private String billNo;
    private String dishId;
    private String dishName;
    private int quantity;
    private int price;
    private String tableNo;

    public Order() {
    }

    public Order(String tableNo, String billNo, int sno) {
        this.tableNo = tableNo;
        this.billNo = billNo;
        this.sno = sno;
    }

    public Order(int sno, String billNo, String dishId, String dishName, int quantity, int price, String tableNo) {
        this.sno = sno;
        this.billNo = billNo;
        this.dishId = dishId;
        this.dishName = dishName;
        this.quantity = quantity;
        this.price = price;
        this.tableNo = tableNo;
    }

    public int getSno() {
        return sno;
    }

    public String getBillNo() {
        return billNo;
    }

    public String getDishId() {
        return dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public String getTableNo() {
        return tableNo;
    }
}
