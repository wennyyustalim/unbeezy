package com.sams.unbeezy.services.dismisser;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class RiseAndShineDismisserService extends PanicDismisserService implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mLight;

    private String LOG_TAG = "RASD_Service";
    private float THRESHOLD = 30;

    RiseAndShineDismisserService() {
        super("RiseAndShineService", "Rise and Shine!", "Turn on your light! ");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "Service Created");
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_LIGHT) {
            if(sensorEvent.values[0] >= THRESHOLD) {
                mSensorManager.unregisterListener(this, mLight);
                dismiss();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

}
