package com.seindev.sehalsein.restaurantmanagement;

/**
 * Created by sehalsein on 22/02/16.
 */
public class Customer {
    private int sno;
    private String customerid;
    private String customername;

    public Customer() {
    }

    public Customer(int sno, String customerid, String customername) {
        this.sno = sno;
        this.customerid = customerid;
        this.customername = customername;
    }

    public int getSno() {
        return sno;
    }

    public String getCustomerid() {
        return customerid;
    }

    public String getCustomername() {
        return customername;
    }
}
