package com.seindev.sehalsein.restaurantmanagement;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.Query;

import java.util.ArrayList;

/**
 * Created by sehalsein on 21/03/16.
 */
public class AdminMenuHomeAdapter extends FirebaseRecyclerAdapter<AdminMenuHomeAdapter.ViewHolder, Menu> {

    private MenuClickListener menuClickListener;
    private Constant constant;
    private Context context;
    private Typeface caveat, greatvibes, lobster, alexbrush, milonga;


    public void setMenuClickListener(MenuClickListener menuClickListener) {
        this.menuClickListener = menuClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button vEdit, vDelete;
        TextView vDishName;

        public ViewHolder(View view) {
            super(view);
            vDishName = (TextView) view.findViewById(R.id.textDishName);
            vEdit = (Button) view.findViewById(R.id.buttonEdit);
            vDelete = (Button) view.findViewById(R.id.buttonDelete);
        }


    }

    public AdminMenuHomeAdapter(Query query, Class<Menu> itemClass, @Nullable ArrayList<Menu> items,
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
    public AdminMenuHomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu_list_admin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdminMenuHomeAdapter.ViewHolder holder, final int position) {
        final Menu item = getItem(position);
        holder.vDishName.setText(item.getDishname());
        holder.vDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage("Are you sure you want to delete?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(context, "Successfully deleted!!", Toast.LENGTH_LONG).show();
                                String Order = context.getResources().getString(R.string.FireBase_Menu_URL) + "/" + item.getSno();
                                Firebase mOrderDelete = new Firebase(Order);
                                mOrderDelete.removeValue();
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(context, "Not deleted!!", Toast.LENGTH_LONG).show();
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });
        holder.vEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                constant.setDishId(item.getDishid());
                Toast.makeText(context, "EDIT" + constant.getDishId(), Toast.LENGTH_LONG).show();
                context.startActivity(new Intent(context, UpdateItemHome.class));
            }
        });
    }

    @Override
    protected void itemAdded(Menu item, String key, int position) {
        //Log.d("AdminMenuHomeAdapter", "Added a new item to the adapter.");
    }

    @Override
    protected void itemChanged(Menu oldItem, Menu newItem, String key, int position) {
        // Log.d("AdminMenuHomeAdapter", "Changed an item.");
    }

    @Override
    protected void itemRemoved(Menu item, String key, int position) {
        //Log.d("AdminMenuHomeAdapter", "Removed an item from the adapter.");
    }

    @Override
    protected void itemMoved(Menu item, String key, int oldPosition, int newPosition) {
        //Log.d("AdminMenuHomeAdapter", "Moved an item.");
    }
}
