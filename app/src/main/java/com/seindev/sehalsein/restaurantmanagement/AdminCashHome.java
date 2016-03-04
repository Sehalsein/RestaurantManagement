package com.seindev.sehalsein.restaurantmanagement;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class AdminCashHome extends Fragment {


    private String mDate;
    private Calendar cal;
    private DecimalFormat decimalFormat;

    private TextView vTextToday;
    private TextView vTextYesterday;
    private TextView vTextGrowthYesterday;
    private TextView vTextEarnings;
    private TextView vTextThisMonth;
    private TextView vTextGrowthThisMonth;
    private TextView vTextLastMonth;
    private TextView vTextGrowthLastMonth;
    private ImageView vYesterdayGrowth;
    private ImageView vLastMonth;
    private ImageView vThisMonth;

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
        vTextThisMonth = (TextView) layout.findViewById(R.id.textThisMonthEarning);
        vTextGrowthThisMonth = (TextView) layout.findViewById(R.id.textThisMonthGrowth);
        vTextLastMonth = (TextView) layout.findViewById(R.id.textLastMonthEarning);
        vTextGrowthLastMonth = (TextView) layout.findViewById(R.id.textLastMonthGrowth);

        vYesterdayGrowth = (ImageView) layout.findViewById(R.id.imageYesterdayGrowthIcon);
        vLastMonth = (ImageView) layout.findViewById(R.id.imageLastMonthGrowthIcon);
        vThisMonth = (ImageView) layout.findViewById(R.id.imageThisMonthGrowthIcon);


        cal = Calendar.getInstance();
        mDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
        decimalFormat = new DecimalFormat("####0.00");

        setupFireBaseToday();
        setupFireBaseYesterday();
        setupFireBaseLifeTime();
        setupFireBaseThisMonth();
        setupFireBaseLastMonth();

        return layout;
    }

    //Last Month vs The Month Before
    private void setupFireBaseLastMonth() {

        vTextGrowthLastMonth.setTextColor(getResources().getColor(R.color.colorSecondaryText));
        vLastMonth.setImageResource(R.drawable.vector_arrow_grey);
        vLastMonth.setRotation(180);
        vTextGrowthLastMonth.setText("0.00 %");


        String mFireBaseLinkBill = getResources().getString(R.string.FireBase_Bill_URL);
        Firebase bRef = new Firebase(mFireBaseLinkBill);

        cal.add(Calendar.MONTH, -1);
        final String mCurrentMonth = new SimpleDateFormat("MM").format(cal.getTime());

        cal.add(Calendar.MONTH, -2);
        final String mLastMonth = new SimpleDateFormat("MM").format(cal.getTime());
        //Toast.makeText(getActivity(), "Current MONTH" + month, Toast.LENGTH_LONG).show();

        final float[] mThisMonth = {0};
        final float[] mMonth = {0};


        //Calculates this month amount
        bRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                String mCurrentDate;
                String nCurrentMonth;
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Bill post = postSnapshot.getValue(Bill.class);

                    mCurrentDate = post.getDate();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date date = format.parse(mCurrentDate);
                        cal.setTime(date);
                        nCurrentMonth = new SimpleDateFormat("MM").format(cal.getTime());

                        if (mCurrentMonth.equals(nCurrentMonth)) {
                            mThisMonth[0] += post.getPrice();
                            calculateLastMonth(mThisMonth[0], mMonth[0]);

                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                vTextLastMonth.setText("Rs " + mThisMonth[0]);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        //calculates last month amount
        bRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String mCurrentDate;
                String nLastMonth;
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Bill post = postSnapshot.getValue(Bill.class);

                    mCurrentDate = post.getDate();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date date = format.parse(mCurrentDate);
                        cal.setTime(date);
                        // cal.add(Calendar.MONTH, -1);
                        nLastMonth = new SimpleDateFormat("MM").format(cal.getTime());

                        if (mCurrentMonth.equals(nLastMonth)) {
                            mMonth[0] += post.getPrice();
                            calculateLastMonth(mThisMonth[0], mMonth[0]);
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                //vTextLastMonth.setText("Rs " + mMonth[0]);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    //Last Month vs The Month Before (calculation)
    private void calculateLastMonth(float mTotalThisMonth, float mTotalLastMonth) {

        float mMonth = mTotalThisMonth;
        float nLastMonth = mTotalLastMonth;

        //Toast.makeText(getActivity(), "THIS MONTH : " + mMonth + "\n" + "LAST MONTH : " + nLastMonth, Toast.LENGTH_LONG).show();
        String growth = getContext().getResources().getString(R.string.percentageDown);
        float mDiffrence = 0;
        float mPercentage = 0;

        if (mMonth == 0 && nLastMonth == 0) {
            mPercentage = 0;
            growth = getContext().getResources().getString(R.string.percentageNo);
        } else if (nLastMonth > mMonth) {
            mDiffrence = nLastMonth - mMonth;
            mPercentage = (mDiffrence / nLastMonth) * 100;
            growth = getContext().getResources().getString(R.string.percentageDown);
        } else if (mMonth > nLastMonth) {
            mDiffrence = mMonth - nLastMonth;
            if (nLastMonth == 0) {
                mPercentage = mDiffrence;
            } else {
                mPercentage = (mDiffrence / nLastMonth) * 100;
            }
            growth = getContext().getResources().getString(R.string.percentageUp);
        }


        if (growth.equals(getContext().getResources().getString(R.string.percentageUp))) {
            vTextGrowthLastMonth.setTextColor(getResources().getColor(R.color.color100));
            vLastMonth.setImageResource(R.drawable.vector_arrow_green);
            vLastMonth.setRotation(90);

        } else if (growth.equals(getContext().getResources().getString(R.string.percentageDown))) {
            vTextGrowthLastMonth.setTextColor(getResources().getColor(R.color.color10));
            vLastMonth.setImageResource(R.drawable.vector_arrow_red);
            vLastMonth.setRotation(-90);
        } else if (growth.equals(getContext().getResources().getString(R.string.percentageNo))) {
            vTextGrowthLastMonth.setTextColor(getResources().getColor(R.color.colorSecondaryText));
            vLastMonth.setImageResource(R.drawable.vector_arrow_grey);
            vLastMonth.setRotation(180);


        }
        vTextGrowthLastMonth.setText(decimalFormat.format(mPercentage) + "%");

    }

    //THIS MONTH vs SAME DAY LAST MONTH
    private void setupFireBaseThisMonth() {

        String mFireBaseLinkBill = getResources().getString(R.string.FireBase_Bill_URL);
        Firebase bRef = new Firebase(mFireBaseLinkBill);

        final String mCurrentMonth = new SimpleDateFormat("MM").format(cal.getTime());

        cal.add(Calendar.MONTH, -1);
        final String mLastMonth = new SimpleDateFormat("MM").format(cal.getTime());
        //Toast.makeText(getActivity(), "Current MONTH" + mLastMonth, Toast.LENGTH_LONG).show();

        final float[] mThisMonth = {0};
        final float[] mMonth = {0};


        //Calculates this month amount
        bRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                String mCurrentDate;
                String nCurrentMonth;
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Bill post = postSnapshot.getValue(Bill.class);

                    mCurrentDate = post.getDate();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date date = format.parse(mCurrentDate);
                        cal.setTime(date);
                        nCurrentMonth = new SimpleDateFormat("MM").format(cal.getTime());

                        if (mCurrentMonth.equals(nCurrentMonth)) {
                            mThisMonth[0] += post.getPrice();
                            calculateThisMonth(mThisMonth[0], mMonth[0]);

                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                vTextThisMonth.setText("Rs " + mThisMonth[0]);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        //calculates last month amount
        bRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String mCurrentDate;
                String nLastMonth;
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Bill post = postSnapshot.getValue(Bill.class);

                    mCurrentDate = post.getDate();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date date = format.parse(mCurrentDate);
                        cal.setTime(date);
                        nLastMonth = new SimpleDateFormat("MM").format(cal.getTime());
                       // Toast.makeText(getActivity(), "LAST MONTh" + mLastMonth + "SADSAD " + nLastMonth, Toast.LENGTH_LONG).show();
                        if (mLastMonth.equals(nLastMonth)) {
                            mMonth[0] += post.getPrice();
                            Log.d("MONTH", "" + mMonth[0] + post.getDate());
                            calculateThisMonth(mThisMonth[0], mMonth[0]);
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                //Toast.makeText(getActivity(), "LAST MONTh" + mMonth[0], Toast.LENGTH_LONG).show();
                vTextLastMonth.append("Rs " + mMonth[0]);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    //THIS MONTH vs SAME DAY LAST MONTH (calculation)
    private void calculateThisMonth(float mTotalThisMonth, float mTotalLastMonth) {

        float mMonth = mTotalThisMonth;
        float nLastMonth = mTotalLastMonth;

        // Toast.makeText(getActivity(), "THIS MONTH : " + mMonth + "\n" + "LAST MONTH : " + nLastMonth, Toast.LENGTH_LONG).show();
        String growth = getContext().getResources().getString(R.string.percentageDown);
        float mDiffrence = 0;
        float mPercentage = 0;

        if (mMonth == 0 && nLastMonth == 0) {
            mPercentage = 0;
            growth = getContext().getResources().getString(R.string.percentageNo);
        } else if (nLastMonth > mMonth) {
            mDiffrence = nLastMonth - mMonth;
            mPercentage = (mDiffrence / nLastMonth) * 100;
            growth = getContext().getResources().getString(R.string.percentageDown);
        } else if (mMonth > nLastMonth) {
            mDiffrence = mMonth - nLastMonth;
            if (nLastMonth == 0) {
                mPercentage = mDiffrence;
            } else {
                mPercentage = (mDiffrence / nLastMonth) * 100;
            }
            growth = getContext().getResources().getString(R.string.percentageUp);
        }


        if (growth.equals(getContext().getResources().getString(R.string.percentageUp))) {
            vTextGrowthThisMonth.setTextColor(getResources().getColor(R.color.color100));
            vThisMonth.setImageResource(R.drawable.vector_arrow_green);
            vThisMonth.setRotation(90);

        } else if (growth.equals(getContext().getResources().getString(R.string.percentageDown))) {
            vTextGrowthThisMonth.setTextColor(getResources().getColor(R.color.color10));
            vThisMonth.setImageResource(R.drawable.vector_arrow_red);
            vThisMonth.setRotation(-90);
        } else if (growth.equals(getContext().getResources().getString(R.string.percentageNo))) {
            vTextGrowthThisMonth.setTextColor(getResources().getColor(R.color.colorSecondaryText));
            vThisMonth.setImageResource(R.drawable.vector_arrow_grey);
            vThisMonth.setRotation(180);


        }

        vTextGrowthThisMonth.setText(decimalFormat.format(mPercentage) + "%");

    }

    //LifeTime
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

    //This Yesterday vs Same Day Last Week
    private void setupFireBaseYesterday() {
        cal.add(Calendar.DATE, -1);
        String mYesterdayDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());

        cal.add(Calendar.DATE, -7);
        String mLastWeekDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());


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

    //This Yesterday vs Same Day Last Week (calculation)
    private void calculateYesterday(float mTotalYesterday, float mTotalLastWeek) {

        float mYesterday = mTotalYesterday;
        float mLastWeek = mTotalLastWeek;
        String growth = getContext().getResources().getString(R.string.percentageDown);
        float mDiffrence = 0;
        float mPercentage = 0;


        if (mYesterday == 0 && mLastWeek == 0) {
            mPercentage = 0;
            growth = getContext().getResources().getString(R.string.percentageNo);
        } else if (mLastWeek > mYesterday) {
            mDiffrence = mLastWeek - mYesterday;
            mPercentage = (mDiffrence / mLastWeek) * 100;
            growth = getContext().getResources().getString(R.string.percentageDown);
        } else if (mYesterday > mLastWeek) {
            mDiffrence = mYesterday - mLastWeek;
            if (mLastWeek == 0) {
                mPercentage = mDiffrence;
            } else {
                mPercentage = (mDiffrence / mLastWeek) * 100;
            }
            growth = getContext().getResources().getString(R.string.percentageUp);
        }


        if (growth.equals(getContext().getResources().getString(R.string.percentageUp))) {
            vTextGrowthYesterday.setTextColor(getResources().getColor(R.color.color100));
            vYesterdayGrowth.setImageResource(R.drawable.vector_arrow_green);
            vYesterdayGrowth.setRotation(90);

        } else if (growth.equals(getContext().getResources().getString(R.string.percentageDown))) {
            vTextGrowthYesterday.setTextColor(getResources().getColor(R.color.color10));
            vYesterdayGrowth.setImageResource(R.drawable.vector_arrow_red);
            vYesterdayGrowth.setRotation(-90);
        } else if (growth.equals(getContext().getResources().getString(R.string.percentageNo))) {
            vTextGrowthYesterday.setTextColor(getResources().getColor(R.color.colorSecondaryText));
            vYesterdayGrowth.setImageResource(R.drawable.vector_arrow_grey);
            vYesterdayGrowth.setRotation(180);


        }
        vTextGrowthYesterday.setText(decimalFormat.format(mPercentage) + "%");

    }

    //Today So Far
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
