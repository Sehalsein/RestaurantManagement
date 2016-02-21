package com.seindev.sehalsein.restaurantmanagement;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class AddCustomerHome extends AppCompatActivity {

    private EditText vCustomerId, vCustomerName, vMobileNo, vSno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        vCustomerId = (EditText) findViewById(R.id.editCustomerId);
        vCustomerName = (EditText) findViewById(R.id.editCustomerName);
        vMobileNo = (EditText) findViewById(R.id.editMobileNo);
        vSno = (EditText) findViewById(R.id.editSNo);

    }


    public void addcustomer(View view) {
        String mCustomerId;
        String mCustomerName;
        int mSno;
        String mMobileNo;

        try {
            mCustomerId = String.valueOf(vCustomerId.getText());
            mSno = Integer.parseInt(vSno.getText().toString());
            mCustomerName = String.valueOf(vCustomerName.getText());
            mMobileNo = String.valueOf(vMobileNo.getText());

            if (vSno.getText().toString().trim().length() <= 0) {
                mSno = 1;
            }
            if (vCustomerId.getText().toString().trim().length() <= 0) {
                mCustomerId = "";
            }
            if (vCustomerName.getText().toString().trim().length() <= 0) {
                mCustomerName = "";
            }
            if (vMobileNo.getText().toString().trim().length() <= 0) {
                mMobileNo = "";
            }


            //FIREBASE ENTRY
            //Firebase.setAndroidContext(this);
            Firebase mref = new Firebase("https://restaurant-managment.firebaseio.com");
            //CHILD
            Firebase Ref = mref.child("Customer").child(mSno + "");

            //Checks is Values EMPTY
            if (mCustomerId == "" || mCustomerName == "" || mMobileNo == "" || mSno == 0) {
                Toast.makeText(this, "FIELD EMPTY ", Toast.LENGTH_LONG).show();
            } else {

                //Menu menu = new Menu(mSno, mDishId, mDishName, mPrice, mQuantity, mCategory, mTags);
                Customer customer = new Customer(mCustomerId, mCustomerName, mMobileNo, mSno);
                Ref.setValue(customer, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        if (firebaseError != null) {
                            System.out.println("Data could not be saved. " + firebaseError.getMessage());
                            Toast.makeText(AddCustomerHome.this, "ITEM Data could not be saved. ", Toast.LENGTH_LONG).show();
                        } else {
                            System.out.println("Data saved successfully.");
                            Toast.makeText(AddCustomerHome.this, "Data saved successfully.", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }

        } catch (NullPointerException n) {
            Toast.makeText(this, "" + n.toString(), Toast.LENGTH_LONG).show();
        }

    }

}
