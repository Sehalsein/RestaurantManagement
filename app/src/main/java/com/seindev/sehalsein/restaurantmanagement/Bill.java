package com.seindev.sehalsein.restaurantmanagement;

/**
 * Created by sehalsein on 09/02/16.
 */
public class Bill {

    private int sno;
    private String billNo;
    private int price;
    private String tableNo;
    private String date;

    public Bill() {
    }

    public Bill(int sno, String billNo, int price, String tableNo, String date) {
        this.sno = sno;
        this.billNo = billNo;
        this.price = price;
        this.tableNo = tableNo;
        this.date = date;
    }

    public int getSno() {
        return sno;
    }

    public String getBillNo() {
        return billNo;
    }

    public int getPrice() {
        return price;
    }

    public String getTableNo() {
        return tableNo;
    }

    public String getDate() {
        return date;
    }
}
