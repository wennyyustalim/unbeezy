package com.sams.unbeezy;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class GPSTracker extends Service implements LocationListener {
    String LOG_TAG = "GPSTracker";
    private final Context mContext;

    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;

    Location location;

    double latitude;
    double longitude;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = TimeUnit.MINUTES.toMillis(1)

    protected LocationManager locationManager;

    public GPSTracker(Context context) {
        this.mContext = context;
        getLocation();
    }

    public Location getLocation() {
        Log.d(LOG_TAG, "Getting location...");
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
                Log.d(LOG_TAG, "GPS and Network is not enabled.");
                showSettingsAlert();
            } else {
                this.canGetLocation = true;
                if ( ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions((Activity)mContext, new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    }, 10);
                }
                if ( ContextCompat.checkSelfPermission( mContext, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions((Activity)mContext, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, 11);
                }
                // First get location from Network Provider

                if (isNetworkEnabled) {
                    Log.d(LOG_TAG, "Location is enabled");
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d(LOG_TAG, "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d(LOG_TAG, "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                Log.d(LOG_TAG, String.format("Lat: %f, Lon: %f", latitude, longitude));
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     * */
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }
        return longitude;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public double distanceFromITB(double lat, double lon) {
        final int R = 6371000; // Radius of the earth

        double itbLat = -6.891422;
        double itbLong = 107.610667;
        double latDistance = Math.toRadians(itbLat - lat);
        double lonDistance = Math.toRadians(itbLong - lon);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(itbLong))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c ;

        return distance;
    }

    public Boolean isOutsideRange() {
        double distance = distanceFromITB(location.getLatitude(), location.getLongitude());
        Log.d(LOG_TAG, String.format("Lat: %f, Long: %f", location.getLatitude(), location.getLongitude()));
        Log.d(LOG_TAG, String.format("Distance from itb: %f", distance));
        if (distance > 300) {
            Log.d(LOG_TAG, String.format("You're outside ITB"));
            return Boolean.TRUE;
        }
        Log.d(LOG_TAG, String.format("You're inside ITB"));
        return Boolean.FALSE;
    }

    @Override
    public void onLocationChanged(Location location) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}
