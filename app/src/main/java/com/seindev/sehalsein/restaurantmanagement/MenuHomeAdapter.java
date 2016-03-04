package com.seindev.sehalsein.restaurantmanagement;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.client.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sehalsein on 25/11/15.
 */
public class MenuHomeAdapter extends FirebaseRecyclerAdapter<MenuHomeAdapter.ViewHolder, Menu> {

    private MenuClickListener menuClickListener;

    private Context context;
    private Typeface caveat, greatvibes, lobster, alexbrush, milonga;


    public void setMenuClickListener(MenuClickListener menuClickListener) {
        this.menuClickListener = menuClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView vDishIcon;
        TextView vDishName, vCategory, vItemQuantity, vIngredients;
        RelativeLayout vMenuCard;

        public ViewHolder(View view) {
            super(view);
            vMenuCard = (RelativeLayout) view.findViewById(R.id.menu_card);
            vDishName = (TextView) view.findViewById(R.id.textDishName);
            //vIngredients = (TextView) view.findViewById(R.id.textIngredients);
            vDishIcon = (ImageView) view.findViewById(R.id.imageDishIcon);

        }


    }

    public MenuHomeAdapter(Query query, Class<Menu> itemClass, @Nullable ArrayList<Menu> items,
                           @Nullable ArrayList<String> keys, Context context) {
        super(query, itemClass, items, keys);
        this.context = context;

        //Initialize the fonts here from assets folder created TODO FONT
        caveat = Typeface.createFromAsset(context.getAssets(), "fonts/Caveat-Regular.ttf");
        greatvibes = Typeface.createFromAsset(context.getAssets(), "fonts/Lobster.ttf");
        lobster = Typeface.createFromAsset(context.getAssets(), "fonts/GreatVibes-Regular.ttf");
        alexbrush = Typeface.createFromAsset(context.getAssets(), "fonts/AlexBrush-Regular.ttf");
        milonga = Typeface.createFromAsset(context.getAssets(), "fonts/Milonga-Regular.ttf");

    }

    @Override
    public MenuHomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MenuHomeAdapter.ViewHolder holder, final int position) {
        final Menu item = getItem(position);
        //holder.vIngredients.setText(item.getCategory());
        holder.vDishName.setText(item.getDishname());

//        holder.vIngredients.setTypeface(greatvibes);
        holder.vDishName.setTypeface(milonga);

        //Picasso.with(context).load(R.drawable.rice).into(holder.vDishIcon);
        Picasso.with(context).load(R.drawable.noimage).into(holder.vDishIcon);


        holder.vMenuCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuClickListener != null) {
                    menuClickListener.itemClicked(v, item.getDishid());
                }
            }
        });


    }

    @Override
    protected void itemAdded(Menu item, String key, int position) {
        //Log.d("MenuHomeAdapter", "Added a new item to the adapter.");
    }

    @Override
    protected void itemChanged(Menu oldItem, Menu newItem, String key, int position) {
        // Log.d("MenuHomeAdapter", "Changed an item.");
    }

    @Override
    protected void itemRemoved(Menu item, String key, int position) {
        //Log.d("MenuHomeAdapter", "Removed an item from the adapter.");
    }

    @Override
    protected void itemMoved(Menu item, String key, int oldPosition, int newPosition) {
        //Log.d("MenuHomeAdapter", "Moved an item.");
    }
}
