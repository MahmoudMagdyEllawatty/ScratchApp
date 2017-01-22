package com.apps.scratch.scratchapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        StrictMode.enableDefaults();
        Thread timer = new Thread()
        {
            public void run()
            {
                try
                {
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent openMainActivity= new Intent(Splash.this,Products.class);
                    openMainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    openMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(openMainActivity);
                }
            }



        };
        timer.start();
    }




}
