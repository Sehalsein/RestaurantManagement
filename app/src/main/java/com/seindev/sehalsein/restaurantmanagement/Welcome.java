package com.seindev.sehalsein.restaurantmanagement;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class Welcome extends AppCompatActivity {

    //CONSTANT VARIABLE
    private Constant constant;
    private String mTableId;
    private String mBillId;
    private String mOrderId;
    private String mService;
    private String mDishId;
    private String mCustomerId;
    private int mSno = 0;
    private NfcAdapter nfcAdapter;
    private ProgressBar mLoding;

    private float mTotalAmount;

    //VARIABLE
    private Button mDineIn, mHomeDel;
    private ImageView appIcon;
    private RadioButton mTable1, mTable2, mTable3, mTable4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //INITIALIZING VARIABLE
        ImageView welcomebg = (ImageView) findViewById(R.id.welcomebackground);
        appIcon = (ImageView) findViewById(R.id.imageViewLogo);
        mLoding = (ProgressBar) findViewById(R.id.progressBar);
        mDineIn = (Button) findViewById(R.id.buttonDineIn);
        mHomeDel = (Button) findViewById(R.id.buttonDelivery);

        //SETING IT TO FALSE
        mDineIn.setEnabled(false);
        mHomeDel.setEnabled(false);
        appIcon.setEnabled(false);

        //TABLE CHOOSING
        mTableId = getResources().getString(R.string.TableId);
        mTable1 = (RadioButton) findViewById(R.id.radioButton);
        mTable1.setChecked(true);
        mTable1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTableId = "T001";
            }
        });
        mTable2 = (RadioButton) findViewById(R.id.radioButton2);
        mTable2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTableId = "T002";
            }
        });
        mTable3 = (RadioButton) findViewById(R.id.radioButton3);
        mTable3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTableId = "T003";
            }
        });
        mTable4 = (RadioButton) findViewById(R.id.radioButton4);
        mTable4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTableId = "T004";
            }
        });
        RadioGroup group = (RadioGroup) findViewById(R.id.radiogroup);


        Picasso.with(this).load(R.drawable.welcomebg).resize(500, 800).into(welcomebg, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });
        // Picasso.with(this).load(R.drawable.welcomebg).transform(new Blur(this, 20)).into(welcomebg);

        //FIREBASE INITIALIZING METHODS
        initOrderId();
        initCustomer();
        initActive();

    }

    //CHEKING ACTIVE USERS
    private void initActive() {
        String mActiveLink = getResources().getString(R.string.FireBase_Active_URL);
        final Firebase mRef = new Firebase(mActiveLink);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean vExist = dataSnapshot.exists();
                //CHECKS IF THE VALUE EXIST OR NOT
                if (vExist) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Active post = postSnapshot.getValue(Active.class);
                        mSno = post.getSno();
                    }

                } else {
                    mSno = 0;
                }

                //ENABLING EVERYTHING
                mLoding.setVisibility(View.INVISIBLE);
                mDineIn.setEnabled(true);
                mHomeDel.setEnabled(true);
                appIcon.setEnabled(true);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }

    //INITALIZING CUSTOMER TABLE
    private void initCustomer() {
        String mOrderLink = getResources().getString(R.string.FireBase_Customer_URL);
        final Firebase mRef = new Firebase(mOrderLink);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean vExist = dataSnapshot.exists();
                String nCustomerId = null;
                if (vExist) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Customer post = postSnapshot.getValue(Customer.class);
                        nCustomerId = post.getCustomerid();
                    }
                    final int result = Integer.parseInt(CustomerNumberOnly(nCustomerId));
                    mCustomerId = CustomerNumberCalc(result);
                    System.out.println("CUSTOMER ID : " + mCustomerId);
                } else {
                    mCustomerId = getResources().getString(R.string.CustomerId);
                    System.out.println("CUSTOMER ID : " + mCustomerId);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    //FORMATES ORDERNO TO ORDERID
    public String CustomerNumberCalc(int num) {

        String customerno = getResources().getString(R.string.CustomerCode) + new DecimalFormat("000").format(++num);
        return customerno;

    }

    //CALCULATES THE ORDERNO
    public static String CustomerNumberOnly(final String customerno) {
        final StringBuilder sb = new StringBuilder(customerno.length());
        for (int i = 0; i < customerno.length(); i++) {
            final char c = customerno.charAt(i);
            if (c > 47 && c < 58) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    //INTITALIZING ORDER ID FROM FIREBASE
    private void initOrderId() {

        String FirebaselinkBill = getResources().getString(R.string.FireBase_OrderDetail_URL);
        final Firebase mRef = new Firebase(FirebaselinkBill);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nOrderId = null;
                boolean vExist = dataSnapshot.exists();
                if (vExist) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Order post = postSnapshot.getValue(Order.class);
                        nOrderId = post.getOrderid();
                    }
                    final int result = Integer.parseInt(OrderNumberOnly(nOrderId));
                    mOrderId = OrderNumberCalc(result);
                    System.out.println("OrderId ID : " + mOrderId);
                } else {
                    mOrderId = getResources().getString(R.string.OrderId);
                    System.out.println("ORDER ID : " + mOrderId);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("WELCOME", "onCancelled ERROR" + firebaseError.toString());
            }
        });
    }

    //FORMATES ORDERNO TO ORDERID
    public String OrderNumberCalc(int num) {

        String orderno = getResources().getString(R.string.OrderCode) + new DecimalFormat("000").format(++num);
        return orderno;

    }

    //CALCULATES THE ORDERNO
    public static String OrderNumberOnly(final String orderno) {
        final StringBuilder sb = new StringBuilder(orderno.length());
        for (int i = 0; i < orderno.length(); i++) {
            final char c = orderno.charAt(i);
            if (c > 47 && c < 58) {
                sb.append(c);
            }
        }
        return sb.toString();
    }


    //DINE-IN BUTTON CLICK
    public void dineIn(View view) {
        mService = getResources().getString(R.string.DineIn);
        //mTableId = "T001";
        constant.setTableId(mTableId);
        constant.setOrderId(mOrderId);
        constant.setService(mService);
        constant.setCustomerId(mCustomerId);

        //Toast.makeText(Welcome.this, "TABLE ID : " + mTableId, Toast.LENGTH_SHORT).show();
        ++mSno;
        String mActiveLink = getResources().getString(R.string.FireBase_Active_URL);
        Firebase mRef = new Firebase(mActiveLink).child(mSno + "");
        Active active = new Active(mSno, mTableId, mOrderId);
        mRef.setValue(active);

        startActivity(new Intent(Welcome.this, MenuHome.class));
        finish();
    }

    //ADMIN SECRECT ENTRY
    public void icon(View view) {
        constant.setOrderId(mOrderId);
        constant.setService(mService);
        constant.setCustomerId(mCustomerId);
        startActivity(new Intent(Welcome.this, LoginHome.class));
        finish();
    }

    //HOME DELIVERY BUTTON CLICK
    public void homeDelivery(View view) {
        mService = getResources().getString(R.string.HomeDelivery);
        mTableId = "H001";
        constant.setTableId(mTableId);
        constant.setOrderId(mOrderId);
        constant.setService(mService);
        constant.setCustomerId(mCustomerId);

        startActivity(new Intent(Welcome.this, HomeDeliveryDetail.class));
        finish();
    }

    private void getTagInfo(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        Log.e("jaks", "getTagInfo: " + tag.toString());
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        getTagInfo(intent);
    }
}
