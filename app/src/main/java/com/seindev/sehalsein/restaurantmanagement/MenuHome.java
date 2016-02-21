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

    //Constant
    private Constant constant;

    //RECYCLER VIEW VARIABLE
    private final static String SAVED_ADAPTER_ITEMS = "SAVED_ADAPTER_ITEMS";
    private final static String SAVED_ADAPTER_KEYS = "SAVED_ADAPTER_KEYS";
    private Query mQuery;
    private MenuHomeAdapter mAdapter;
    private ArrayList<Menu> mAdapterItems;
    private ArrayList<String> mAdapterKeys;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        handleInstanceState(savedInstanceState);

        initFirebase();
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
            mAdapterItems = new ArrayList<Menu>();
            mAdapterKeys = new ArrayList<String>();
        }
    }

    //Initializing FireBase
    private void initFirebase() {

        String firebaseLocation = getResources().getString(R.string.FireBase_Menu_URL);
        //QUERY
        //mQuery = new Firebase(firebaseLocation).orderByChild("category").equalTo("burger");
        mQuery = new Firebase(firebaseLocation);

    }

    //Initializing Recycler View
    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.menu_recycler);
        mAdapter = new MenuHomeAdapter(mQuery, Menu.class, mAdapterItems, mAdapterKeys);
        mAdapter.setMenuClickListener(this);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu_home, menu);

        //TODO SHOPPING CART INCREMENT 
        menu.findItem(R.id.action_settings).setIcon(R.drawable.vector_shopping_cart);

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
        // outState.putParcelable(SAVED_ADAPTER_ITEMS, Parcels.wrap(mAdapter.getItems()));
        outState.putStringArrayList(SAVED_ADAPTER_KEYS, mAdapter.getKeys());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.destroy();
    }

    @Override
    public void itemClicked(View view, String DishId) {
        constant.setDishid(DishId);
        startActivity(new Intent(MenuHome.this, ItemDetail.class));
    }
}