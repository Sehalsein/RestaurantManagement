package com.seindev.sehalsein.restaurantmanagement;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class AddItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.add_item_recycler);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayout);

        AddItemAdapter addItemAdapter = new AddItemAdapter(this, createList(7));
        recyclerView.setAdapter(addItemAdapter);

    }

    private List<AddItemInfo> createList(int size) {
        List<AddItemInfo> result = null;

        try {

            String vItemHint[] = {
                    "Dish Name",
                    "Price",
                    "Quantity",
                    "Category",
                    "SubCategory",
            };

            String vItemName[] = {
                    "Dish Name",
                    "Price",
                    "Quantity",
                    "Category",
                    "SubCategory",
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
