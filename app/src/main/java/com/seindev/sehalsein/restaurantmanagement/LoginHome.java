package com.seindev.sehalsein.restaurantmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by sehalsein on 08/12/15.
 */
public class LoginHome extends AppCompatActivity {


    // UI references.
    private EditText vUserName;
    private EditText vPassword;
    private View vProgressView;
    private View mLoginFormView;
    private Button mEmailSignInButton;


    String[] UserName = new String[10];
    String[] Password = new String[10];

    String mUserName;
    String mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //TOOL BAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up the login form.
        vUserName = (EditText) findViewById(R.id.editUserName);
        vPassword = (EditText) findViewById(R.id.editPassword);
        mEmailSignInButton = (Button) findViewById(R.id.buttonLogin);
        mLoginFormView = findViewById(R.id.login_form);
        vProgressView = findViewById(R.id.login_progress);

        //FireBase
        Firebase.setAndroidContext(this);
        initLogin();

    }

    //Login
    private void initLogin() {

        String mLoginLink = getResources().getString(R.string.FireBase_Login_URL);
        final Firebase rRef = new Firebase(mLoginLink);

        rRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean vLoginExist = dataSnapshot.exists();
                int i = 0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Login post = postSnapshot.getValue(Login.class);
                    UserName[i] = post.getUsername();
                    Password[i] = post.getUsername();
                    i++;
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    public void login(View view) {

        boolean correct = false;

        mUserName = vUserName.getText().toString();
        mPassword = vPassword.getText().toString();

        if (vUserName.getText().toString().trim().length() <= 0) {
            mUserName = "";
        }
        if (vPassword.getText().toString().trim().length() <= 0) {
            mPassword = "";
        }

        if (mUserName == "" || mPassword == "") {
            if (mUserName == "") {
                vUserName.setError("Enter Username");
            }
            if (mPassword == "") {
                vPassword.setError("Enter Password");
            }
        } else {

            for (int i = 0; i <= UserName.length; i++) {
                try {
                    if (mUserName.equals(UserName[i]) && mPassword.equals(Password[i])) {
                        correct = true;
                        break;
                    } else {
                        correct = false;
                    }
                } catch (ArrayIndexOutOfBoundsException m) {
                    System.out.print("ARRAY" + m.toString());

                } catch (Exception e) {
                    System.out.print("EXCEPTIOn" + e.toString());
                }
            }
            if (correct) {
                if (mUserName.equals("admin")) {
                    startActivity(new Intent(LoginHome.this, AdminHome.class));
                    finish();
                } else if (mUserName.equals("kitchen")) {
                    startActivity(new Intent(LoginHome.this, KitchenHome.class));
                    finish();
                } else if (mUserName.equals("cashier")) {
                    startActivity(new Intent(LoginHome.this, CashierHome.class));
                    finish();
                }
            } else {
                vUserName.setError("Incorrect Username");
                vPassword.setError("Incorrect Password");
                Toast.makeText(LoginHome.this, "Incorrect username and password", Toast.LENGTH_LONG).show();
            }
        }


    }

}
