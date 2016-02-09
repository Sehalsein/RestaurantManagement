package com.seindev.sehalsein.restaurantmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class ItemDetail extends AppCompatActivity {

    private TextView vCustomerName, vDescription, vIndRatings, vReview, vQuantity, vSpicyLevel;
    private LinearLayout vLayoutReview, vLayoutNoReview;
    private RatingBar vRatings;
    private Toolbar toolbar;

    boolean vReviewExist;
    boolean vOrderExist;
    String mDishName, mDishId;

    int mQuantity = 0;
    int mPrice;
    int mSno = 0;
    String dishid;
    String mBillNo = "B001";
    String mTableNo = "T001";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        vCustomerName = (TextView) findViewById(R.id.customername);
        vDescription = (TextView) findViewById(R.id.textDescription);
        vIndRatings = (TextView) findViewById(R.id.textIndRating);
        vRatings = (RatingBar) findViewById(R.id.ratingBar);

        vSpicyLevel = (TextView) findViewById(R.id.textSpicyLevel);
        vReview = (TextView) findViewById(R.id.textReview);
        vQuantity = (TextView) findViewById(R.id.test);
        vLayoutNoReview = (LinearLayout) findViewById(R.id.layoutNoReview);
        vLayoutReview = (LinearLayout) findViewById(R.id.layoutReview);

        Intent intent = getIntent();
        dishid = intent.getStringExtra("DishId");
        if (dishid == null) {
            dishid = "K001";
        }

        //Toast.makeText(this, "DISH ID ITEM : " + dishid, Toast.LENGTH_LONG).show();
        setupFirebase(dishid);

    }

    public void morereview(View view) {
        if (vReviewExist) {
            Intent intent = new Intent(ItemDetail.this, ReviewHome.class);
            intent.putExtra("DishId", dishid);
            startActivity(intent);

        }

    }

    private void setupFirebase(String dishId) {

        Firebase.setAndroidContext(this);
        String FirebaselinkMenu = getResources().getString(R.string.FireBase_Menu_URL);
        String FirabaseLinkCustoemr = getResources().getString(R.string.FireBase_Customer_URL);
        String FirebaselinkReview = getResources().getString(R.string.FireBase_Review_URL) + "/" + dishId;
        String FirebaselinkOrder = getResources().getString(R.string.FireBase_Order_URL);

        final Firebase mRef = new Firebase(FirebaselinkMenu);
        final Firebase rRef = new Firebase(FirebaselinkReview);
        final Firebase oRef = new Firebase(FirebaselinkOrder);

        //final Firebase cRef = new Firebase(FirabaseLinkCustoemr);

        //MENU TABLE
        Query mQuery = mRef.orderByChild("dishId").equalTo(dishId);
        mQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Menu post = postSnapshot.getValue(Menu.class);
                    mDishName = post.getDishName();
                    mDishId = post.getDishId();
                    mPrice = Integer.parseInt(post.getPrice());
                    vDescription.setText(post.getTags());
                    toolbar.setTitle(post.getDishName());

                    //Toast.makeText(ItemDetail.this, "DISH NAME : " + post.getDishName(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());

            }
        });

        //REVIEW TABLE
        //Query rQuery = rRef.orderByChild("dishid").equalTo(dishId);
        rRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                vReviewExist = dataSnapshot.exists();
                if (vReviewExist) {
                    vLayoutNoReview.setVisibility(View.INVISIBLE);
                    int i = 0;
                    float avg = 0f;
                    String customerid = "";
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Review post = postSnapshot.getValue(Review.class);
                        float mRating = post.getRatings();
                        vIndRatings.setBackgroundResource(backgroundcolor(mRating));
                        customerid = post.getCustomerid();
                        vIndRatings.setText(mRating + "");
                        vReview.setText(post.getReview());
                        avg = avg + post.getRatings();
                        //vSpicyLevel.setText(post.getRatings());
                        i++;
                        vRatings.setRating(ratingscaluclate(avg, i));

                        //Toast.makeText(ItemDetail.this, "Exists : " + exist, Toast.LENGTH_SHORT).show();
                        //System.out.println(post.getRatings() + " - " + post.getSNo());
                    }
                    CustomerName(customerid);
                } else {
                    //Toast.makeText(ItemDetail.this, "Exists : " + exist, Toast.LENGTH_SHORT).show();
                    vLayoutReview.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        //ORDER TABLE
        oRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("There are " + snapshot.getChildrenCount() + " blog posts");
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Order post = postSnapshot.getValue(Order.class);
                    mSno = post.getSno();

                    // Toast.makeText(ItemDetail.this, "Exists : " + mSno, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        //ORDER TABLE
        Query oQuery = oRef.orderByChild("dishId").equalTo(dishId);
        oQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                vOrderExist = dataSnapshot.exists();
                if (vOrderExist) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Order post = postSnapshot.getValue(Order.class);
                        vQuantity.setText(post.getQuantity() + "");
                        mQuantity = post.getQuantity();
                        mSno = post.getSno();
                    }
                } else {
                    mQuantity = 0;
                    ++mSno;
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


    }

    private void CustomerName(String CustomerId) {
        //CUSTOMER TABLE
        //Toast.makeText(ItemDetail.this, "CustomerId : " + CustomerId, Toast.LENGTH_SHORT).show();
        Firebase.setAndroidContext(this);
        String FirabaselinkCustomer = getResources().getString(R.string.FireBase_Customer_URL);
        final Firebase cRef = new Firebase(FirabaselinkCustomer);
        Query cQuery = cRef.orderByChild("customerId").equalTo(CustomerId);

        cQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Customer post = postSnapshot.getValue(Customer.class);
                    vCustomerName.setText(post.getCustomerName());
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

    public void buttonplus(View view) {

        mQuantity++;

        Firebase.setAndroidContext(this);
        String FirebaselinkOrder = getResources().getString(R.string.FireBase_Order_URL);

        Firebase mref = new Firebase("https://restaurant-managment.firebaseio.com");
        Firebase Ref = mref.child("Order").child("" + mSno);
        Order menu = new Order(mSno, mBillNo, mDishId, mDishName, mQuantity, mPrice, mTableNo);
        Ref.setValue(menu, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                    //Toast.makeText(.this, "ITEM Data could not be saved. ", Toast.LENGTH_LONG).show();
                } else {
                    System.out.println("Data saved successfully.");
                    //Toast.makeText(AddItemHome.this, "Data saved successfully.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //TODO DO 0 QUANTITY REMOVE ENTRY
    public void buttonminus(View view) {

        if (mQuantity > 0) {
            --mQuantity;
            Firebase.setAndroidContext(this);
            String FirebaselinkOrder = getResources().getString(R.string.FireBase_Order_URL);

            Firebase mref = new Firebase("https://restaurant-managment.firebaseio.com");
            Firebase Ref = mref.child("Order").child("" + mSno);
            Order menu = new Order(mSno, mBillNo, mDishId, mDishName, mQuantity, mPrice, mTableNo);
            Ref.setValue(menu, new Firebase.CompletionListener() {
                @Override
                public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                    if (firebaseError != null) {
                        System.out.println("Data could not be saved. " + firebaseError.getMessage());
                        //Toast.makeText(.this, "ITEM Data could not be saved. ", Toast.LENGTH_LONG).show();
                    } else {
                        System.out.println("Data saved successfully.");
                        //Toast.makeText(AddItemHome.this, "Data saved successfully.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(ItemDetail.this, ShoppingCartHome.class));
                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
