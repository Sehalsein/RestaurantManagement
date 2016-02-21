package com.seindev.sehalsein.restaurantmanagement;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.Query;

import java.util.ArrayList;

public class ReviewHome extends AppCompatActivity {

    //CONSTANT VARIABLE
    private Constant constant;
    private String mDishId;


    //FIREBASE RECYCLER VIEW
    private final static String SAVED_ADAPTER_ITEMS = "SAVED_ADAPTER_ITEMS";
    private final static String SAVED_ADAPTER_KEYS = "SAVED_ADAPTER_KEYS";
    private Query mQuery;
    private ReviewHomeAdapter mAdapter;
    private ArrayList<Review> mAdapterItems;
    private ArrayList<String> mAdapterKeys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        mDishId = constant.getDishid();
        if (mDishId == null) {
            mDishId = getResources().getString(R.string.DishId);
        }

        handleInstanceState(savedInstanceState);
        initFirebase(mDishId);
        initRecyclerView();


    }

    // Restoring the item list and the keys of the items: they will be passed to the adapter
    private void handleInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null &&
                savedInstanceState.containsKey(SAVED_ADAPTER_ITEMS) &&
                savedInstanceState.containsKey(SAVED_ADAPTER_KEYS)) {
            //  mAdapterItems = Parcels.unwrap(savedInstanceState.getParcelable(SAVED_ADAPTER_ITEMS));
            mAdapterKeys = savedInstanceState.getStringArrayList(SAVED_ADAPTER_KEYS);
        } else {
            mAdapterItems = new ArrayList<Review>();
            mAdapterKeys = new ArrayList<String>();
        }
    }

    private void initFirebase(String mDishId) {
        //Firebase.setAndroidContext(this);
        String firebaseLocation = getResources().getString(R.string.FireBase_Review_URL) + "/" + mDishId;
        mQuery = new Firebase(firebaseLocation);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.review_recycler);
        mAdapter = new ReviewHomeAdapter(mQuery, Review.class, mAdapterItems, mAdapterKeys);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(mAdapter);
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
                Toast.makeText(this, "SETTINGS", Toast.LENGTH_LONG).show();
                return true;


            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    // Saving the list of items and keys of the items on rotation
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // outState.putParcelable(SAVED_ADAPTER_ITEMS, Parcels.wrap(mAdapter.getItems()));
        outState.putStringArrayList(SAVED_ADAPTER_KEYS, mAdapter.getKeys());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.destroy();
    }


}