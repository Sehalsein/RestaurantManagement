package com.seindev.sehalsein.restaurantmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
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

    private float mTotalAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ImageView welcomebg = (ImageView) findViewById(R.id.welcomebackground);
        ImageView appIcon = (ImageView) findViewById(R.id.imageViewLogo);
        EditText tableno = (EditText) findViewById(R.id.editText);

        //TODO REMOVE EDIT TEXT
        tableno.setVisibility(View.INVISIBLE);

        Picasso.with(this)
                .load(R.drawable.welcomebg)
                .resize(500, 800)
                .into(welcomebg);

        initOrderId();

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
                    System.out.println("TRUE" + mOrderId);
                } else {
                    mOrderId = getResources().getString(R.string.OrderId);
                    System.out.println("FALSE" + mOrderId);
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

        String billno = "F" + new DecimalFormat("000").format(++num);
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

    //DINE-IN BUTTON CLICK
    public void dineIn(View view) {
        mService = getResources().getString(R.string.DineIn);
        constant.setOrderId(mOrderId);
        constant.setService(mService);

        startActivity(new Intent(Welcome.this, MenuHome.class));
        finish();
    }

    //ADMIN SECRECT ENTRY
    public void icon(View view) {
        startActivity(new Intent(Welcome.this, AdminHome.class));
    }

    //HOME DELIVERY BUTTON CLICK
    public void homeDelivery(View view) {
        mService = getResources().getString(R.string.HomeDelivery);
        constant.setOrderId(mOrderId);
        constant.setService(mService);

        startActivity(new Intent(Welcome.this, MenuHome.class));
        finish();
    }

}
