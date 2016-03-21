package com.seindev.sehalsein.restaurantmanagement;

/**
 * Created by sehalsein on 18/02/16.
 */
public class Constant {

    private static String TableId;
    private static String OrderId;
    private static String BillId;
    private static String Service;
    private static String DishId;
    private static String CustomerId;
    private static float TotalAmount;
    private static String Category;
    private static int Imageid;

    public static int getImageid() {
        return Imageid;
    }

    public static void setImageid(int imageid) {
        Imageid = imageid;
    }

    public Constant() {
    }

    public static String getCategory() {
        return Category;
    }

    public static void setCategory(String category) {
        Category = category;
    }

    public static String getCustomerId() {
        return CustomerId;
    }

    public static String getTableId() {
        return TableId;
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

    public static String getService() {
        return Service;
    }

    public static void setService(String service) {
        Service = service;
    }

    public static String getDishId() {
        return DishId;
    }

    public static void setDishId(String dishId) {
        DishId = dishId;
    }

    public static void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public static float getTotalAmount() {
        return TotalAmount;
    }

    public static void setTotalAmount(float totalAmount) {
        TotalAmount = totalAmount;
    }
}
