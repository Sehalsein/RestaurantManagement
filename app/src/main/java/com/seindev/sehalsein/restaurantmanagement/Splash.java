package com.seindev.sehalsein.restaurantmanagement;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class Splash extends AppCompatActivity {

    private PendingIntent pendingIntent;
    boolean myBoolean;
    public View logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = findViewById(R.id.logo);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                // Code here will run in UI thread

                ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(Splash.this, R.animator.flipping);
                anim.setTarget(logo);
                anim.setDuration(2000);
                anim.start();

                final Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.logo_heartbeat);
                logo.startAnimation(animation);
            }
        });
        int myTimer = 3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                Intent i = new Intent(Splash.this, Welcome.class);
                startActivity(i);
                finish();

            }
        }, myTimer);

    }

}


