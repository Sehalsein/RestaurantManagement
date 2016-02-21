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
 * Created by sehalsein on 20/02/16.
 */
public class CashierItemAdapter extends FirebaseRecyclerAdapter<CashierItemAdapter.ViewHolder, Order> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView vQuantity, vDishName;

        public ViewHolder(View view) {
            super(view);
            vDishName = (TextView) view.findViewById(R.id.textDishName);
            vQuantity = (TextView) view.findViewById(R.id.textQuantity);

        }
    }


    public CashierItemAdapter(Query query, Class<Order> itemClass, @Nullable ArrayList<Order> items,
                              @Nullable ArrayList<String> keys) {
        super(query, itemClass, items, keys);
    }

    @Override
    public CashierItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cashier_item_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CashierItemAdapter.ViewHolder holder, final int position) {
        final Order item = getItem(position);

        holder.vDishName.setText(item.getDishname());
        holder.vQuantity.setText(item.getQuanity() + "");
    }


    @Override
    protected void itemAdded(Order item, String key, int position) {
        Log.d("CashierItemAdapter", "Added a new item to the adapter.");
    }

    @Override
    protected void itemChanged(Order oldItem, Order newItem, String key, int position) {
        Log.d("CashierItemAdapter", "Changed an item.");

    }

    @Override
    protected void itemRemoved(Order item, String key, int position) {
        Log.d("CashierItemAdapter", "Removed an item from the adapter.");

    }

    @Override
    protected void itemMoved(Order item, String key, int oldPosition, int newPosition) {
        Log.d("CashierItemAdapter", "Moved an item.");

    }


}
