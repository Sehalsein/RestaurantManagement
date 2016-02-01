package com.seindev.sehalsein.restaurantmanagement;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Query;

import java.util.ArrayList;

/**
 * Created by sehalsein on 01/02/16.
 */
public class ReviewHomeAdapter extends FirebaseRecyclerAdapter<ReviewHomeAdapter.ViewHolder, Review> {

    public ReviewHomeAdapter(Query query, Class<Review> itemClass) {
        super(query, itemClass);
    }

    public ReviewHomeAdapter(Query query, Class<Review> itemClass, @Nullable ArrayList<Review> items, @Nullable ArrayList<String> keys) {
        super(query, itemClass, items, keys);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Review item = getItem(position);
        holder.vCustomerName.setText(item.getCustomerid());
        holder.vRatings.setText(item.getRatings() + "");
        holder.vReviews.setText(item.getReview());
    }

    @Override
    protected void itemAdded(Review item, String key, int position) {
        Log.d("ReviewHomeAdapter", "Added a new item to the adapter.");
    }

    @Override
    protected void itemChanged(Review oldItem, Review newItem, String key, int position) {
        Log.d("ReviewHomeAdapter", "Changed an item.");
    }

    @Override
    protected void itemRemoved(Review item, String key, int position) {
        Log.d("ReviewHomeAdapter", "Removed an item from the adapter.");
    }

    @Override
    protected void itemMoved(Review item, String key, int oldPosition, int newPosition) {
        Log.d("ReviewHomeAdapter", "Moved an item.");
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView vCustomerDP;
        TextView vCustomerName, vRatings, vReviews;

        public ViewHolder(View view) {
            super(view);

            vCustomerName = (TextView) view.findViewById(R.id.textCustomerName);
            vRatings = (TextView) view.findViewById(R.id.textRatings);
            vReviews = (TextView) view.findViewById(R.id.textReview);

        }
    }
}
