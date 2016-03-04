package com.seindev.sehalsein.restaurantmanagement;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class AddStaff extends AppCompatActivity {

    EditText vUserName, vPassword;
    String mUserName, mPassword;
    String nUserName, nPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        vUserName = (EditText) findViewById(R.id.editUserName);
        vPassword = (EditText) findViewById(R.id.editPassword);

        vUserName.clearFocus();
        vPassword.clearFocus();
        mUserName = null;
        mPassword = null;

        //initLogin();
    }



    public void submit(View v) {

        hideKeyboard();

        mUserName = vUserName.getText().toString();
        mPassword = vPassword.getText().toString();

        if (vUserName.getText().toString().trim().length() <= 0) {
            mUserName = "";
        }
        if (vPassword.getText().toString().trim().length() <= 0) {
            mPassword = "";
        }

        //Toast.makeText(AddStaff.this, "USERNAME" + mUserName + "\n Password" + mPassword, Toast.LENGTH_SHORT).show();
        System.out.print("USERNAME" + mUserName + "\n Password" + mPassword);
        if (mUserName == "" || mPassword == "") {
           // Toast.makeText(AddStaff.this, "NO", Toast.LENGTH_SHORT).show();
            if (mUserName == "") {
                vUserName.setError("Enter Username");
            }
            if (mPassword == "") {
                vPassword.setError("Enter Password");
            }
        } else {
            String FirebaseLogin = getResources().getString(R.string.FireBase_Login_URL);
            Firebase mRef = new Firebase(FirebaseLogin);
            Login login = new Login(mUserName, mPassword);
            mRef.push().setValue(login, new Firebase.CompletionListener() {
                @Override
                public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                    if (firebaseError != null) {
                        System.out.println("Data could not be saved. " + firebaseError.getMessage());
                        //Toast.makeText(.this, "ITEM Data could not be saved. ", Toast.LENGTH_LONG).show();
                    } else {
                        System.out.println("Data saved successfully.");
                        //Toast.makeText(AddItemHome.this, "Data saved successfully.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }


    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
