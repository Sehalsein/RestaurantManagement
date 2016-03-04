package com.seindev.sehalsein.restaurantmanagement;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuHome extends AppCompatActivity implements MenuClickListener {

    //Constant
    private Constant constant;
    private String mCategory = null;
    private String mTableId = null;
    private String mOrderId = null;


    //CHECKING
    private String nTableId = null;
    private String nOrderId = null;
    private String jOrderId = null;

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

        mCategory = constant.getCategory();
        mTableId = constant.getTableId();
        mOrderId = constant.getOrderId();

        initFirebase();
        initRecyclerView();
        initActive();
    }

    //FORMATES ORDERNO TO ORDERID
    public String OrderNumberCalc(int num) {

        String orderno = getResources().getString(R.string.OrderCode) + new DecimalFormat("000").format(++num);
        return orderno;

    }

    //CALCULATES THE ORDERNO
    public static String OrderNumberOnly(final String orderno) {
        final StringBuilder sb = new StringBuilder(orderno.length());
        for (int i = 0; i < orderno.length(); i++) {
            final char c = orderno.charAt(i);
            if (c > 47 && c < 58) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private void initActive() {

        final String mOrderLink = getResources().getString(R.string.FireBase_Active_URL);

        final Firebase oRef = new Firebase(mOrderLink);

        //GET THE TABLE ID FOR ORDER NO
        Query mQuery = oRef.orderByChild("orderid").equalTo(mOrderId).limitToFirst(1);
        mQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Active post = postSnapshot.getValue(Active.class);
                    nTableId = post.getTableid();
                    nOrderId = post.getOrderid();
                    //constant.setOrderId(post.getOrderid());
                    System.out.println("nTABLE  ID :" + nTableId);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


        Query cQuery = oRef.orderByChild("tableid").equalTo(mTableId);
        cQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Active post = postSnapshot.getValue(Active.class);
                    if (nTableId.equals(post.getTableid())) {
                        constant.setOrderId(nOrderId);
                    } else {
                        final int result = Integer.parseInt(OrderNumberOnly(post.getOrderid()));
                        jOrderId = OrderNumberCalc(result);
                        constant.setOrderId(jOrderId);

                    }

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


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
        if (mCategory == null) {
            Toast.makeText(this, "FASLE" + mCategory, Toast.LENGTH_LONG).show();
            mQuery = new Firebase(firebaseLocation);

        } else {
            Toast.makeText(this, "TRYU" + mCategory, Toast.LENGTH_LONG).show();
            mQuery = new Firebase(firebaseLocation).orderByChild("category").equalTo("Seafood");
        }

    }

    //Initializing Recycler View
    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.menu_recycler);
        mAdapter = new MenuHomeAdapter(mQuery, Menu.class, mAdapterItems, mAdapterKeys, this);
        mAdapter.setMenuClickListener(this);


        DisplayMetrics metrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
        if (diagonalInches >= 6.5) {
            //Toast.makeText(this, "Screen size is large", Toast.LENGTH_LONG).show();
            GridLayoutManager linearLayout = new GridLayoutManager(this, 2);
            linearLayout.setOrientation(GridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayout);
            recyclerView.setAdapter(mAdapter);
        } else {
            //Toast.makeText(this, "Screen size is normal", Toast.LENGTH_LONG).show();
            LinearLayoutManager linearLayout = new LinearLayoutManager(this);
            linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayout);
            recyclerView.setAdapter(mAdapter);

        }


        //Toast.makeText(this, "Screen size is neither large, normal or small", Toast.LENGTH_LONG).show();


    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //TODO SHOPPING CART INCREMENT 
        //menu.findItem(R.id.action_settings).setIcon(R.drawable.vector_shopping_cart);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(MenuHome.this, ShoppingCartHome.class));
                return true;
            case R.id.action_filter:
                startActivity(new Intent(MenuHome.this, Filter.class));
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
        constant.setDishId(DishId);
        startActivity(new Intent(MenuHome.this, ItemDetail.class));
    }
}