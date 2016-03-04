package com.seindev.sehalsein.restaurantmanagement;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

//SWIPE

public class KitchenHome extends AppCompatActivity implements KitchenClickListener {

    //CONSTANT VARIABLE
    private Constant constant;
    private String mTableId;
    private String mBillId;
    private String mOrderId;
    private String mService;
    private String mDishId;


    //RECYCLER VIEW
    private RecyclerView recyclerView;
    private KitchenAdapter mAdapter;
    private final static String SAVED_ADAPTER_ITEMS = "SAVED_ADAPTER_ITEMS";
    private final static String SAVED_ADAPTER_KEYS = "SAVED_ADAPTER_KEYS";
    private Query mQuery;
    private ArrayList<KitchenOpen> mAdapterItems;
    private ArrayList<String> mAdapterKeys;

    //VARIABLE
    private int mSno;
    private int nSno;
    private float mTotalAmount;

    //SWIPE
    private ArrayList<String> countries = new ArrayList<>();
    private AlertDialog.Builder alertDialog;
    private EditText et_country;
    private int edit_position;
    private View view;
    private boolean add = false;
    private Paint p = new Paint();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        handleInstanceState(savedInstanceState);

        initFirebase();
        initRecyclerView();
        initCashier();

    }

    private void initCashier() {

        final String mOrderLink = getResources().getString(R.string.FireBase_Cashier_Open_URL);
        final Firebase mRef = new Firebase(mOrderLink);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean vExist = dataSnapshot.exists();
                if (vExist) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        KitchenOpen post = postSnapshot.getValue(KitchenOpen.class);
                        nSno = post.getSno();
                    }
                } else {
                    nSno = 0;
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
            mAdapterItems = new ArrayList<KitchenOpen>();
            mAdapterKeys = new ArrayList<String>();
        }
    }

    private void initFirebase() {
        String firebaseLocation = getResources().getString(R.string.FireBase_Kitchen_Open_URL);
        mQuery = new Firebase(firebaseLocation);
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.kitchen_recycler);
        mAdapter = new KitchenAdapter(mQuery, KitchenOpen.class, mAdapterItems, mAdapterKeys, this);
        mAdapter.setKitchenClickListner(this);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        //SWIPE initializing
        initSwipe();
    }


    //TODO FIX ENTIRE CARD SWIPE
    //SWIPE
    private void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    mAdapter.removeItem(position);

                    //KITCHEN SWIPE DELETE (DELETE)
                    String KitchenOpen = getResources().getString(R.string.FireBase_Kitchen_Open_URL) + "/" + mSno;
                    Firebase mDelete = new Firebase(KitchenOpen);
                    mDelete.removeValue();
                } else {
                    ++nSno;
                    //KITCHEN SWIPE DELETE (COMPLETE)
                    mAdapter.removeItem(position);
                    String KitchenOpen = getResources().getString(R.string.FireBase_Kitchen_Open_URL) + "/" + mSno;
                    Firebase mDelete = new Firebase(KitchenOpen);
                    mDelete.removeValue();

                    //ADDING ITEM To CASHIER
                    String FireBaseLink = getResources().getString(R.string.FireBase_Cashier_Open_URL);
                    Firebase mRef = new Firebase(FireBaseLink);
                    Firebase aRef = mRef.child(nSno + "");
                    KitchenOpen kitchenOpen = new KitchenOpen(nSno, mOrderId, mTableId, mTotalAmount);
                    aRef.setValue(kitchenOpen, new Firebase.CompletionListener() {
                        @Override
                        public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                            if (firebaseError != null) {
                                System.out.println("Data could not be saved. " + firebaseError.getMessage());
                                //Toast.makeText(KitchenHome.this, "ITEM Data could not be saved. ", Toast.LENGTH_LONG).show();
                            } else {
                                System.out.println("Data saved successfully.");
                                //Toast.makeText(KitchenHome.this, "Data saved successfully.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX > 0) {
                        p.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_edit_white);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_white);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    //TODO TRY THIS
    private void removeView() {
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    @Override
    public void itemClicked(View view, String BillId, int Sno, String TableId, float TotalAmount) {
        mOrderId = BillId;
        mSno = Sno;
        mTableId = TableId;
        mTotalAmount = TotalAmount;
        //Toast.makeText(KitchenHome.this, "BILL ID" + mBillId, Toast.LENGTH_LONG).show();
    }
}