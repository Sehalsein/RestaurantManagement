package com.seindev.sehalsein.restaurantmanagement;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;

import java.util.ArrayList;

public class ShoppingCartHome extends AppCompatActivity implements ShoppingCart {


    private RecyclerView recyclerView;
    private ShoppingCartAdapter mMyAdapter;
    TextView vTotalAmount;

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

    private void setupFirebase() {
        Firebase.setAndroidContext(this);
        String firebaseLocation = getResources().getString(R.string.FireBase_Order_URL);
        mQuery = new Firebase(firebaseLocation);
        //mQuery.orderByChild("ratings").orderByValue().limitToLast(1);
    }

    private void setUpRecycler() {

        recyclerView = (RecyclerView) findViewById(R.id.order_list_recycler);
        mMyAdapter = new ShoppingCartAdapter(mQuery, Order.class, mAdapterItems, mAdapterKeys);
        mMyAdapter.send(this);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.addItemDecoration();
        recyclerView.setAdapter(mMyAdapter);
    }


    @Override
    public void send(int TotalAmount) {
        vTotalAmount.setText("RS " + TotalAmount);
    }
}
