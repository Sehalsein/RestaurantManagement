package com.seindev.sehalsein.restaurantmanagement;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.*;


import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuHome extends AppCompatActivity {


    public MenuHome() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_home);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.menu_recycler);
        recyclerView.setHasFixedSize(true);

        //TODO
        /*
           *FOR TABLET USE GRID LAYOUT
            * FOR PHONES USE LINEAR LAYOUT
            * 25/11/15 NOW CURRENTLY USING OFFLINE TO PASS DEMO VALUES
         */

        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayout);

        MenuHomeAdapter menuHomeAdapter = new MenuHomeAdapter(this, createList(7));
        recyclerView.setAdapter(menuHomeAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private List<MenuHomeInfo> createList(int size) {
        List<MenuHomeInfo> result = null;

        try {
            String vDishName[] = {"CHICKEN BURGER", "WAZAAAAP", "SEHAL", "CHRIS", "ASDSDADS", "SEIN"};
            String vIngredients[] = {"CHICKEN", "AWEXOME", "BATMAN", "ASS", "ASSES", "BRUCE WAYNE"};
            int vDishIcon = R.mipmap.ic_launcher;

            result = new ArrayList<MenuHomeInfo>();
            for (int i = 0; i < size; i++) {

                MenuHomeInfo menuHomeInfo = new MenuHomeInfo();
                menuHomeInfo.vDishIcon = vDishIcon;
                menuHomeInfo.vDishName = vDishName[i];
                menuHomeInfo.vIngredients = vIngredients[i];

                result.add(menuHomeInfo);
            }
        } catch (Exception e) {

        }

        return result;
    }

}