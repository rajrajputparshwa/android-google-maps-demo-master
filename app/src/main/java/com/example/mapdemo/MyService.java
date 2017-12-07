package com.example.mapdemo;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

// for level below 11

public class MyService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    Handler handler;

    GoogleApiClient mGoogleApiClient;

    @Override
    public void onCreate() {

    }


    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Log.e("userid", "driver1");


        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }

        runnable.run();


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.e("Destroy", "Destroy");

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        mFirebaseInstance = FirebaseDatabase.getInstance();


        mFirebaseDatabase = mFirebaseInstance.getReference("cars");


        mFirebaseDatabase.child("driver1").child("lat").setValue(location.getLatitude());

        mFirebaseDatabase.child("driver1").child("log").setValue(location.getLongitude());
        String msg = "Updated Locationss : " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());


        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        runnable.run();


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms

                    Log.e("Connect", "Connect");

                    onConnected(null);

                }
            }, 5000);

        }
    };
}