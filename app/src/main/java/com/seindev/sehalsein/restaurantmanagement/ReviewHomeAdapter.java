package com.seindev.sehalsein.restaurantmanagement;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by sehalsein on 01/02/16.
 */
public class ReviewHomeAdapter extends FirebaseRecyclerAdapter<ReviewHomeAdapter.ViewHolder, Review> {

    Context context;

    public ReviewHomeAdapter(Query query, Class<Review> itemClass) {
        super(query, itemClass);
    }

    public ReviewHomeAdapter(Query query, Class<Review> itemClass, @Nullable ArrayList<Review> items, @Nullable ArrayList<String> keys, Context context) {
        super(query, itemClass, items, keys);

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Review item = getItem(position);
        holder.vCustomerName.setText(item.getCustomerid());
        holder.vRatings.setText(item.getRating() + "");
        holder.vRatings.setBackgroundResource(backgroundcolor(item.getRating()));
        holder.vReviews.setText(item.getReview());
        CustomerName(item.getCustomerid(), holder);

    }

    //Displays Customer Name for Review
    private void CustomerName(String CustomerId, final ViewHolder holder) {

        final String[] mCustomerName = {null};

        String FirabaselinkCustomer = context.getResources().getString(R.string.FireBase_Customer_URL);
        final Firebase cRef = new Firebase(FirabaselinkCustomer);
        Query cQuery = cRef.orderByChild("customerid").equalTo(CustomerId);

        cQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Customer post = postSnapshot.getValue(Customer.class);
                    mCustomerName[0] = post.getCustomername();
                }
                holder.vCustomerName.setText(mCustomerName[0]);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    //Background Color For RATINGS
    private int backgroundcolor(float ratings) {

        int bgcolor = R.color.color50;

        //TODO CHECK IF THIS CODE WORKS WITH DECIMAL
        int percentage = (int) ((ratings / 5) * 100);
        percentage = 10 * ((percentage + 9) / 10);
        //Toast.makeText(ItemDetail.this, "PERCENTAGE : " + percentage, Toast.LENGTH_SHORT).show();
        switch (percentage) {
            case 10:
                bgcolor = R.color.color10;
                break;
            case 20:
                bgcolor = R.color.color20;
                break;
            case 30:
                bgcolor = R.color.color30;
                break;
            case 40:
                bgcolor = R.color.color40;
                break;
            case 50:
                bgcolor = R.color.color50;
                break;
            case 60:
                bgcolor = R.color.color60;
                break;
            case 70:
                bgcolor = R.color.color70;
                break;
            case 80:
                bgcolor = R.color.color80;
                break;
            case 90:
                bgcolor = R.color.color90;
                break;
            case 100:
                bgcolor = R.color.color100;
                break;
            default:
                bgcolor = R.color.color0;
        }

        return bgcolor;
    }

    @Override
    protected void itemAdded(Review item, String key, int position) {
        // Log.d("ReviewHomeAdapter", "Added a new item to the adapter.");
    }

    @Override
    protected void itemChanged(Review oldItem, Review newItem, String key, int position) {
        // Log.d("ReviewHomeAdapter", "Changed an item.");
    }

    @Override
    protected void itemRemoved(Review item, String key, int position) {
        //  Log.d("ReviewHomeAdapter", "Removed an item from the adapter.");
    }

    @Override
    protected void itemMoved(Review item, String key, int oldPosition, int newPosition) {
        //  Log.d("ReviewHomeAdapter", "Moved an item.");
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView vCustomerDP;
        TextView vCustomerName, vRatings, vReviews;

        public ViewHolder(View view) {
            super(view);

            vCustomerName = (TextView) view.findViewById(R.id.textcustomername);
            vRatings = (TextView) view.findViewById(R.id.textIndRating);
            vReviews = (TextView) view.findViewById(R.id.textReview);

        }
    }
}
