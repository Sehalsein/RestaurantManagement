package com.seindev.sehalsein.restaurantmanagement;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;

import java.util.ArrayList;

/**
 * Created by sehalsein on 14/02/16.
 */
public class KitchenAdapter extends FirebaseRecyclerAdapter<KitchenAdapter.ViewHolder, Order> {


    private KitchenItemAdapter mMyAdapter;
    private TextView vTotalAmount;

    private String mBillId, mbillid;
    private String mTableNo = "T001";
    private int mTotalAmount;
    private int mSno;

    private final static String SAVED_ADAPTER_ITEMS = "SAVED_ADAPTER_ITEMS";
    private final static String SAVED_ADAPTER_KEYS = "SAVED_ADAPTER_KEYS";

    private Query mQuery;
    private Context context;
    private ArrayList<Order> mAdapterItems;
    private ArrayList<String> mAdapterKeys;


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView vTextBillNo;
        private RecyclerView recyclerView;

        public ViewHolder(View view) {
            super(view);
            vTextBillNo = (TextView) view.findViewById(R.id.textBillNo);
            recyclerView = (RecyclerView) view.findViewById(R.id.kitchen_list_recycler);
        }
    }


    public KitchenAdapter(Query query, Class<Order> itemClass, @Nullable ArrayList<Order> items,
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
        final Order item = getItem(position);
        holder.vTextBillNo.setText(item.getBillNo());

        //handleInstanceState(savedInstanceState);

        setupFirebase(item.getBillNo());

        mMyAdapter = new KitchenItemAdapter(mQuery, Order.class, mAdapterItems, mAdapterKeys);
        LinearLayoutManager linearLayout = new LinearLayoutManager(context);
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        holder.recyclerView.setLayoutManager(linearLayout);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerView.setAdapter(mMyAdapter);

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

    private void setupFirebase(String billNo) {
        Firebase.setAndroidContext(context);
        String firebaseLocation = context.getResources().getString(R.string.FireBase_OrderDetail_URL) + "/" + billNo;
        mQuery = new Firebase(firebaseLocation);
    }

    @Override
    protected void itemAdded(Order item, String key, int position) {
        Log.d("KitchenAdapter", "Added a new item to the adapter.");
    }

    @Override
    protected void itemChanged(Order oldItem, Order newItem, String key, int position) {
        Log.d("KitchenAdapter", "Changed an item.");
    }

    @Override
    protected void itemRemoved(Order item, String key, int position) {
        Log.d("KitchenAdapter", "Removed an item from the adapter.");
    }

    @Override
    protected void itemMoved(Order item, String key, int oldPosition, int newPosition) {
        Log.d("KitchenAdapter", "Moved an item.");
    }
}
