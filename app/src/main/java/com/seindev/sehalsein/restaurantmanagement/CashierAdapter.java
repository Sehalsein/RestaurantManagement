package com.seindev.sehalsein.restaurantmanagement;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by sehalsein on 20/02/16.
 */
public class CashierAdapter extends FirebaseRecyclerAdapter<CashierAdapter.ViewHolder, KitchenOpen> {

    //Variable
    private TextView vTotalAmount;
    private String mTableNo = "T001";
    private float mTotalAmount;
    private float nTotalAmount;
    private int mSno;

    //CONSTANT VARIABLE
    private Constant constant;
    private String mTableId;
    private String mBillId;
    private String mOrderId;
    private String mService;
    private String mDishId;


    //Recycler View
    private CashierItemAdapter mMyAdapter;
    private final static String SAVED_ADAPTER_ITEMS = "SAVED_ADAPTER_ITEMS";
    private final static String SAVED_ADAPTER_KEYS = "SAVED_ADAPTER_KEYS";
    private Query mQuery;
    private Context context;
    private ArrayList<Order> mAdapterItems;
    private ArrayList<String> mAdapterKeys;

    private CashierClickListner cashierClickListner;

    public void setCashierClickListner(CashierClickListner cashierClickListner) {
        this.cashierClickListner = cashierClickListner;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView vTextBillNo, vTextTotalAmount, vTextTableNo;
        private RecyclerView recyclerView;
        private LinearLayout vCashierCard;

        public ViewHolder(View view) {
            super(view);
            vTextBillNo = (TextView) view.findViewById(R.id.textBillNo);
            vTextTableNo = (TextView) view.findViewById(R.id.textTableNo);
            recyclerView = (RecyclerView) view.findViewById(R.id.cashier_list_recycler);
            vCashierCard = (LinearLayout) view.findViewById(R.id.cashier_card);
            vTextTotalAmount = (TextView) view.findViewById(R.id.textTotalAmount);
            vTotalAmount = (TextView) view.findViewById(R.id.textTotalAmount);


        }

    }

    public void removeItem(int position) {
        //countries.remove(position);
        notifyItemRemoved(position);
        // notifyItemRangeChanged(position, countries.size());
    }

    public CashierAdapter(Query query, Class<KitchenOpen> itemClass, @Nullable ArrayList<KitchenOpen> items,
                          @Nullable ArrayList<String> keys, Context context) {
        super(query, itemClass, items, keys);
        this.context = context;
    }

    @Override
    public CashierAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cashier_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CashierAdapter.ViewHolder holder, final int position) {
        final KitchenOpen item = getItem(position);
        holder.vTextBillNo.setText(item.getOrderid());
        mOrderId = item.getOrderid();

        //float mServiceTax = item.getTotalamount() * 13 / 100;
        //mTotalAmount = item.getTotalamount() + mServiceTax;

        holder.vTextTotalAmount.setText("TOTAL AMOUNT : " + item.getTotalamount() + "");
        holder.vTextTableNo.setText("" + item.getTableid());

        //TODO FIX SOME LATE BUG HERE
        holder.vCashierCard.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (cashierClickListner != null) {
                    mOrderId = item.getOrderid();
                    cashierClickListner.itemClicked(v, mOrderId, item.getSno(), item.getTableid(), item.getTotalamount());
                    return true;
                } else {
                    return false;
                }
            }
        });
        initFirebase(mOrderId);

        //RECYCLER VIEW
        mMyAdapter = new CashierItemAdapter(mQuery, Order.class, mAdapterItems, mAdapterKeys);
        LinearLayoutManager linearLayout = new LinearLayoutManager(context);
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        holder.recyclerView.setLayoutManager(linearLayout);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerView.setAdapter(mMyAdapter);
        holder.recyclerView.setEnabled(false);

        String mActiveLink = context.getResources().getString(R.string.FireBase_Delivery_URL) + "/" + mOrderId;
        final Firebase mRef = new Firebase(mActiveLink);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean vExist = dataSnapshot.exists();
                if (vExist) {
                    Delivery post = dataSnapshot.getValue(Delivery.class);
                    holder.vTextTableNo.setText("" + String.format("%1$.2f", post.getMobileno()).split("\\.")[0]);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }


    private void initFirebase(String mOrderId) {

        String firebaseLocation = context.getResources().getString(R.string.FireBase_OrderDetail_URL);
        Log.d("KitchenAdapter", "KitchenOpen ID : " + mOrderId);
        mQuery = new Firebase(firebaseLocation).orderByChild("orderid").equalTo("" + mOrderId);

    }

    @Override
    protected void itemAdded(KitchenOpen item, String key, int position) {
        Log.d("CashierAdapter", "Added a new item to the adapter.");
    }

    @Override
    protected void itemChanged(KitchenOpen oldItem, KitchenOpen newItem, String key, int position) {
        Log.d("CashierAdapter", "Changed an item.");
    }

    @Override
    protected void itemRemoved(KitchenOpen item, String key, int position) {
        Log.d("CashierAdapter", "Removed an item from the adapter.");
    }

    @Override
    protected void itemMoved(KitchenOpen item, String key, int oldPosition, int newPosition) {
        Log.d("CashierAdapter", "Moved an item.");
    }
}
