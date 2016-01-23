package com.seindev.sehalsein.restaurantmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ImageView welcomebg = (ImageView) findViewById(R.id.welcomebackground);

        ImageView appIcon= (ImageView) findViewById(R.id.imageViewLogo);



        Picasso.with(this)
                .load(R.drawable.welcomebg)
                .resize(500, 800)
                .into(welcomebg);

    }

    public void dineIn(View view) {
        //Toast.makeText(this, "DINEIN", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Welcome.this, MenuHome.class));
    }
    public void icon(View view) {
        startActivity(new Intent(Welcome.this, AdminHome.class));
    }
    public void homeDelivery(View view) {
        //Toast.makeText(this,"HOMEDELIVERY",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Welcome.this, MenuHome.class));
    }

}
