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
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CashierHome extends AppCompatActivity implements CashierClickListner {


    //VARIABLE
    private float mTotalAmount;
    private int mSno;
    private int nSno;
    private String mDate;

    //CONSTANT VARIABLE
    private Constant constant;
    private String mTableId;
    private String mBillId;
    private String mOrderId;
    private String mService;
    private String mDishId;

    //RECYCLER VIEW 
    private RecyclerView recyclerView;
    private CashierAdapter mMyAdapter;
    private final static String SAVED_ADAPTER_ITEMS = "SAVED_ADAPTER_ITEMS";
    private final static String SAVED_ADAPTER_KEYS = "SAVED_ADAPTER_KEYS";
    private Query mQuery;
    private ArrayList<KitchenOpen> mAdapterItems;
    private ArrayList<String> mAdapterKeys;

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
        setContentView(R.layout.activity_cashier_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        handleInstanceState(savedInstanceState);

        initFirebase();
        initRecyclerView();
        initBillDetail();

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
        String firebaseLocation = getResources().getString(R.string.FireBase_Cashier_Open_URL);
        mQuery = new Firebase(firebaseLocation);
    }

    private void initRecyclerView() {

        recyclerView = (RecyclerView) findViewById(R.id.cashier_recycler);
        mMyAdapter = new CashierAdapter(mQuery, KitchenOpen.class, mAdapterItems, mAdapterKeys, this);
        mMyAdapter.setCashierClickListner(this);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mMyAdapter);
        mMyAdapter.notifyDataSetChanged();
        initSwipe();
    }


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
                    mMyAdapter.removeItem(position);

                    //Toast.makeText(CashierHome.this, "DELETE", Toast.LENGTH_LONG).show();
                    //Toast.makeText(CashierHome.this, "BILL IDASIDSA : " + mBillId, Toast.LENGTH_LONG).show();

                    //KITCHEN SWIPE DELETE (DELETE)
                    String KitchenOpen = getResources().getString(R.string.FireBase_Cashier_Open_URL) + "/" + nSno;
                    Firebase mDelete = new Firebase(KitchenOpen);
                    mDelete.removeValue();

                } else {
                    //KITCHEN SWIPE (COMPLETE)
                    mMyAdapter.removeItem(position);
                    String CashiernOpen = getResources().getString(R.string.FireBase_Cashier_Open_URL) + "/" + nSno;
                    Firebase mDelete = new Firebase(CashiernOpen);
                    mDelete.removeValue();

                    String TableClear = getResources().getString(R.string.FireBase_Order_URL) + "/" + mTableId;
                    Firebase vDelete = new Firebase(TableClear);
                    vDelete.removeValue();

                    clearActice();


                    addBill();
                    //initBillDetail();

                    /*
                    removeView();
                    edit_position = position;
                    alertDialog.setTitle("Edit Country");
                    et_country.setText(countries.get(position));
                    alertDialog.show();*/
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

    private void clearActice() {

        String mActiveLink = getResources().getString(R.string.FireBase_Active_URL);
        final Firebase oRef = new Firebase(mActiveLink);
        oRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                System.out.println("There are " + snapshot.getChildrenCount() + " KitchenOpen");
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Active post = postSnapshot.getValue(Active.class);
                    if (mTableId.equals(post.getTableid())) {
                        String ActiveClear = getResources().getString(R.string.FireBase_Active_URL) + "/" + post.getSno();
                        Firebase vDelete = new Firebase(ActiveClear);
                        vDelete.removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }

    private void initBillDetail() {
        String mBillLink = getResources().getString(R.string.FireBase_Bill_URL);
        final Firebase oRef = new Firebase(mBillLink);
        //Checks the Serial Number of the previous KitchenOpen
        oRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    System.out.println("There are " + snapshot.getChildrenCount() + " KitchenOpen");
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Bill post = postSnapshot.getValue(Bill.class);
                        mSno = post.getSno();
                        mBillId = post.getBillid();
                    }
                    final int result = Integer.parseInt(OrderNumberOnly(mBillId));
                    mBillId = OrderNumberCalc(result);
                    ++mSno;
                    Toast.makeText(CashierHome.this, "BILL ID: " + mBillId, Toast.LENGTH_SHORT).show();

                } else {
                    mBillId = getResources().getString(R.string.BillId);
                    mSno = 1;
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
       /* if (mBillId == null) {
       /*     mBillId = getResources().getString(R.string.BillId);
            mSno = 1;
        } else {
            final int result = Integer.parseInt(OrderNumberOnly(mBillId));
            mBillId = OrderNumberCalc(result);
            ++mSno;
        }     */

        /*
        Calendar cal = Calendar.getInstance();
        mDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

        String FireBaseLink = getResources().getString(R.string.FireBase_Bill_URL);
        Firebase mRef = new Firebase(FireBaseLink);
        Firebase aRef = mRef.child(mSno + "");
        Bill bill = new Bill(mSno, mBillId, mOrderId, mTotalAmount, mTableId, mDate);
        aRef.setValue(bill, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                    //Toast.makeText(CashierHome.this, "ITEM Data could not be saved. ", Toast.LENGTH_LONG).show();
                } else {
                    System.out.println("Data saved successfully.");
                    //Toast.makeText(CashierHome.this, "Data saved successfully.", Toast.LENGTH_LONG).show();
                }
            }
        });*/
    }

    public void addBill() {
        Calendar cal = Calendar.getInstance();
        mDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

        String FireBaseLink = getResources().getString(R.string.FireBase_Bill_URL);
        Firebase mRef = new Firebase(FireBaseLink);
        Firebase aRef = mRef.child(mSno + "");
        Bill bill = new Bill(mSno, mBillId, mOrderId, mTotalAmount, mTableId, mDate);
        aRef.setValue(bill, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                    //Toast.makeText(CashierHome.this, "ITEM Data could not be saved. ", Toast.LENGTH_LONG).show();
                } else {
                    System.out.println("Data saved successfully.");
                    //Toast.makeText(CashierHome.this, "Data saved successfully.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //FORMATES ORDERNO TO ORDERID
    public String OrderNumberCalc(int num) {

        String billno = "B" + new DecimalFormat("000").format(++num);
        return billno;

    }

    //CALCULATES THE ORDERNO
    public static String OrderNumberOnly(final String billno) {
        final StringBuilder sb = new StringBuilder(billno.length());
        for (int i = 0; i < billno.length(); i++) {
            final char c = billno.charAt(i);
            if (c > 47 && c < 58) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private void removeView() {
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }

    @Override
    public void itemClicked(View view, String OrderId, int Sno, String TableId, float TotalAmount) {
        mOrderId = OrderId;
        nSno = Sno;
        mTableId = TableId;
        mTotalAmount = TotalAmount;
    }

}
