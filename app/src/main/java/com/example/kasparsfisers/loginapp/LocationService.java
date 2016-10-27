package com.example.kasparsfisers.loginapp;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class LocationService extends Service implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static LocationService instance = null;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */

    public static boolean isInstanceCreated() {
        return instance != null;
    }


    Geocoder gcd = null;
    double lat,lon,acc;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        instance = this;
        Toast.makeText(this, R.string.serviceStart, Toast.LENGTH_SHORT).show();




        // Create the location client to start receiving updates
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
        }

        mGoogleApiClient.connect();
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        Toast.makeText(this, R.string.serviceStop, Toast.LENGTH_SHORT).show();

        // Disconnecting the client invalidates it.
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

        // only stop if it's connected
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }

        instance = null;
        super.onDestroy();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // Get last known recent location.
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        android.location.Location mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (mCurrentLocation != null) {
            // Print current location if not null
            Log.d("DEBUG", "current location: " + mCurrentLocation.toString());

        }
        // Begin polling for new location updates.
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
    }


    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        Log.i("Activity","error:"
                + result.getErrorCode());
    }

    @Override
    public void onLocationChanged(Location location) {
         lat= location.getLatitude();
         lon= location.getLongitude();
         acc= location.getAccuracy();
        // New location has now been determined
        String msg = "Inserted: " +
                Double.toString(lat) + ", " +
                Double.toString(lon) + ", " +
                Double.toString(acc);

     //   LocationDbHelper.insertCoordinates(this,lat, lon, acc);
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

        Functions.getAddressFromLocation(lat,lon, acc, this, new GeocoderHandler());


//        if (gcd == null){
//             gcd = new Geocoder(this,Locale.getDefault());
//        }
     //   Toast.makeText(this, Functions.getLocationName(gcd,lat,lon), Toast.LENGTH_LONG).show();

    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String result;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    result = bundle.getString("address");
                    break;
                default:
                    result = null;
            }

            Toast.makeText(LocationService.this, result, Toast.LENGTH_SHORT).show();
        }
    }
}