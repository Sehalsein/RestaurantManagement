package com.seindev.sehalsein.restaurantmanagement;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class UpdateItemHome extends AppCompatActivity {

    private EditText vDishName, vPrice, vSno, vDishId, vDescription, vImageUrl;
    private AutoCompleteTextView vCategory;
    private Constant constant;
    private String[] categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        vDishId = (EditText) findViewById(R.id.editDishId);
        vDishName = (EditText) findViewById(R.id.editDishName);
        vPrice = (EditText) findViewById(R.id.editPrice);
        vSno = (EditText) findViewById(R.id.editSNo);
        vCategory = (AutoCompleteTextView) findViewById(R.id.autocompletetextCategory);
        vDescription = (EditText) findViewById(R.id.editDescription);
        vImageUrl = (EditText) findViewById(R.id.editImageUrl);

        categories = getResources().getStringArray(R.array.categories);
        //CATEGORY
        ArrayAdapter<String> mcategories = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories);
        vCategory.setAdapter(mcategories);
        vCategory.setThreshold(1);

        vSno.setEnabled(false);
        vDishId.requestFocus();

        initMenu();
    }

    private void initMenu() {

        String mDishId = constant.getDishId();
        String mMenuLink = getResources().getString(R.string.FireBase_Menu_URL);
        final Firebase mRef = new Firebase(mMenuLink);

        Query mQuery = mRef.orderByChild("dishid").equalTo(mDishId);
        mQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Menu post = postSnapshot.getValue(Menu.class);
                    vDishId.setText(post.getDishid());
                    vDishName.setText(post.getDishname());
                    vPrice.setText(post.getPrice() + "");
                    vSno.setText(post.getSno() + "");
                    vCategory.setText(post.getCategory());
                    vDescription.setText(post.getDescription());
                    vImageUrl.setText(post.getImageurl());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());

            }
        });
    }

    public void UpdateItem(View view) {

        String mDishName = null, mCategory = null, mDishId = null, mDescription = null;
        String mImageUrl = null;
        float mPrice = 0;
        int mSno = 0, mSpicyLevel = 0;

        final int[] Sno = new int[1];

        try {
            mDishId = String.valueOf(vDishId.getText());
            mSno = Integer.parseInt(vSno.getText().toString());
            mCategory = String.valueOf(vCategory.getText());
            mDishName = String.valueOf(vDishName.getText());
            mPrice = Float.parseFloat(vPrice.getText().toString());
            mDescription = vDescription.getText().toString();
            mImageUrl = vImageUrl.getText().toString();

            if (vDishId.getText().toString().trim().length() <= 0) {
                mDishId = "";
            }
            if (vCategory.getText().toString().trim().length() <= 0) {
                mCategory = "";
            }
            if (vDishName.getText().toString().trim().length() <= 0) {
                mDishName = "";
            }
            if (vDescription.getText().toString().trim().length() <= 0) {
                mDescription = "";
            }
            if (vPrice.getText().toString().trim().length() <= 0) {
                mPrice = 0;
            }
            if (vImageUrl.getText().toString().trim().length() <= 0) {
                mImageUrl = "";
            }
            if (mPrice == 0.00) {
                mPrice = 0;
            }
            Firebase mRef = new Firebase("https://restaurant-managment.firebaseio.com");
            Firebase Ref = mRef.child("Menu").child(mSno + "");


            if (mCategory == "" || mDishId == "" || mDishName == "" || mDescription == "" || mPrice == 0) {
                if (mCategory == "") {
                    vCategory.setError("Enter Category!");
                }
                if (mDishId == "") {
                    vDishId.setError("Enter Dish Id!");
                }
                if (mDishName == "") {
                    vDishName.setError("Enter Dish Name!");
                }
                if (mDescription == "") {
                    vDescription.setError("Enter Description!");
                }
                if (mPrice == 0) {
                    vPrice.setError("Enter Price!");
                }
                if (mImageUrl == "") {
                    vImageUrl.setError("Enter Image URL!!");
                }
            } else {
                Menu menu = new Menu(mSno, mDishId, mDishName, mPrice, mCategory, mDescription, mImageUrl);

                Ref.setValue(menu, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        if (firebaseError != null) {
                            System.out.println("Data could not be saved. " + firebaseError.getMessage());
                            //Toast.makeText(AddItemHome.this, "ITEM Data could not be saved. ", Toast.LENGTH_LONG).show();
                        } else {
                            System.out.println("Data saved successfully.");
                            Toast.makeText(UpdateItemHome.this, "Data updated successfully!!", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                            //Toast.makeText(AddItemHome.this, "Data saved successfully.", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }

        } catch (NullPointerException n) {
            //Toast.makeText(this, "" + n.toString(), Toast.LENGTH_LONG).show();
        }

    }

}
