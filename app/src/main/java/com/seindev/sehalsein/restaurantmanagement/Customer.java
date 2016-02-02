package com.seindev.sehalsein.restaurantmanagement;

/**
 * Created by sehalsein on 02/02/16.
 */
public class Customer {

    private String customerId;
    private String customerName;
    private String mobileNo;
    private int sno;

    public Customer() {
    }

    public Customer(String customerId, String customerName, String mobileNo, int sno) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.mobileNo = mobileNo;
        this.sno = sno;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public int getSno() {
        return sno;
    }
}
