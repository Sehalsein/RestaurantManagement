package com.seindev.sehalsein.restaurantmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class HomeDeliveryDetail extends AppCompatActivity {


    private EditText vCustomerName, vMobileNo, vAddress, vLandmark;
    private String mCustomerName, mAddress, mLandmark;
    private double mMobileNo;

    private Constant constant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_delivery_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        vCustomerName = (EditText) findViewById(R.id.editCustomerName);
        vMobileNo = (EditText) findViewById(R.id.editMobile);
        vAddress = (EditText) findViewById(R.id.editAddress);
        vLandmark = (EditText) findViewById(R.id.editLandmark);
        vMobileNo.setInputType(InputType.TYPE_CLASS_NUMBER);

        constant.setTableId(constant.getOrderId());


    }

    public void proceed(View view) {
        mMobileNo = 00;
        try {

            if (vCustomerName.getText().toString().trim().length() <= 0) {
                mCustomerName = "";
            }
            if (vAddress.getText().toString().trim().length() <= 0) {
                mAddress = "";
            }
            if (vLandmark.getText().toString().trim().length() <= 0) {
                mLandmark = "";
            }
            if (vMobileNo.getText().toString().trim().length() < 10) {
                mMobileNo = 1;
            }
            Firebase mRef = new Firebase("https://restaurant-managment.firebaseio.com");
            Firebase Ref = mRef.child("Delivery").child(constant.getOrderId() + "");


            if (mCustomerName == "" || mAddress == "" || mLandmark == "" || mMobileNo == 1) {
                if (mCustomerName == "") {
                    vCustomerName.setError("Enter Name!");
                }
                if (mAddress == "") {
                    vAddress.setError("Enter Address!");
                }
                if (mLandmark == "") {
                    vLandmark.setError("Enter Landmark!");
                }
                if (mMobileNo == 1) {
                    vMobileNo.setError("Enter MobileNo!");
                }
            } else {
                mCustomerName = String.valueOf(vCustomerName.getText());
                mMobileNo = Double.parseDouble(vMobileNo.getText().toString());
                mAddress = String.valueOf(vAddress.getText());
                mLandmark = String.valueOf(vLandmark.getText());

                Delivery delivery = new Delivery(mCustomerName, mMobileNo, mAddress, mLandmark);

                Ref.setValue(delivery, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        if (firebaseError != null) {
                            System.out.println("Data could not be saved. " + firebaseError.getMessage());
                        } else {
                            System.out.println("Data saved successfully.");
                            startActivity(new Intent(HomeDeliveryDetail.this,MenuHome.class));

                        }
                    }
                });

            }

        } catch (NullPointerException n) {
            //Toast.makeText(this, "" + n.toString(), Toast.LENGTH_LONG).show();
        }

    }
}
