package com.seindev.sehalsein.restaurantmanagement;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        intiDialog();
    }

    public void menu(View view) {
        startActivity(new Intent(MainActivity.this, MenuHome.class));
    }

    public void admin(View view) {
        startActivity(new Intent(MainActivity.this, AdminHome.class));
    }

    public void additem(View view) {
        startActivity(new Intent(MainActivity.this, AddItemHome.class));
    }

    public void login(View view) {
        startActivity(new Intent(MainActivity.this, LoginHome.class));
    }

    public void login1(View view) {
        startActivity(new Intent(MainActivity.this, AddStaff.class));
        //Toast.makeText(MainActivity.this, "OHH SO SAD", Toast.LENGTH_LONG).show();

    }

    public void welcome(View view) {
        startActivity(new Intent(MainActivity.this, Welcome.class));
    }

    public void settings(View view) {
        //startActivity(new Intent(MainActivity.this, DialogBox.class));
        dialog.show();
    }

    public void shopping(View view) {
        startActivity(new Intent(MainActivity.this, ShoppingCartHome.class));
    }

    public void iteminfo(View view) {
        startActivity(new Intent(MainActivity.this, ItemDetail.class));
    }

    public void review(View view) {
        startActivity(new Intent(MainActivity.this, ReviewHome.class));
    }

    public void kitchen(View view) {
        startActivity(new Intent(MainActivity.this, KitchenHome.class));
    }

    public void addreview(View view) {
        startActivity(new Intent(MainActivity.this, AddReviewHome.class));
    }

    public void dialograte(View view) {

        dialog.show();

    }

    public void addcustomer(View view) {
        //Toast.makeText(MainActivity.this, "AUTOMATIC :P", Toast.LENGTH_SHORT).show();
    }

    public void cashier(View view) {
        startActivity(new Intent(MainActivity.this, CashierHome.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    private void intiDialog() {
        /*// create a Dialog component
        dialog = new Dialog(this);
        //tell the Dialog to use the dialog.xml as it's layout description
//        dialog.setContentView(R.layout.content_add_review);
        dialog.setTitle("Android Custom Dialog Box");


        Button dialogButton = (Button) dialog.findViewById(R.id.addreview);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
*/
    }
}
