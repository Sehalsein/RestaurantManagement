package com.seindev.sehalsein.restaurantmanagement;

/**
 * Created by sehalsein on 02/12/15.
 */
public class AddItem {

    String vItemName, vItemHint;
    private String DishName;
    private String Ingredients;
    private String Category;
    private String Tags;

    private int Quantity;
    private int Price;

    public AddItem() {
    }

    public AddItem(String DishName, int Price, int Quantity, String Category, String Tags) {
        this.DishName = DishName;
        this.Price = Price;
        this.Quantity = Quantity;
        this.Category = Category;
        this.Tags = Tags;
    }

    public int getPrice() {
        return Price;
    }

    public int Quantity() {
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
