package com.seindev.sehalsein.restaurantmanagement;

/**
 * Created by sehalsein on 22/03/16.
 */
public class Delivery {
    private String customername;
    private double mobileno;
    private String address;
    private String lanmark;

    public Delivery() {
    }

    public Delivery(String customername, double mobileno, String address, String lanmark) {
        this.customername = customername;
        this.mobileno = mobileno;
        this.address = address;
        this.lanmark = lanmark;
    }

    public String getCustomername() {
        return customername;
    }

    public double getMobileno() {
        return mobileno;
    }

    public String getAddress() {
        return address;
    }

    public String getLanmark() {
        return lanmark;
    }
}
