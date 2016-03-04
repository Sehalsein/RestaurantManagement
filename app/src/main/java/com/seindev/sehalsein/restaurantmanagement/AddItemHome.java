package com.seindev.sehalsein.restaurantmanagement;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class AddItemHome extends AppCompatActivity {

    private EditText vDishName, vPrice, vSno, vDishId, vDescription, vImageUrl;
    private AutoCompleteTextView vCategory;
    private String[] categories;
    private String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        //FireBase
        //Firebase.setAndroidContext(this);

        //TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //INITIALIZING
        vDishId = (EditText) findViewById(R.id.editDishId);
        vDishName = (EditText) findViewById(R.id.editDishName);
        vPrice = (EditText) findViewById(R.id.editPrice);
        vSno = (EditText) findViewById(R.id.editSNo);
        vCategory = (AutoCompleteTextView) findViewById(R.id.autocompletetextCategory);
        vDescription = (EditText) findViewById(R.id.editDescription);
        vImageUrl = (EditText) findViewById(R.id.editImageUrl);
        //ARRAY
        categories = getResources().getStringArray(R.array.categories);

        vSno.setEnabled(false);
        vDishId.requestFocus();

        //INPUT TYPES
        //DISHNAME
        vDishName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);

        //PRICE
        vPrice.setInputType(InputType.TYPE_CLASS_NUMBER);
        vSno.setInputType(InputType.TYPE_CLASS_NUMBER);


        //CATEGORY
        ArrayAdapter<String> mcategories = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories);
        vCategory.setAdapter(mcategories);
        vCategory.setThreshold(1);

        setUpFirebase();

    }

    private void setUpFirebase() {
        Firebase.setAndroidContext(this);
        final int[] Sno = new int[1];
        Firebase mRef = new Firebase("https://restaurant-managment.firebaseio.com/Menu");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    System.out.println("There are " + snapshot.getChildrenCount() + " blog posts");
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Menu post = postSnapshot.getValue(Menu.class);
                        int sno = post.getSno();
                        vSno.setText(++sno + "");
                        //Toast.makeText(AddItemHome.this, "Exists : " + sno, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    vSno.setText("1");
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    public void Additem(View view) {

        String mDishName, mCategory, mDishId, mDescription;
        String mImageUrl;
        float mPrice = 0;
        int mSno, mSpicyLevel = 0;

        final int[] Sno = new int[1];

        try {
            mDishId = String.valueOf(vDishId.getText());
            mSno = Integer.parseInt(vSno.getText().toString());
            mCategory = String.valueOf(vCategory.getText());
            mDishName = String.valueOf(vDishName.getText());
            mPrice = Float.parseFloat(vPrice.getText().toString());
            mDescription = vDescription.getText().toString();
            mImageUrl = vImageUrl.getText().toString();

            if (vSno.getText().toString().trim().length() <= 0) {
                mSno = 0;
            }
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
                            vDishId.setText("");
                            vDishName.setText("");
                            vPrice.setText("");
                            vCategory.setText("");
                            vDescription.setText("");
                            vImageUrl.setText("");
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
