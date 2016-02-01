package com.seindev.sehalsein.restaurantmanagement;

/**
 * Created by sehalsein on 31/01/16.
 */



//TODO CAMEL CASE THE VARIABLES IMPORTANT FOR CONSISTENCY


public class Review {
    private String customerid;
    private String dishid;
    private String review;
    private int sno;

    //TODO RATING FLOAT
    private int ratings;

    public Review() {
    }

    public Review(String customerid, String dishid, String review, int sno, int ratings) {
        this.customerid = customerid;
        this.dishid = dishid;
        this.review = review;
        this.sno = sno;
        this.ratings = ratings;
    }

    public String getCustomerid() {
        return customerid;
    }

    public String getDishid() {
        return dishid;
    }

    public String getReview() {
        return review;
    }

    public int getSno() {
        return sno;
    }

    public int getRatings() {
        return ratings;
    }
}
