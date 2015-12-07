package com.seindev.sehalsein.restaurantmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddItem extends AppCompatActivity {

    private EditText vDishName, vPrice, vQuantity;
    private AutoCompleteTextView vCategory;
    private MultiAutoCompleteTextView vTags;
    private String[] categories;
    private String[] tags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        //TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //INITIALIZING
        vDishName = (EditText) findViewById(R.id.editDishName);
        vPrice = (EditText) findViewById(R.id.editPrice);
        vQuantity = (EditText) findViewById(R.id.editQuantity);
        vCategory = (AutoCompleteTextView) findViewById(R.id.autocompletetextCategory);
        vTags = (MultiAutoCompleteTextView) findViewById(R.id.multiautocompletetextTags);

        //ARRAY
        categories = getResources().getStringArray(R.array.categories);
        tags = getResources().getStringArray(R.array.tags);

        //INPUT TYPES
        //DISHNAME
        vDishName.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);

        //PRICE
        vPrice.setInputType(InputType.TYPE_CLASS_NUMBER);

        //QUANTITY
        vQuantity.setInputType(InputType.TYPE_CLASS_NUMBER);

        //CATEGORY
        ArrayAdapter<String> mcategories = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories);
        vCategory.setAdapter(mcategories);
        vCategory.setThreshold(1);

        //TAGS
        ArrayAdapter<String> mtags = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tags);
        vTags.setAdapter(mtags);
        vTags.setThreshold(1);
        vTags.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());


    }


    public void Additem(View view) {

        String mDishName, mCategory, mTags;
        String mPrice, mQuantity;

        try {
            mCategory = String.valueOf(vCategory.getText());
            mDishName = String.valueOf(vDishName.getText());
            mPrice = vPrice.getText().toString();
            mQuantity = vQuantity.getText().toString();
            mTags = String.valueOf(vTags.getText());

            if (vCategory.getText().toString().trim().length() <= 0) {
                mCategory = "";
            }
            if (vDishName.getText().toString().trim().length() <= 0) {
                mDishName = "";
            }
            if (vTags.getText().toString().trim().length() <= 0) {
                mTags = "";
            }
            if (vPrice.getText().toString().trim().length() <= 0) {
                mPrice = "";
            }
            if (vQuantity.getText().toString().trim().length() <= 0) {
                mQuantity = "";
            }


            if (mCategory == "" || mDishName == "" || mTags == "" || mPrice == "" || mQuantity == "")
                Toast.makeText(this, "FIELD EMPTY ", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(this, "ITEM INSERTED", Toast.LENGTH_LONG).show();


        } catch (NullPointerException n) {
            Toast.makeText(this, "" + n.toString(), Toast.LENGTH_LONG).show();
        }


    }

}
