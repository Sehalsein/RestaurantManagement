package com.seindev.sehalsein.restaurantmanagement;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class ShoppingCartHome extends AppCompatActivity implements ShoppingClickListener {

    //CONSTANT VARIABLE
    private Constant constant;
    private String mTableId;
    private String mBillId;
    private String mOrderId;
    private String mService;
    private String nOrderId;

    //XML Variable
    private TextView vTotalAmount;
    private TextView vHeader;

    //Variable
    private int mTotalAmount;
    private int mSno;

    //Recycler View
    private final static String SAVED_ADAPTER_ITEMS = "SAVED_ADAPTER_ITEMS";
    private final static String SAVED_ADAPTER_KEYS = "SAVED_ADAPTER_KEYS";
    private Query mQuery;
    private RecyclerView recyclerView;
    private ShoppingCartAdapter mAdapter;
    private ArrayList<Order> mAdapterItems;
    private ArrayList<String> mAdapterKeys;

    //TypeFace
    private Typeface caveat, greatvibes, lobster, alexbrush, milonga;

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

        vTotalAmount = (TextView) findViewById(R.id.textTotalAmount);
        vHeader = (TextView) findViewById(R.id.textOrderHeader);

        lobster = Typeface.createFromAsset(getAssets(), "fonts/Lobster.ttf");
        vHeader.setTypeface(lobster);

        mTableId = constant.getTableId();
        if (mTableId == null) {
            mTableId = getResources().getString(R.string.TableId);
        }

        mOrderId = constant.getOrderId();
        if (mOrderId == null) {
            mOrderId = getResources().getString(R.string.OrderId);
        }

        initFirebase();
        initRecycler();
        initKitchenOpenDetail();

    }

    private void initKitchenOpenDetail() {
        final String mOrderLink = getResources().getString(R.string.FireBase_Kitchen_Open_URL);
        final Firebase mRef = new Firebase(mOrderLink);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean vExist = dataSnapshot.exists();
                if (vExist) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        KitchenOpen post = postSnapshot.getValue(KitchenOpen.class);
                        mSno = post.getSno();
                        nOrderId = post.getOrderid();
                    }
                } else {
                    mSno = 1;
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
            mAdapterItems = new ArrayList<Order>();
            mAdapterKeys = new ArrayList<String>();
        }
    }

    @Override
    public void send(int TotalAmount) {
        mTotalAmount = TotalAmount;
        vTotalAmount.setText("RS " + TotalAmount);
    }

    //Firebase Query
    private void initFirebase() {
        String firebaseLocation = getResources().getString(R.string.FireBase_Order_URL) + "/" + mTableId;
        mQuery = new Firebase(firebaseLocation);
    }

    //Initializing Recycler View
    private void initRecycler() {
        recyclerView = (RecyclerView) findViewById(R.id.order_list_recycler);
        mAdapter = new ShoppingCartAdapter(mQuery, Order.class, mAdapterItems, mAdapterKeys);
        mAdapter.send(this);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }

    //ORDERING THE FOOD
    public void order(View view) {
        if (!mOrderId.equals(nOrderId)) {
            //Toast.makeText(ShoppingCartHome.this, "SNO if eqquals : " + mSno, Toast.LENGTH_LONG).show();
            ++mSno;
        }

        String FireBaseLink = getResources().getString(R.string.FireBase_Kitchen_Open_URL);
        Firebase mRef = new Firebase(FireBaseLink);
        Firebase aRef = mRef.child(mSno + "");
        KitchenOpen kitchenOpen = new KitchenOpen(mSno, mOrderId, mTableId, mTotalAmount);
        aRef.setValue(kitchenOpen, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                    //Toast.makeText(ShoppingCartHome.this, "ITEM Data could not be saved. ", Toast.LENGTH_LONG).show();
                } else {
                    System.out.println("Data saved successfully.");
                    //Toast.makeText(ShoppingCartHome.this, "Data saved successfully.", Toast.LENGTH_LONG).show();
                }
            }
        });
        onBackPressed();
    }
}
