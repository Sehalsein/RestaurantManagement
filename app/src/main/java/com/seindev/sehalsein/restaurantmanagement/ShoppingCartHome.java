package com.seindev.sehalsein.restaurantmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ShoppingCartHome extends AppCompatActivity implements ShoppingCart {


    private RecyclerView recyclerView;
    private ShoppingCartAdapter mMyAdapter;
    private TextView vTotalAmount;

    private String mBillId, mbillid;
    private String mTableNo;
    private int mTotalAmount;
    private int mBillSno = 1;

    private final static String SAVED_ADAPTER_ITEMS = "SAVED_ADAPTER_ITEMS";
    private final static String SAVED_ADAPTER_KEYS = "SAVED_ADAPTER_KEYS";

    private Query mQuery;
    private ArrayList<Order> mAdapterItems;
    private ArrayList<String> mAdapterKeys;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        handleInstanceState(savedInstanceState);

        Intent intent = getIntent();
        mTableNo = intent.getStringExtra("TableNo");
        mbillid = intent.getStringExtra("BillNo");

        Toast.makeText(this, "Table NO : " + mTableNo, Toast.LENGTH_LONG).show();

        setupFirebase();
        setUpRecycler();

        vTotalAmount = (TextView) findViewById(R.id.textTotalAmount);

    }


    // Restoring the item list and the keys of the items: they will be passed to the adapter
    private void handleInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null &&
                savedInstanceState.containsKey(SAVED_ADAPTER_ITEMS) &&
                savedInstanceState.containsKey(SAVED_ADAPTER_KEYS)) {
            //  mAdapterItems = Parcels.unwrap(savedInstanceState.getParcelable(SAVED_ADAPTER_ITEMS));
            mAdapterKeys = savedInstanceState.getStringArrayList(SAVED_ADAPTER_KEYS);
        } else {
            mAdapterItems = new ArrayList<Order>();
            mAdapterKeys = new ArrayList<String>();
        }
    }

    @Override
    public void send(int TotalAmount, String BillId, String TableNo) {
        mTotalAmount = TotalAmount;
        mBillId = BillId;
        mTableNo = TableNo;
        vTotalAmount.setText("RS " + TotalAmount);
    }

    private void setupFirebase() {
        Firebase.setAndroidContext(this);
        String firebaseLocation = getResources().getString(R.string.FireBase_Order_URL) + "/" + mTableNo;
        mQuery = new Firebase(firebaseLocation);

        String BillLink = getResources().getString(R.string.FireBase_Bill_URL);
        Firebase mRef = new Firebase(BillLink);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean vExist = snapshot.exists();
                if (vExist) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Bill post = postSnapshot.getValue(Bill.class);
                        mBillSno = post.getSno();
                        mbillid = post.getBillNo();
                    }
                } else {
                    mBillSno = 1;
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    private void setUpRecycler() {

        recyclerView = (RecyclerView) findViewById(R.id.order_list_recycler);
        mMyAdapter = new ShoppingCartAdapter(mQuery, Order.class, mAdapterItems, mAdapterKeys);
        mMyAdapter.send(this);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mMyAdapter);
    }


    public void order(View view) {

        Firebase.setAndroidContext(this);

        if (!mbillid.equals(mBillId)) {
            ++mBillSno;
        }

        String FireBaseLink = getResources().getString(R.string.FireBase_URL);
        Firebase mRef = new Firebase(FireBaseLink);
        Firebase Ref = mRef.child("Bill").child(mBillSno + "");

        Date cDate = new Date();
        String mDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);

        Bill bill = new Bill(mBillSno, mBillId, mTotalAmount, mTableNo, mDate);

        Ref.setValue(bill, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                    Toast.makeText(ShoppingCartHome.this, "ITEM Data could not be saved. ", Toast.LENGTH_LONG).show();
                } else {
                    System.out.println("Data saved successfully.");
                    Toast.makeText(ShoppingCartHome.this, "Data saved successfully.", Toast.LENGTH_LONG).show();
                }
            }
        });

        Firebase aRef = mRef.child("Open").child(mBillSno + "");
        Order order = new Order(mTableNo, mBillId, mBillSno);
        aRef.setValue(order, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                    Toast.makeText(ShoppingCartHome.this, "ITEM Data could not be saved. ", Toast.LENGTH_LONG).show();
                } else {
                    System.out.println("Data saved successfully.");
                    Toast.makeText(ShoppingCartHome.this, "Data saved successfully.", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
