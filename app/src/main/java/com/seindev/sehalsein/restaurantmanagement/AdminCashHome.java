package com.seindev.sehalsein.restaurantmanagement;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminCashHome extends Fragment {


    private String mDate;
    private Calendar cal;

    private TextView vTextToday;
    private TextView vTextYesterday;
    private TextView vTextGrowthYesterday;
    private TextView vTextEarnings;

    public static AdminCashHome newInstance() {
        AdminCashHome fragment = new AdminCashHome();
        return fragment;
    }

    public AdminCashHome() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_admin_cash_home, container, false);

        vTextToday = (TextView) layout.findViewById(R.id.textTodayEarning);
        vTextYesterday = (TextView) layout.findViewById(R.id.textYesterdayEarning);
        vTextGrowthYesterday = (TextView) layout.findViewById(R.id.textYesterdayGrowth);
        vTextEarnings = (TextView) layout.findViewById(R.id.textEarning);

        cal = Calendar.getInstance();
        mDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

        setupFireBaseToday();
        setupFireBaseYesterday();
        setupFireBaseLifeTime();

        return layout;
    }

    private void setupFireBaseLifeTime() {

        String mFireBaseLinkBill = getResources().getString(R.string.FireBase_Bill_URL);
        Firebase bRef = new Firebase(mFireBaseLinkBill);
        bRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                float mLifeTime = 0;
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Bill post = postSnapshot.getValue(Bill.class);
                    System.out.println(post.getBillid() + " - " + post.getDate());
                    mLifeTime += post.getPrice();
                }
                vTextEarnings.setText("Rs " + mLifeTime);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    private void setupFireBaseYesterday() {
        cal.add(Calendar.DATE, -1);
        String mYesterdayDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

        cal.add(Calendar.DATE, -7);
        String mLastWeekDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

        Firebase.setAndroidContext(getActivity());

        final float[] mYesterdayAmount = new float[1];
        final float[] mLastWeekAmount = new float[1];

        String mFireBaseLinkBill = getResources().getString(R.string.FireBase_Bill_URL);
        Firebase bRef = new Firebase(mFireBaseLinkBill);
        Query bQuery = bRef.orderByChild("date").equalTo(mYesterdayDate);
        bQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                float mYesterday = 0;

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Bill post = postSnapshot.getValue(Bill.class);
                    System.out.println(post.getBillid() + " - " + post.getDate());
                    mYesterday += post.getPrice();
                }
                vTextYesterday.setText("Rs " + mYesterday);
                mYesterdayAmount[0] = mYesterday;
                calculateYesterday(mYesterdayAmount[0], mLastWeekAmount[0]);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });


        Query bLQuery = bRef.orderByChild("date").equalTo(mLastWeekDate);
        bLQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                float mLastWeek = 0;

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Bill post = postSnapshot.getValue(Bill.class);
                    System.out.println(post.getBillid() + " LAST WEEK - " + post.getPrice());
                    mLastWeek += post.getPrice();


                }
                mLastWeekAmount[0] = mLastWeek;
                calculateYesterday(mYesterdayAmount[0], mLastWeekAmount[0]);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }

    private void calculateYesterday(float mTotalYesterday, float mTotalLastWeek) {

        float mYesterday = mTotalYesterday;
        float mLastWeek = mTotalLastWeek;
        boolean growth = false;
        float mDiffrence = 0;

        if (mLastWeek > mYesterday) {
            mDiffrence = mLastWeek - mYesterday;
            growth = false;
        } else if (mYesterday > mLastWeek) {
            mDiffrence = mYesterday - mLastWeek;
            growth = true;
        }

        float mPercentage = (mDiffrence / mLastWeek) * 100;

        if (growth) {
            vTextGrowthYesterday.setTextColor(getResources().getColor(R.color.color100));
        } else {
            vTextGrowthYesterday.setTextColor(getResources().getColor(R.color.color10));
        }
        vTextGrowthYesterday.setText(mPercentage + "%");

    }

    private void setupFireBaseToday() {

        //Firebase.setAndroidContext(getActivity());

        String mFireBaseLinkBill = getResources().getString(R.string.FireBase_Bill_URL);
        Firebase bRef = new Firebase(mFireBaseLinkBill);

        Query bQuery = bRef.orderByChild("date").equalTo(mDate);


        bQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                float mTotalAmount = 0;

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Bill post = postSnapshot.getValue(Bill.class);
                    System.out.println(post.getBillid() + " - " + post.getDate());
                    mTotalAmount += post.getPrice();
                }
                vTextToday.setText("Rs " + mTotalAmount);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }


}
