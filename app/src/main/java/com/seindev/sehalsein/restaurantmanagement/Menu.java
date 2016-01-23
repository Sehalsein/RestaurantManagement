package com.seindev.sehalsein.restaurantmanagement;

/**
 * Created by sehalsein on 29/11/15.
 */
public class Menu {

    private String sno;
    private String dishId;
    private String dishName;
    private String price;
    private String quantity;
    private String category;
    private String tags;
    private String spicy;
    private String ingredients;

    public Menu() {
    }

    public Menu(String SNo, String DishId, String DishName, String Price, String Quantity, String Category, String Tags) {
        this.sno = SNo;
        this.dishId = DishId;
        this.dishName = DishName;
        this.price = Price;
        this.quantity = Quantity;
        this.category = Category;
        this.tags = Tags;
    }

    public String getSNo() {
        return sno;
    }

    public String getDishId() {
        return dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public String getTags() {
        return tags;
    }
}
