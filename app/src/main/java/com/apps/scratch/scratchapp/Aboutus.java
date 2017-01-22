package com.apps.scratch.scratchapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Aboutus extends AppCompatActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        Toolbar t = (Toolbar) findViewById(R.id.tToolbar_aboutUs);
        ImageButton back = (ImageButton) t.findViewById(R.id.title_cart_1);
        TextView title = (TextView) t.findViewById(R.id.title_children);

        title.setText("About Us");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }



    @Override
    public void onClick(View v) {
    }


    @Override
    public void onBackPressed() {
            super.onBackPressed();
    }
}
