package com.seindev.sehalsein.restaurantmanagement;

/**
 * Created by sehalsein on 02/12/15.
 */
public class AddItemInfo {

    String vItemName, vItemHint;
    private String DishName;
    private String Ingredients;
    private String Category;
    private String SubCategory;

    private int Quantity;
    private int Price;

    public AddItemInfo() {
    }

    public AddItemInfo(String DishName, int Price, int Quantity, String Category, String SubCategory) {
        this.DishName = DishName;
        this.Price = Price;
        this.Quantity = Quantity;
        this.Category = Category;
        this.SubCategory = SubCategory;
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

    public String getSubCategory() {
        return SubCategory;
    }
}
