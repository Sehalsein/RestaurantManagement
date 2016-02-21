package com.seindev.sehalsein.restaurantmanagement;

/**
 * Created by sehalsein on 21/02/16.
 */
public class KitchenOpen {
    private int sno;
    private String orderid;
    private String tableid;
    private float totalamount;

    public KitchenOpen() {
    }

    public KitchenOpen(int sno, String orderid, String tableid, float totalamount) {
        this.sno = sno;
        this.orderid = orderid;
        this.tableid = tableid;
        this.totalamount = totalamount;
    }

    public int getSno() {
        return sno;
    }

    public String getOrderid() {
        return orderid;
    }

    public String getTableid() {
        return tableid;
    }

    public float getTotalamount() {
        return totalamount;
    }
}
