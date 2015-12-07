package com.seindev.sehalsein.restaurantmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddItem extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        //TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //RECYCLER VIEW
        recyclerView = (RecyclerView) findViewById(R.id.add_item_recycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayout);
        AddItemAdapter addItemAdapter = new AddItemAdapter(this, createList(7));
        recyclerView.setAdapter(addItemAdapter);


    }


    public void Additem(View view) {

        Toast.makeText(this, "WAZAAAAAAP", Toast.LENGTH_LONG).show();

    }

    //TEMP TEST LIST
    private List<AddItemInfo> createList(int size) {
        List<AddItemInfo> result = null;

        try {

            String vItemHint[] = {
                    "Dish Name",
                    "Price (\u20B9)",       //RUPEE SYMBOL
                    "Quantity (no.of person)",
                    "Category",
                    "Tags",
            };

            String vItemName[] = {
                    "Dish Name",
                    "Price",
                    "Quantity",
                    "Category",
                    "Tags",
            };


            result = new ArrayList<AddItemInfo>();
            for (int i = 0; i < size; i++) {

                AddItemInfo addItemInfo = new AddItemInfo();
                addItemInfo.vItemHint = vItemHint[i];
                addItemInfo.vItemName = vItemName[i];


                result.add(addItemInfo);
            }
        } catch (Exception e) {

        }

        return result;
    }

}
