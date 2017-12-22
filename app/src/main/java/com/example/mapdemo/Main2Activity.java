package com.example.mapdemo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

public class Main2Activity extends AppCompatActivity {

    Context c = this;
    String next;
    Pref_Master pref_master;
    private static final int STORAGE_PERMISSION_CODE_LOCATION = 22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        requestLOCAtion();

        pref_master = new Pref_Master(c);

        if (pref_master.getUID().equals("0")) {

            DialogBox.setConfirm(c);
        } else {
            Intent i = new Intent(getApplicationContext(), MapDemoActivity.class);
            startActivity(i);
        }


    }

    private void requestLOCAtion() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, STORAGE_PERMISSION_CODE_LOCATION);
    }


}
