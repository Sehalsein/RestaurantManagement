package com.seindev.sehalsein.restaurantmanagement;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Query;

import java.util.ArrayList;

/**
 * Created by sehalsein on 14/02/16.
 */
public class KitchenItemAdapter extends FirebaseRecyclerAdapter<KitchenItemAdapter.ViewHolder, Order> {

    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView vQuantity, vDishName;
        LinearLayout vInnerLayout;

        public ViewHolder(View view) {
            super(view);
            vDishName = (TextView) view.findViewById(R.id.textDishName);
            vQuantity = (TextView) view.findViewById(R.id.textQuantity);
            vInnerLayout = (LinearLayout) view.findViewById(R.id.innerLayout);

            vInnerLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Toast.makeText(context,"INNER TOUCH",Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }

    }


    public KitchenItemAdapter(Query query, Class<Order> itemClass, @Nullable ArrayList<Order> items,
                              @Nullable ArrayList<String> keys, Context context) {
        super(query, itemClass, items, keys);
        this.context = context;
    }

    @Override
    public KitchenItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kitchen_item_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final KitchenItemAdapter.ViewHolder holder, final int position) {
        final Order item = getItem(position);

        holder.vDishName.setText(item.getDishname());
        holder.vQuantity.setText(item.getQuanity() + "");

        String status = item.getStatus();
        if (status.equals(context.getResources().getString(R.string.NewOrder))) {
            holder.vDishName.setTextColor(context.getResources().getColor(R.color.color10));
        } else if (status.equals(context.getResources().getString(R.string.DoneOrder))) {
            holder.vDishName.setTextColor(context.getResources().getColor(R.color.color100));
        }


    }


    @Override
    protected void itemAdded(Order item, String key, int position) {
        // Log.d("KitchenItemAdapter", "Added a new item to the adapter.");
    }

    @Override
    protected void itemChanged(Order oldItem, Order newItem, String key, int position) {
        // Log.d("KitchenItemAdapter", "Changed an item.");

    }

    @Override
    protected void itemRemoved(Order item, String key, int position) {
        // Log.d("KitchenItemAdapter", "Removed an item from the adapter.");

    }

    @Override
    protected void itemMoved(Order item, String key, int oldPosition, int newPosition) {
        // Log.d("KitchenItemAdapter", "Moved an item.");

    }


}
