package com.seindev.sehalsein.restaurantmanagement;

/**
 * Created by sehalsein on 18/02/16.
 */
public class Constant {

    private static String TableId;
    private static String OrderId;
    private static String BillId;
    private static String Service;
    private static String Dishid;
    private static float TotalAmount;

    public static float getTotalAmount() {
        return TotalAmount;
    }

    public static void setTotalAmount(float totalAmount) {
        TotalAmount = totalAmount;
    }

    public Constant() {
    }

    public static String getDishid() {
        return Dishid;
    }

    public static void setDishid(String dishid) {
        Dishid = dishid;
    }

    public static String getTableId() {
        return TableId;
    }

    public static String getService() {
        return Service;
    }

    public static void setService(String service) {
        Service = service;
    }

    public static void setTableId(String tableId) {
        TableId = tableId;
    }

    public static String getOrderId() {
        return OrderId;
    }

    public static void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public static String getBillId() {
        return BillId;
    }

    public static void setBillId(String billId) {
        BillId = billId;
    }
}
