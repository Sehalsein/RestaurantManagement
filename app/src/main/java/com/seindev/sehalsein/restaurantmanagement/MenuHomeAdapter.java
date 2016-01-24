package com.seindev.sehalsein.restaurantmanagement;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Query;

import java.util.ArrayList;

/**
 * Created by sehalsein on 25/11/15.
 */
public class MenuHomeAdapter extends FirebaseRecyclerAdapter<MenuHomeAdapter.ViewHolder, Menu> {

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView vDishIcon;
        TextView vDishName, vCategory;
        Button vPlus, vMinus;

        public ViewHolder(View view) {
            super(view);

            vDishName = (TextView) view.findViewById(R.id.textDishName);
            vCategory = (TextView) view.findViewById(R.id.textCategory);
            vDishIcon = (ImageView) view.findViewById(R.id.imageDishIcon);
            vMinus = (Button) view.findViewById(R.id.buttonminus);
            vPlus = (Button) view.findViewById(R.id.buttonplus);

        }
    }

    public MenuHomeAdapter(Query query, Class<Menu> itemClass, @Nullable ArrayList<Menu> items,
                           @Nullable ArrayList<String> keys) {
        super(query, itemClass, items, keys);
    }

    @Override
    public MenuHomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuHomeAdapter.ViewHolder holder, int position) {
        Menu item = getItem(position);
//        holder.vCategory.setText(item.getCategory());
        holder.vDishName.setText(item.getDishName());
    }

    @Override
    protected void itemAdded(Menu item, String key, int position) {
        Log.d("MenuHomeAdapter", "Added a new item to the adapter.");
    }

    @Override
    protected void itemChanged(Menu oldItem, Menu newItem, String key, int position) {
        Log.d("MenuHomeAdapter", "Changed an item.");
    }

    @Override
    protected void itemRemoved(Menu item, String key, int position) {
        Log.d("MenuHomeAdapter", "Removed an item from the adapter.");
    }

    @Override
    protected void itemMoved(Menu item, String key, int oldPosition, int newPosition) {
        Log.d("MenuHomeAdapter", "Moved an item.");
    }
}
