package com.seindev.sehalsein.restaurantmanagement;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.firebase.client.Firebase;
import com.firebase.client.Query;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuHome extends AppCompatActivity implements MenuClickListener {

    private final static String SAVED_ADAPTER_ITEMS = "SAVED_ADAPTER_ITEMS";
    private final static String SAVED_ADAPTER_KEYS = "SAVED_ADAPTER_KEYS";

    private Query mQuery;
    private MenuHomeAdapter mMyAdapter;
    private ArrayList<Menu> mAdapterItems;
    private ArrayList<String> mAdapterKeys;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        handleInstanceState(savedInstanceState);

        //mMyAdapter = new MenuHomeAdapter(this);
        setupFirebase();
        setupRecyclerview();
    }

    // Restoring the item list and the keys of the items: they will be passed to the adapter
    private void handleInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null &&
                savedInstanceState.containsKey(SAVED_ADAPTER_ITEMS) &&
                savedInstanceState.containsKey(SAVED_ADAPTER_KEYS)) {
            //  mAdapterItems = Parcels.unwrap(savedInstanceState.getParcelable(SAVED_ADAPTER_ITEMS));
            mAdapterKeys = savedInstanceState.getStringArrayList(SAVED_ADAPTER_KEYS);
        } else {
            mAdapterItems = new ArrayList<Menu>();
            mAdapterKeys = new ArrayList<String>();
        }
    }

    private void setupFirebase() {
        Firebase.setAndroidContext(this);
        String firebaseLocation = getResources().getString(R.string.FireBase_Menu_URL);
        mQuery = new Firebase(firebaseLocation);
        //mQuery.orderByChild("ratings").orderByValue().limitToLast(1);
    }

    private void setupRecyclerview() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.menu_recycler);
        mMyAdapter = new MenuHomeAdapter(mQuery, Menu.class, mAdapterItems, mAdapterKeys);
        mMyAdapter.setMenuClickListener(this);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayout);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mMyAdapter);
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
                startActivity(new Intent(MenuHome.this, ShoppingCartHome.class));
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
        // outState.putParcelable(SAVED_ADAPTER_ITEMS, Parcels.wrap(mMyAdapter.getItems()));
        outState.putStringArrayList(SAVED_ADAPTER_KEYS, mMyAdapter.getKeys());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMyAdapter.destroy();
    }

    @Override
    public void itemClicked(View view, String DishId) {

        Intent intent = new Intent(MenuHome.this, ItemDetail.class);
        intent.putExtra("DishId", DishId);
        startActivity(intent);
    }
}