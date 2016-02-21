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


    //CONSTANT VARIABLE
    private Constant constant;
    private String mTableId;
    private String mOrderId;
    private String mService;
    private String mDishId;

    //DESIGN VARIABLE
    private TextView vCustomerName, vDescription, vIndRatings, vReview, vQuantity, vSpicyLevel;
    private LinearLayout vLayoutReview, vLayoutNoReview;
    private RatingBar vRatings;
    private Toolbar toolbar;

    //Check Variables
    private boolean vReviewExist;
    private boolean vOrderExist;


    //Variable
    private int mSno = 0;
    private int mQuantity = 0;
    private String mDishName;
    private int mPrice;


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

        //Initializing 
        vCustomerName = (TextView) findViewById(R.id.customername);
        vDescription = (TextView) findViewById(R.id.textDescription);
        vIndRatings = (TextView) findViewById(R.id.textIndRating);
        vRatings = (RatingBar) findViewById(R.id.ratingBar);
        vSpicyLevel = (TextView) findViewById(R.id.textSpicyLevel);
        vReview = (TextView) findViewById(R.id.textReview);
        vQuantity = (TextView) findViewById(R.id.test);
        vLayoutNoReview = (LinearLayout) findViewById(R.id.layoutNoReview);
        vLayoutReview = (LinearLayout) findViewById(R.id.layoutReview);

        mDishId = constant.getDishid();
        if (mDishId == null) {
            mDishId = getResources().getString(R.string.DishId);
        }

        mTableId = constant.getTableId();
        if (mTableId == null) {
            mTableId = getResources().getString(R.string.TableId);
        }

        mOrderId = constant.getOrderId();
        if (mOrderId == null) {
            mOrderId = getResources().getString(R.string.OrderId);
        }

        initMenuDetail(mDishId);
        initReviewDetail(mDishId);
        initOrderDetail(mDishId, mTableId);


    }

    //Displays Order Ind
    private void initOrderDetail(String mDishId, String mTableId) {

        String mOrderLink = getResources().getString(R.string.FireBase_Order_URL) + "/" + mTableId;
        final Firebase oRef = new Firebase(mOrderLink);

        //Checks the Serial Number of the previous Order
        oRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("There are " + snapshot.getChildrenCount() + " Order");
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Order post = postSnapshot.getValue(Order.class);
                    mSno = post.getSno();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        //Checks weather the Order exist
        System.out.println("ORDER TEST : " + mDishId);
        Query oQuery = oRef.orderByChild("dishid").equalTo(mDishId);
        oQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                vOrderExist = dataSnapshot.exists();
                if (vOrderExist) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Order post = postSnapshot.getValue(Order.class);
                        vQuantity.setText(post.getQuanity() + "");
                        mQuantity = post.getQuanity();
                        mSno = post.getSno();
                        System.out.println("ORDER TEST : TRUE");
                    }
                } else {
                    System.out.println("ORDER TEST : FALSE");
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

    //Displaying Single Review and Ratings
    private void initReviewDetail(String mDishId) {

        String mReviewLink = getResources().getString(R.string.FireBase_Review_URL) + "/" + mDishId;
        final Firebase rRef = new Firebase(mReviewLink);

        rRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                vReviewExist = dataSnapshot.exists();
                if (vReviewExist) {
                    vLayoutNoReview.setVisibility(View.INVISIBLE);
                    int count = (int) dataSnapshot.getChildrenCount();
                    float ratings = 0f;
                    String customerid = "";
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Review post = postSnapshot.getValue(Review.class);
                        float mRating = post.getRatings();
                        vIndRatings.setBackgroundResource(backgroundcolor(mRating));
                        customerid = post.getCustomerid();
                        vIndRatings.setText(mRating + "");
                        vReview.setText(post.getReview());
                        ratings = ratings + post.getRatings();
                        vRatings.setRating(ratingscaluclate(ratings, count));
                    }
                    CustomerName(customerid);
                } else {
                    vLayoutReview.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    //Displaying Dish Detail
    private void initMenuDetail(final String mDishId) {
        String mMenuLink = getResources().getString(R.string.FireBase_Menu_URL);
        final Firebase mRef = new Firebase(mMenuLink);

        Query mQuery = mRef.orderByChild("dishId").equalTo(mDishId);
        mQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Menu post = postSnapshot.getValue(Menu.class);
                    mPrice = Integer.parseInt(post.getPrice());
                    mDishName = post.getDishName();
                    vDescription.setText(post.getTags());
                    toolbar.setTitle(post.getDishName());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());

            }
        });

    }

    //MORE REVIEW FIX
    public void morereview(View view) {
        if (vReviewExist) {
            startActivity(new Intent(ItemDetail.this, ReviewHome.class));
        }

    }

    //Displays Customer Name for Review
    private void CustomerName(String CustomerId) {

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

    //Ratings Calculation
    private float ratingscaluclate(float avgrating, int i) {
        return avgrating / i;
    }

    public void buttonplus(View view) {

        mQuantity++;

        String FirebaselinkOrder = getResources().getString(R.string.FireBase_Order_URL);
        Firebase mref = new Firebase(FirebaselinkOrder);
        Firebase Ref = mref.child(mTableId).child("" + mSno);
        Order order = new Order(mSno, mOrderId, mDishId, mDishName, mQuantity, mPrice, mTableId);
        Ref.setValue(order, new Firebase.CompletionListener() {
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

        String FirebaselinkOrderDetail = getResources().getString(R.string.FireBase_OrderDetail_URL);
        Firebase mRef = new Firebase(FirebaselinkOrderDetail);
        Firebase ref = mRef.child("" + mSno);
        Order orderdetail = new Order(mSno, mOrderId, mDishId, mDishName, mQuantity, mPrice, mTableId);
        ref.setValue(orderdetail, new Firebase.CompletionListener() {
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

    public void buttonminus(View view) {

        if (mQuantity > 0) {
            --mQuantity;

            String FirebaselinkOrder = getResources().getString(R.string.FireBase_Order_URL);

            Firebase mref = new Firebase(FirebaselinkOrder);
            Firebase Ref = mref.child(mTableId).child("" + mSno);
            Order menu = new Order(mSno, mOrderId, mDishId, mDishName, mQuantity, mPrice, mTableId);
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

            String FirebaselinkOrderDetail = getResources().getString(R.string.FireBase_OrderDetail_URL);
            Firebase mRef = new Firebase(FirebaselinkOrderDetail);
            Firebase ref = mRef.child("" + mSno);
            Order orderdetail = new Order(mSno, mOrderId, mDishId, mDishName, mQuantity, mPrice, mTableId);
            ref.setValue(orderdetail, new Firebase.CompletionListener() {
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

            //Deleting the entry when quantity = 0 
            if (mQuantity == 0) {
                String OrderDetail = getResources().getString(R.string.FireBase_OrderDetail_URL) + "/" + mSno;
                String Order = getResources().getString(R.string.FireBase_Order_URL) + "/" + mTableId + "/" + mSno;
                Firebase mDelete = new Firebase(OrderDetail);
                Firebase mOrderDelete = new Firebase(Order);
                mOrderDelete.removeValue();
                mDelete.removeValue();
            }


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
