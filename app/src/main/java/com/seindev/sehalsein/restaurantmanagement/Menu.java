package com.seindev.sehalsein.restaurantmanagement;

/**
 * Created by sehalsein on 29/11/15.
 */
public class Menu {

    private int sno;
    private String dishid;
    private String dishname;
    private float price;
    private String category;
    private String description;
    private String imageurl;

    public Menu() {
    }

    public Menu(int sno, String dishid, String dishname, float price, String category, String description, String imageurl) {
        this.sno = sno;
        this.dishid = dishid;
        this.dishname = dishname;
        this.price = price;
        this.category = category;
        this.description = description;
        this.imageurl = imageurl;
    }

    public String getImageurl() {
        return imageurl;
    }

    public int getSno() {
        return sno;
    }

    public String getDishid() {
        return dishid;
    }

    public String getDishname() {
        return dishname;
    }

    public float getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }
}
