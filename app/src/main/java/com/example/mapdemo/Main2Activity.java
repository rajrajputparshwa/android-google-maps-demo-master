package com.example.mapdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {

    Context c = this;
    String next;
    Pref_Master pref_master;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        pref_master = new Pref_Master(c);

        if (pref_master.getUID().equals("0")) {

            DialogBox.setConfirm(c);
        } else {
            Intent i = new Intent(getApplicationContext(), MapDemoActivity.class);
            startActivity(i);
        }


    }

}
