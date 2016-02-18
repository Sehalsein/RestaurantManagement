package com.seindev.sehalsein.restaurantmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    private String mTAbleNo = "T002";
    private String mBillNo = "B001";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ImageView welcomebg = (ImageView) findViewById(R.id.welcomebackground);
        ImageView appIcon = (ImageView) findViewById(R.id.imageViewLogo);
        EditText tableno = (EditText) findViewById(R.id.editText);

        Picasso.with(this)
                .load(R.drawable.welcomebg)
                .resize(500, 800)
                .into(welcomebg);

        setupBillId();

    }

    private void setupBillId() {
        Firebase.setAndroidContext(this);
        String FirebaselinkMenu = getResources().getString(R.string.FireBase_Bill_URL);
        final Firebase mRef = new Firebase(FirebaselinkMenu);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean vExist = dataSnapshot.exists();
                String billno = "B";
                if (vExist) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Bill post = postSnapshot.getValue(Bill.class);
                        billno = post.getBillNo();
                    }

                    final int result = Integer.parseInt(BillNumberonly(billno));

                    mBillNo = BillNumbercalc(result);
                    System.out.println("TRUE " + mBillNo);
                } else {
                    mBillNo = "B001";
                    System.out.println("FALSE " + mBillNo);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    public String BillNumbercalc(int num) {

        String billno = "B" + new DecimalFormat("000").format(++num);
        return billno;

    }

    public static String BillNumberonly(final String billno) {
        final StringBuilder sb = new StringBuilder(billno.length());
        for (int i = 0; i < billno.length(); i++) {
            final char c = billno.charAt(i);
            if (c > 47 && c < 58) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public void dineIn(View view) {
        //Toast.makeText(this, "DINEIN", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Welcome.this, MenuHome.class);
        intent.putExtra("TableNo", mTAbleNo);
        intent.putExtra("BillNo", mBillNo);
        startActivity(intent);
    }

    public void icon(View view) {
        startActivity(new Intent(Welcome.this, AdminHome.class));
    }

    public void homeDelivery(View view) {
        //Toast.makeText(this,"HOMEDELIVERY",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Welcome.this, MenuHome.class);
        intent.putExtra("TableNo", mTAbleNo);
        intent.putExtra("BillNo", mBillNo);
        startActivity(intent);
    }

}
