package com.seindev.sehalsein.restaurantmanagement;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddReviewHome extends AppCompatActivity implements View.OnClickListener {

    private EditText vReview, vCustomerName;
    private String mReview;
    private int mSno;

    //DATE
    private Calendar cal;
    private String mDate;

    //VARIABE
    private String mCustomerId;
    private String mCustomerName;
    private int nSno;

    //CONSTANT VARIABLE
    private Constant constant;
    private String mTableId;
    private String mBillId;
    private String mOrderId;
    private String mService;
    private String mDishId;


    //RATINGS
    private ImageView image10, image20, image30, image40, image50, image60, image70, image80, image90, image100;
    private TextView vRating;
    private float mRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        vReview = (EditText) findViewById(R.id.editReview);
        vCustomerName = (EditText) findViewById(R.id.editCustomerName);


        //TODAY'S DATE
        cal = Calendar.getInstance();
        mDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

        mDishId = constant.getDishId();
        if (mDishId == null) {
            mDishId = getResources().getString(R.string.DishId);
        }

        mCustomerId = constant.getCustomerId();
        if (mCustomerId == null) {
            mCustomerId = getResources().getString(R.string.CustomerId);
        }

        //Toast.makeText(this, "Customer ID" + mCustomerId, Toast.LENGTH_SHORT).show();

        //RATINGS
        image10 = (ImageView) findViewById(R.id.image10);
        image20 = (ImageView) findViewById(R.id.image20);
        image30 = (ImageView) findViewById(R.id.image30);
        image40 = (ImageView) findViewById(R.id.image40);
        image50 = (ImageView) findViewById(R.id.image50);
        image60 = (ImageView) findViewById(R.id.image60);
        image70 = (ImageView) findViewById(R.id.image70);
        image80 = (ImageView) findViewById(R.id.image80);
        image90 = (ImageView) findViewById(R.id.image90);
        image100 = (ImageView) findViewById(R.id.image100);
        vRating = (TextView) findViewById(R.id.textRatings);

        image10.setOnClickListener(this);
        image20.setOnClickListener(this);
        image30.setOnClickListener(this);
        image40.setOnClickListener(this);
        image50.setOnClickListener(this);
        image60.setOnClickListener(this);
        image70.setOnClickListener(this);
        image80.setOnClickListener(this);
        image90.setOnClickListener(this);
        image100.setOnClickListener(this);

        initReview();
        initCustomer();

    }

    private void initReview() {

        String mOrderLink = getResources().getString(R.string.FireBase_Review_URL) + "/" + mDishId;
        final Firebase mRef = new Firebase(mOrderLink);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean vExist = dataSnapshot.exists();
                if (vExist) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Review post = postSnapshot.getValue(Review.class);
                        mSno = post.getSno();
                    }
                    ++mSno;
                } else {
                    mSno = 1;
                }

                //Toast.makeText(AddReviewHome.this, "ORDER SNO : " + mSno, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }

    private void initCustomer() {

        String mOrderLink = getResources().getString(R.string.FireBase_Customer_URL);
        final Firebase mRef = new Firebase(mOrderLink);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean vExist = dataSnapshot.exists();
                String nCustomerId = null;
                if (vExist) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Customer post = postSnapshot.getValue(Customer.class);
                        nCustomerId = post.getCustomerid();
                        nSno = post.getSno();
                    }
                    ++nSno;

                } else {
                    nSno = 1;
                }

                //Toast.makeText(AddReviewHome.this, "SNO : " + nSno + "\n CUSOMER ID : " + mCustomerId, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        final Firebase nRef = new Firebase(mOrderLink);
        Query query = nRef.orderByChild("customerid").equalTo("" + mCustomerId);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean vExist = dataSnapshot.exists();
                if (vExist) {
                    --nSno;
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }

    //SUBMIT BUTTON
    public void submit(View view) {
        addReview();
        addCustomer();


    }

    private void addCustomer() {

        mCustomerName = vCustomerName.getText().toString();

        String mFirebaseUrl = getResources().getString(R.string.FireBase_Customer_URL);
        Firebase firebase = new Firebase(mFirebaseUrl);
        Firebase mRef = firebase.child(nSno + "");

        Customer review = new Customer(nSno, mCustomerId, mCustomerName);

        mRef.setValue(review, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                   // Toast.makeText(AddReviewHome.this, "ITEM Data could not be saved. ", Toast.LENGTH_LONG).show();
                } else {
                    System.out.println("Data saved successfully.");
                   // Toast.makeText(AddReviewHome.this, "Data saved successfully.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void addReview() {

        mReview = vReview.getText().toString();

        String mFirebaseUrl = getResources().getString(R.string.FireBase_Review_URL);
        Firebase firebase = new Firebase(mFirebaseUrl);
        Firebase mRef = firebase.child(mDishId).child(mSno + "");

        Review review = new Review(mSno, mCustomerId, mDishId, mRating, mReview, mDate);

        mRef.setValue(review, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                   // Toast.makeText(AddReviewHome.this, "ITEM Data could not be saved. ", Toast.LENGTH_LONG).show();
                } else {
                    System.out.println("Data saved successfully.");
                   // Toast.makeText(AddReviewHome.this, "Data saved successfully.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    //TODO FIND A BETTER EFFICIENT WAY TO DO THIS
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image100:
                clearbackground();
                image100.setBackgroundResource(R.color.color100);
                image90.setBackgroundResource(R.color.color90);
                image80.setBackgroundResource(R.color.color80);
                image70.setBackgroundResource(R.color.color70);
                image60.setBackgroundResource(R.color.color60);
                image50.setBackgroundResource(R.color.color50);
                image40.setBackgroundResource(R.color.color40);
                image30.setBackgroundResource(R.color.color30);
                image20.setBackgroundResource(R.color.color20);
                image10.setBackgroundResource(R.color.color10);
                mRating = 5f;
                vRating.setText(mRating + "");
                break;
            case R.id.image90:
                clearbackground();
                image90.setBackgroundResource(R.color.color90);
                image80.setBackgroundResource(R.color.color80);
                image70.setBackgroundResource(R.color.color70);
                image60.setBackgroundResource(R.color.color60);
                image50.setBackgroundResource(R.color.color50);
                image40.setBackgroundResource(R.color.color40);
                image30.setBackgroundResource(R.color.color30);
                image20.setBackgroundResource(R.color.color20);
                image10.setBackgroundResource(R.color.color10);
                mRating = 4.5f;
                vRating.setText(mRating + "");
                break;
            case R.id.image80:
                clearbackground();
                image80.setBackgroundResource(R.color.color80);
                image70.setBackgroundResource(R.color.color70);
                image60.setBackgroundResource(R.color.color60);
                image50.setBackgroundResource(R.color.color50);
                image40.setBackgroundResource(R.color.color40);
                image30.setBackgroundResource(R.color.color30);
                image20.setBackgroundResource(R.color.color20);
                image10.setBackgroundResource(R.color.color10);
                mRating = 4f;
                vRating.setText(mRating + "");
                break;
            case R.id.image70:
                clearbackground();
                image70.setBackgroundResource(R.color.color70);
                image60.setBackgroundResource(R.color.color60);
                image50.setBackgroundResource(R.color.color50);
                image40.setBackgroundResource(R.color.color40);
                image30.setBackgroundResource(R.color.color30);
                image20.setBackgroundResource(R.color.color20);
                image10.setBackgroundResource(R.color.color10);
                mRating = 3.5f;
                vRating.setText(mRating + "");
                break;
            case R.id.image60:
                clearbackground();
                image60.setBackgroundResource(R.color.color60);
                image50.setBackgroundResource(R.color.color50);
                image40.setBackgroundResource(R.color.color40);
                image30.setBackgroundResource(R.color.color30);
                image20.setBackgroundResource(R.color.color20);
                image10.setBackgroundResource(R.color.color10);
                mRating = 3f;
                vRating.setText(mRating + "");
                break;
            case R.id.image50:
                clearbackground();
                image50.setBackgroundResource(R.color.color50);
                image40.setBackgroundResource(R.color.color40);
                image30.setBackgroundResource(R.color.color30);
                image20.setBackgroundResource(R.color.color20);
                image10.setBackgroundResource(R.color.color10);
                mRating = 2.5f;
                vRating.setText(mRating + "");
                break;
            case R.id.image40:
                clearbackground();
                image40.setBackgroundResource(R.color.color40);
                image30.setBackgroundResource(R.color.color30);
                image20.setBackgroundResource(R.color.color20);
                image10.setBackgroundResource(R.color.color10);
                mRating = 2f;
                vRating.setText(mRating + "");
                break;
            case R.id.image30:
                clearbackground();
                image30.setBackgroundResource(R.color.color30);
                image20.setBackgroundResource(R.color.color20);
                image10.setBackgroundResource(R.color.color10);
                mRating = 1.5f;
                vRating.setText(mRating + "");
                break;
            case R.id.image20:
                clearbackground();
                image20.setBackgroundResource(R.color.color20);
                image10.setBackgroundResource(R.color.color10);
                mRating = 1f;
                vRating.setText(mRating + "");
                break;
            case R.id.image10:
                clearbackground();
                image10.setBackgroundResource(R.color.color10);
                mRating = .5f;
                vRating.setText(mRating + "");
                break;
            default:
                clearbackground();
                mRating = 0f;
                // vRating.setText(mRating + "");
        }
    }

    private void clearbackground() {
        image100.setBackgroundResource(R.color.colorSecondaryText);
        image90.setBackgroundResource(R.color.colorSecondaryText);
        image80.setBackgroundResource(R.color.colorSecondaryText);
        image70.setBackgroundResource(R.color.colorSecondaryText);
        image60.setBackgroundResource(R.color.colorSecondaryText);
        image50.setBackgroundResource(R.color.colorSecondaryText);
        image40.setBackgroundResource(R.color.colorSecondaryText);
        image30.setBackgroundResource(R.color.colorSecondaryText);
        image20.setBackgroundResource(R.color.colorSecondaryText);
        image10.setBackgroundResource(R.color.colorSecondaryText);

    }
}
