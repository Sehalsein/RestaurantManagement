package com.seindev.sehalsein.restaurantmanagement;

/**
 * Created by sehalsein on 29/11/15.
 */
public class Menu {

    private String DishName;
    private String Ingredients;
    private String Category;
    private String Tags;

    private String Quantity;
    private String Price;

    public Menu() {
    }

    public Menu(String DishName, String Price, String Quantity, String Category, String Tags) {
        this.DishName = DishName;
        this.Price = Price;
        this.Quantity = Quantity;
        this.Category = Category;
        this.Tags = Tags;
    }

    public String getPrice() {
        return Price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public String getDishName() {
        return DishName;
    }

    public String getCategory() {
        return Category;
    }

    public String getTags() {
        return Tags;
    }
}
