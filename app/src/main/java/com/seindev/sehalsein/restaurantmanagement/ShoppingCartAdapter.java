package com.seindev.sehalsein.restaurantmanagement;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.Query;

import java.util.ArrayList;

/**
 * Created by sehalsein on 31/01/16.
 */
public class ShoppingCartAdapter extends FirebaseRecyclerAdapter<ShoppingCartAdapter.ViewHolder, Order> {

    private int vTotalAmount;
    private ShoppingCart shoppingCart;

    public void send(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView vQuantity, vDishName, vPrice, vTotalPrice;

        public ViewHolder(View view) {
            super(view);
            vDishName = (TextView) view.findViewById(R.id.textDishName);
            vQuantity = (TextView) view.findViewById(R.id.textQuantity);
            vPrice = (TextView) view.findViewById(R.id.textPrice);

        }
    }


    public ShoppingCartAdapter(Query query, Class<Order> itemClass, @Nullable ArrayList<Order> items,
                               @Nullable ArrayList<String> keys) {
        super(query, itemClass, items, keys);
    }

    @Override
    public ShoppingCartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ShoppingCartAdapter.ViewHolder holder, final int position) {
        final Order item = getItem(position);

        int totalprice, price, quantity;
        price = item.getPrice();
        quantity = item.getQuantity();
        totalprice = price * quantity;

        vTotalAmount += totalprice;

        holder.vPrice.setText(totalprice + "");
        holder.vDishName.setText(item.getDishName());
        holder.vQuantity.setText(item.getQuantity() + "");

        if (shoppingCart != null) {
            shoppingCart.send(vTotalAmount);
        }


    }


    @Override
    protected void itemAdded(Order item, String key, int position) {
        Log.d("ShoppingCartAdapter", "Added a new item to the adapter.");
    }

    @Override
    protected void itemChanged(Order oldItem, Order newItem, String key, int position) {
        Log.d("ShoppingCartAdapter", "Changed an item.");

    }

    @Override
    protected void itemRemoved(Order item, String key, int position) {
        Log.d("ShoppingCartAdapter", "Removed an item from the adapter.");

    }

    @Override
    protected void itemMoved(Order item, String key, int oldPosition, int newPosition) {
        Log.d("ShoppingCartAdapter", "Moved an item.");

    }


}
