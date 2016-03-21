package com.seindev.sehalsein.restaurantmanagement;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.client.Firebase;
import com.firebase.client.Query;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminMenuHome extends Fragment {

    Button vAddItem;

    //RECYCLER VIEW VARIABLE
    private final static String SAVED_ADAPTER_ITEMS = "SAVED_ADAPTER_ITEMS";
    private final static String SAVED_ADAPTER_KEYS = "SAVED_ADAPTER_KEYS";
    private Query mQuery;
    private AdminMenuHomeAdapter mAdapter;
    private ArrayList<Menu> mAdapterItems;
    private ArrayList<String> mAdapterKeys;
    RecyclerView recyclerView;
    boolean doubleBackToExitPressedOnce = false;


    public static AdminMenuHome newInstance() {
        AdminMenuHome fragment = new AdminMenuHome();
        return fragment;
    }

    public AdminMenuHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_admin_menu_home, container, false);

        vAddItem = (Button) layout.findViewById(R.id.buttonAddItem);
        Button additem = (Button) layout.findViewById(R.id.buttonAddItem);
        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddItemHome.class));
            }
        });
        //vAddItem.setOnClickListener(this);
        recyclerView = (RecyclerView) layout.findViewById(R.id.menuadmin);

        initFirebase();
        initRecycler();

        return layout;
    }

    private void initRecycler() {
        mAdapter = new AdminMenuHomeAdapter(mQuery, Menu.class, mAdapterItems, mAdapterKeys, getActivity());
        //mAdapter.setMenuClickListener(this);
        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(mAdapter);


    }

    private void initFirebase() {
        String firebaseLocation = getResources().getString(R.string.FireBase_Menu_URL);
        mQuery = new Firebase(firebaseLocation);

        //mAdapter.setMenuClickListener(getActivity());
    }

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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // outState.putParcelable(SAVED_ADAPTER_ITEMS, Parcels.wrap(mAdapter.getItems()));
        outState.putStringArrayList(SAVED_ADAPTER_KEYS, mAdapter.getKeys());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter.destroy();
    }

}
