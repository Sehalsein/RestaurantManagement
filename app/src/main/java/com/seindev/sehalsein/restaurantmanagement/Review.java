package com.seindev.sehalsein.restaurantmanagement;

/**
 * Created by sehalsein on 31/01/16.
 */


//TODO CAMEL CASE THE VARIABLES IMPORTANT FOR CONSISTENCY


public class Review {

    private int sno;
    private String customerid;
    private String dishid;
    private float rating;
    private String review;
    private String date;

    public Review() {
    }

    public Review(int sno, String customerid, String dishid, float rating, String review, String date) {
        this.sno = sno;
        this.customerid = customerid;
        this.dishid = dishid;
        this.rating = rating;
        this.review = review;
        this.date = date;
    }

    public int getSno() {
        return sno;
    }

    public String getCustomerid() {
        return customerid;
    }

    public String getDishid() {
        return dishid;
    }

    public float getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }

    public String getDate() {
        return date;
    }
}
