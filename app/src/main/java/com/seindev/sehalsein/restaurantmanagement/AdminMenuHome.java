package com.seindev.sehalsein.restaurantmanagement;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminMenuHome extends Fragment implements View.OnClickListener {

    Button vAddItem;

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

        vAddItem.setOnClickListener(this);

        return layout;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonAddItem:
                startActivity(new Intent(getActivity(),AddItemHome.class));
                break;
        }

    }
}
