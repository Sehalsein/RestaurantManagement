package com.seindev.sehalsein.restaurantmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class ItemDetail extends AppCompatActivity {

    TextView vCustomerName, vDescription, vIndRatings, vReview;
    RatingBar vRatings;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        vCustomerName = (TextView) findViewById(R.id.customername);
        vDescription = (TextView) findViewById(R.id.textDescription);
        vIndRatings = (TextView) findViewById(R.id.textIndRating);
        vRatings = (RatingBar) findViewById(R.id.ratingBar);
        vReview = (TextView) findViewById(R.id.textReview);

        String dishid = "K001";
        setupFirebase(dishid);

    }

    public void morereview(View view) {
        startActivity(new Intent(ItemDetail.this, ReviewHome.class));
    }

    private void setupFirebase(String dishId) {

        Firebase.setAndroidContext(this);
        String FirebaselinkMenu = getResources().getString(R.string.FireBase_Menu_URL);
        String FirebaselinkReview = getResources().getString(R.string.FireBase_Review_URL);
        final Firebase mRef = new Firebase(FirebaselinkMenu);
        final Firebase rRef = new Firebase(FirebaselinkReview);

        //MENU TABLE
        Query mQuery = mRef.orderByChild("dishId").equalTo(dishId);
        mQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Menu post = postSnapshot.getValue(Menu.class);

                    vDescription.setText(post.getTags());
                    toolbar.setTitle(post.getDishName());

                    Toast.makeText(ItemDetail.this, "DISH NAME : " + post.getDishName(), Toast.LENGTH_LONG).show();
                    //System.out.println(post.getDishName() + " - " + post.getSNo());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        //REVIEW TABLE
        Query rQuery = rRef.orderByChild("dishid").equalTo(dishId);
        rQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                float avg = 0f;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Review post = postSnapshot.getValue(Review.class);

                    float mRating = post.getRatings();

                    vIndRatings.setBackgroundResource(backgroundcolor(mRating));
                    vCustomerName.setText(post.getCustomerid());
                    vIndRatings.setText(mRating + "");
                    vReview.setText(post.getReview());
                    avg = avg + post.getRatings();
                    i++;
                    vRatings.setRating(ratingscaluclate(avg, i));

                    //Toast.makeText(ItemDetail.this, "RATINGS : " + post.getRatings(), Toast.LENGTH_SHORT).show();
                    //System.out.println(post.getRatings() + " - " + post.getSNo());
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

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

    private float ratingscaluclate(float avgrating, int i) {
        return avgrating / i;
    }


}
