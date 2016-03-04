package com.seindev.sehalsein.restaurantmanagement;

/**
 * Created by sehalsein on 04/03/16.
 */
public class Active {

    private int sno;
    private String tableid;
    private String orderid;

    public Active() {
    }

    public Active(int sno, String tableid, String orderid) {
        this.sno = sno;
        this.tableid = tableid;
        this.orderid = orderid;
    }

    public int getSno() {
        return sno;
    }

    public String getTableid() {
        return tableid;
    }

    public String getOrderid() {
        return orderid;
    }
}
