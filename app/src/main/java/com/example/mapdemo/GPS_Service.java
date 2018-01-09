package com.example.mapdemo;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by raj on 6/16/2016.
 */
public class GPS_Service extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private LocationListener listener;
    private LocationManager locationManager;
    Context context = this;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    String name;
    Pref_Master pref_master;
    GoogleApiClient mGoogleApiClient;
    Location destination;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (destination != null) {
            destination = (Location) intent.getExtras().get("data");
        }


        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override


    public void onCreate() {


        pref_master = new Pref_Master(context);
        name = pref_master.getUID();


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


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            //noinspection MissingPermission
            locationManager.removeUpdates(listener);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {


                if (destination != null) {
                    float d = location.distanceTo(destination);
                    /*distanceaway.setText("KMS away from your location :" + d / 1000);*/

                    Log.e("Distancebetween", " " + d / 1000);
                } else {
                    mFirebaseInstance = FirebaseDatabase.getInstance();

                    mFirebaseDatabase = mFirebaseInstance.getReference("cars");

                    GeoFire geoFire = new GeoFire(mFirebaseDatabase);
                    geoFire.setLocation(name, new GeoLocation(location.getLatitude(), location.getLongitude()));

/*


                    mFirebaseDatabase.child(name).child("bearing").setValue(location.getBearing());

                    mFirebaseDatabase.child(name).child("speed").setValue(((location.getSpeed() * 3600) / 1000));

*/

                    Log.e("latitude", " " + location.getLatitude());

                    Log.e("logitude", " " + location.getLongitude());

                    Log.e("Bearing", " " + location.getBearing());

                    Log.e("Speed", " " + ((location.getSpeed() * 3600) / 1000));

            /*    String msg = "Updated Locations : " +
                        Double.toString(location.getLatitude()) + "," +
                        Double.toString(location.getLongitude());
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();*/
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };


        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
