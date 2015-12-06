package com.seindev.sehalsein.restaurantmanagement;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminCashHome extends Fragment {

    public static AdminCashHome newInstance() {
        AdminCashHome fragment = new AdminCashHome();
        return fragment;
    }

    public AdminCashHome() {
        // Required empty public constructor
    }
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_admin_cash_home);

    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_admin_cash_home, container, false);

        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.admin_recycler);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayout = new LinearLayoutManager(getActivity());
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayout);

        AdminCashHomeAdapter adminCashHomeAdapter = new AdminCashHomeAdapter(getActivity(), createList(7));
        recyclerView.setAdapter(adminCashHomeAdapter);

        return layout;
    }


    private List<AdminCashHomeInfo> createList(int size) {
        List<AdminCashHomeInfo> result = null;

        try {
            int vGrowth[] = {100, 100, 100, 100, 100, 100, 1001, 100, 100};
            int vEarning[] = {100, 100, 100, 100, 100, 100, 1001, 100, 100};
            int vGrowthIcon = R.mipmap.ic_launcher;

            String vLifetime[] = {
                    "Today so far",
                    "Yesterday",
                    "This month so far",
                    "Last month",
                    "Lifetime",
            };

            String vPreviousLife[] = {
                    " ",
                    "vs the same day last week",
                    "vs the same day last month",
                    "vs the month before last",
                    " ",
            };


            result = new ArrayList<AdminCashHomeInfo>();
            for (int i = 0; i < size; i++) {

                AdminCashHomeInfo adminCashHomeInfo = new AdminCashHomeInfo();
                adminCashHomeInfo.vGrowthIcon = vGrowthIcon;
                adminCashHomeInfo.vEarning = vEarning[i];
                adminCashHomeInfo.vGrowth = vGrowth[i];
                adminCashHomeInfo.vLifetime = vLifetime[i];
                adminCashHomeInfo.vPreviousLife = vPreviousLife[i];

                result.add(adminCashHomeInfo);
            }
        } catch (Exception e) {

        }

        return result;
    }

}
