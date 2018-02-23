package com.sams.unbeezy.receivers;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzceb;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.sams.unbeezy.PanicAttackActivity;
import com.sams.unbeezy.R;
import com.sams.unbeezy.fragments.AlarmFragment;
import com.sams.unbeezy.lists.DismisserServicesList;
//import com.sams.unbeezy.services.LocationService;
import com.sams.unbeezy.services.LocationService;
import com.sams.unbeezy.services.SchedulingService;

import java.security.Provider;
import java.util.HashMap;

/**
 * Created by wennyyustalim on 19/02/18.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private static final int NOTIFICATION_ID = 0;
    private String TAG = "AlarmReceiver";
    public static final String ALARM_START_ACTION = "START_PANIC_ATTACK";
    public static final String ALARM_CHECK_LOCATION = "START_CHECK_LOCATION";
    public static final String LOCATION_RECEIVED_ACTION = "LOCATION_RECEIVED";
    public static final String LOCATION_IN_RANGE_CODE = "locationInrange";
    public static final Boolean LOCATION_IN_RANGE = true;
    public static final Boolean LOCATION_OUT_RANGE = false;
    public AlarmReceiver() {

    }
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Intent received");
        if(intent.getAction().equals(ALARM_START_ACTION)) {
            Intent intent1 = new Intent(context, PanicAttackActivity.class);
            SharedPreferences sharedPreferences = context.getSharedPreferences("UnbeezyPref",Context.MODE_PRIVATE);
            String code = sharedPreferences.getString("dismisserMode",null);
            intent1.putExtra(DismisserServicesList.DISMISSER_CLASS_INTENT_CODE,code);
            context.startActivity(intent1);
        } else  if(intent.getAction().equals(ALARM_CHECK_LOCATION)){
            Log.d(TAG, "Location Received!");
            Intent intent1  = new Intent(context, LocationService.class);
            context.startService(intent1);
        } else if(intent.getAction().equals(LOCATION_RECEIVED_ACTION)) {

        }



    }
}
