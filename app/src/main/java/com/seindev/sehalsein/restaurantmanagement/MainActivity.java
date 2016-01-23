package com.seindev.sehalsein.restaurantmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        

    }

    public void menu(View view) {
        startActivity(new Intent(MainActivity.this, MenuHome.class));
    }

    public void admin(View view) {
        startActivity(new Intent(MainActivity.this, AdminHome.class));
    }

    public void additem(View view) {
        startActivity(new Intent(MainActivity.this, AddItem.class));
    }

    public void login(View view) {
        startActivity(new Intent(MainActivity.this, Login.class));
    }

    public void login1(View view) {
        startActivity(new Intent(MainActivity.this, Login1.class));
    }

    public void welcome(View view) {
        startActivity(new Intent(MainActivity.this, Welcome.class));
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
}
