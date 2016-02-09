package com.seindev.sehalsein.restaurantmanagement;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class AddReviewHome extends AppCompatActivity {

    private EditText vDishId, vCustomerId, vReview, vRatings, vSno;
    private String mCustomerId;
    private String mDishId;
    private String mReview;
    private int mSno;
    private int mRatings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        vDishId = (EditText) findViewById(R.id.editDishId);
        vCustomerId = (EditText) findViewById(R.id.editCustomerId);
        vRatings = (EditText) findViewById(R.id.editRatings);
        vReview = (EditText) findViewById(R.id.editReview);
        vSno = (EditText) findViewById(R.id.editSNo);


    }

    public void addreview(View view) {

        try {
            mCustomerId = String.valueOf(vCustomerId.getText());
            mDishId = String.valueOf(vDishId.getText());
            mReview = String.valueOf(vReview.getText());
            mSno = Integer.parseInt(String.valueOf(vSno.getText()));
            mRatings = Integer.parseInt(String.valueOf(vRatings.getText()));

            if (vSno.getText().toString().trim().length() <= 0) {
                mSno = 0;
            }
            if (vDishId.getText().toString().trim().length() <= 0) {
                mDishId = "";
            }
            if (vReview.getText().toString().trim().length() <= 0) {
                mReview = "";
            }
            if (vCustomerId.getText().toString().trim().length() <= 0) {
                mCustomerId = "";
            }
            if (vRatings.getText().toString().trim().length() <= 0) {
                mRatings = 0;
            }

            Firebase.setAndroidContext(this);
            String mFirebaseUrl = getResources().getString(R.string.FireBase_Review_URL);
            Firebase firebase = new Firebase(mFirebaseUrl);
            Firebase mRef = firebase.child(mDishId).child(mSno + "");

            if (mDishId == "" || mReview == "" || mCustomerId == "" || mSno == 0) {
                Toast.makeText(this, "FIELD EMPTY ", Toast.LENGTH_LONG).show();
            } else {
                Review review = new Review(mCustomerId, mDishId, mReview, mSno, mRatings);

                mRef.setValue(review, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        if (firebaseError != null) {
                            System.out.println("Data could not be saved. " + firebaseError.getMessage());
                            Toast.makeText(AddReviewHome.this, "ITEM Data could not be saved. ", Toast.LENGTH_LONG).show();
                        } else {
                            System.out.println("Data saved successfully.");
                            Toast.makeText(AddReviewHome.this, "Data saved successfully.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }


        } catch (NullPointerException e) {
            System.out.print("" + e);
        }


    }

}
