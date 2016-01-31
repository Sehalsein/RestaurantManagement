package com.seindev.sehalsein.restaurantmanagement;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartHome extends AppCompatActivity {


    private RecyclerView recyclerView;
    private ShoppingCartAdapter mMyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setUpRecycler();
    }


    private void setUpRecycler() {

        recyclerView = (RecyclerView) findViewById(R.id.order_list_recycler);
        mMyAdapter = new ShoppingCartAdapter(this, createList(10));
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.addItemDecoration();
        recyclerView.setAdapter(mMyAdapter);
    }

    private List<ShoppingCart> createList(int size) {
        List<ShoppingCart> result = null;

        try {
            String vLifetime[] = {
                    "Today so far",
                    "Yesterday",
                    "This month so far",
                    "Last month",
                    "Lifetime",
            };

            String vPreviousLife[] = {
                    " ",
                    "vs the same day last week",
                    "vs the same day last month",
                    "vs the month before last",
                    " ",
            };


            result = new ArrayList<ShoppingCart>();
            for (int i = 0; i < size; i++) {

                ShoppingCart shoppingCart = new ShoppingCart();
                shoppingCart.name = vLifetime[i];
                shoppingCart.price = vPreviousLife[i];

                result.add(shoppingCart);
            }
        } catch (Exception e) {

        }

        return result;
    }

}
