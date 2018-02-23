package com.sams.unbeezy.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.sams.unbeezy.MainActivity;
import com.sams.unbeezy.receivers.AlarmReceiver;

public class LocationService extends IntentService {
    LocationService() {
        super("LocationService");
    }

    public static final String BROADCAST_ACTION = "Hello World";
    public static final String LOG_TAG = "LocationService";
    private static final int TWO_MINUTES = 1000 * 60 * 2;
    final double ITB_LATITUDE = -6.891422;
    final double ITB_LONGITUDE = 107.610667;
    public LocationManager locationManager;
    public MyLocationListener listener;
    public Location previousBestLocation = null;

    Intent intent;
    int counter = 0;

    @Override
    public void onCreate()
    {
        super.onCreate();
        intent = new Intent(BROADCAST_ACTION);
    }

    @Override
    public void onStart(Intent intent, int startId)
    {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(previousBestLocation ==null) {
            listener = new MyLocationListener();
            try {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 4000, 0, listener);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 0, listener);
            } catch (SecurityException e) {
                e.printStackTrace();
                Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent2);
            }
        }else {
            broadcastIntent();
        }

    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }



    /** Checks whether two providers are the same */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }



    @Override
    public void onDestroy() {
        // handler.removeCallbacks(sendUpdatesToUI);
        super.onDestroy();
        Log.v("STOP_SERVICE", "DONE");
        locationManager.removeUpdates((LocationListener) listener);
    }

    public static Thread performOnBackgroundThread(final Runnable runnable) {
        final Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } finally {

                }
            }
        };
        t.start();
        return t;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(LOG_TAG, "Intent Received");
        if (intent != null) {
            Log.d(LOG_TAG, "Intent Received");
            if(previousBestLocation != null) {
                broadcastIntent();
            }
        }
    }

    public void broadcastIntent()  {
        Log.d(LOG_TAG, "Broadcasing intent");
        Intent intent1 = new Intent(getApplicationContext(), AlarmReceiver.class);
        if(isOutsideRange()) {
            intent1.setAction(AlarmReceiver.LOCATION_RECEIVED_ACTION_OUTSIDE_RANGE);
        } else {
            intent1.setAction(AlarmReceiver.LOCATION_RECEIVED_ACTION_INSIDE_RANGE);
        }
        sendBroadcast(intent1);
        stopSelf();

    }
    public Location getITBLoc() {
        Location ITBLocation = new Location("ITB");
        ITBLocation.setLatitude(ITB_LATITUDE);
        ITBLocation.setLongitude(ITB_LONGITUDE);
        return ITBLocation;
    }

    public double distanceBetween(Location a, Location b) {
        return a.distanceTo(b);
    }

    public Boolean isOutsideRange() {
        double distance = distanceBetween(getITBLoc(), previousBestLocation);
        if (distance > 600) {
            Log.d(LOG_TAG, String.format("You're outside ITB"));
            return Boolean.TRUE;
        }
        Log.d(LOG_TAG, String.format("You're inside ITB"));
        return Boolean.FALSE;
    }

    public class MyLocationListener implements LocationListener
    {

        public void onLocationChanged(final Location loc)
        {
            Log.i("LocationListener", "Location changed");
            if(isBetterLocation(loc, previousBestLocation)) {
                loc.getLatitude();
                loc.getLongitude();
                previousBestLocation = loc;
                broadcastIntent();
            }
        }


        public void onProviderDisabled(String provider)
        {
            Toast.makeText( getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT ).show();
        }


        public void onProviderEnabled(String provider)
        {
            Toast.makeText( getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
        }


        public void onStatusChanged(String provider, int status, Bundle extras)
        {

        }

    }
}
