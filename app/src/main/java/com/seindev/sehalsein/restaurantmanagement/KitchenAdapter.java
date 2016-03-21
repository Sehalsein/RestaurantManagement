package com.seindev.sehalsein.restaurantmanagement;

import android.content.Context;
import android.os.Bundle;
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
 * Created by sehalsein on 14/02/16.
 */
public class KitchenAdapter extends FirebaseRecyclerAdapter<KitchenAdapter.ViewHolder, KitchenOpen> {

    //VARIABLE
    private TextView vTotalAmount;
    //private String mBillId, mbillid;
    private String mTableNo = "T001";
    private int mTotalAmount;
    private int mSno;
    private Context context;

    //CONSTANT VARIABLE
    private String mOrderId;

    //RECYCLER VIEW
    private KitchenItemAdapter mMyAdapter;
    private final static String SAVED_ADAPTER_ITEMS = "SAVED_ADAPTER_ITEMS";
    private final static String SAVED_ADAPTER_KEYS = "SAVED_ADAPTER_KEYS";
    private Query mQuery;
    private ArrayList<Order> mAdapterItems;
    private ArrayList<String> mAdapterKeys;

    //CLICK LISTENER
    private KitchenClickListener kitchenClickListener;

    public void setKitchenClickListner(KitchenClickListener kitchenClickListener) {
        this.kitchenClickListener = kitchenClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView vTextBillNo, vTextTableNo;
        private RecyclerView recyclerView;
        private LinearLayout vKitchenCard;

        public ViewHolder(View view) {
            super(view);
            vTextBillNo = (TextView) view.findViewById(R.id.textBillNo);
            vTextTableNo = (TextView) view.findViewById(R.id.textTableNo);
            recyclerView = (RecyclerView) view.findViewById(R.id.kitchen_list_recycler);
            vKitchenCard = (LinearLayout) view.findViewById(R.id.kitchen_card);
        }
    }

    public void removeItem(int position) {
        //countries.remove(position);
        notifyItemRemoved(position);
        // notifyItemRangeChanged(position, countries.size());
    }

    public KitchenAdapter(Query query, Class<KitchenOpen> itemClass, @Nullable ArrayList<KitchenOpen> items,
                          @Nullable ArrayList<String> keys, Context context) {
        super(query, itemClass, items, keys);
        this.context = context;
    }

    @Override
    public KitchenAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.kitchen_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final KitchenAdapter.ViewHolder holder, final int position) {
        final KitchenOpen item = getItem(position);
        holder.vTextBillNo.setText(item.getOrderid());
        holder.vTextTableNo.setText(item.getTableid());

        mOrderId = item.getOrderid();

        //TODO FIX SOME DELAY BUG
        holder.vKitchenCard.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (kitchenClickListener != null) {
                    mOrderId = item.getOrderid();
                    kitchenClickListener.itemClicked(v, mOrderId, item.getSno(), item.getTableid(), item.getTotalamount());
                    return true;
                } else {
                    return false;
                }
            }
        });

        //handleInstanceState(savedInstanceState);

        //Initializing Firebase
        initFirebase(mOrderId);

        //Initializing Inner recycler View
        mMyAdapter = new KitchenItemAdapter(mQuery, Order.class, mAdapterItems, mAdapterKeys, context);
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

    private void initFirebase(String mOrderId) {

        String firebaseLocation = context.getResources().getString(R.string.FireBase_OrderDetail_URL);
        Log.d("KitchenAdapter", "KitchenOpen ID : " + mOrderId);
        mQuery = new Firebase(firebaseLocation).orderByChild("orderid").equalTo("" + mOrderId);

    }

    @Override
    protected void itemAdded(KitchenOpen item, String key, int position) {
        // Log.d("KitchenAdapter", "Added a new item to the adapter.");
    }

    @Override
    protected void itemChanged(KitchenOpen oldItem, KitchenOpen newItem, String key, int position) {
        // Log.d("KitchenAdapter", "Changed an item.");
    }

    @Override
    protected void itemRemoved(KitchenOpen item, String key, int position) {
        // Log.d("KitchenAdapter", "Removed an item from the adapter.");
    }

    @Override
    protected void itemMoved(KitchenOpen item, String key, int oldPosition, int newPosition) {
        // Log.d("KitchenAdapter", "Moved an item.");
    }
}
