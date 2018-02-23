package com.sams.unbeezy;

import android.content.Intent;
import android.location.Location;
import android.os.PersistableBundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sams.unbeezy.adapters.MainActivityFragmentsAdapter;

public class MainActivity extends BaseActivity {

    MainActivityFragmentsAdapter mainActivityFragmentsAdapter;
    ViewPager viewPager;
    String SAVE_INSTANCE_STATE_KEY = "MainActivityFragmentConfig";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityFragmentsAdapter = new MainActivityFragmentsAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.main_view_pager);
        viewPager.setAdapter(mainActivityFragmentsAdapter);
        if(savedInstanceState != null ) {
            position = savedInstanceState.getInt(SAVE_INSTANCE_STATE_KEY);
            viewPager.setCurrentItem(position);
        }
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(4);
        setTabIcons(tabLayout);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]
                {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 100);

        GPSTracker gps = new GPSTracker(this);

        if(gps.canGetLocation()){
            Location loc = gps.getLocation();
            double lat = loc.getLatitude();
            double lon = loc.getLongitude();
            Log.d("LOCATION", String.format("Lat: %f, Lon: %f", lat, lon));
            Boolean outsideITB = gps.isOutsideRange();
            if(outsideITB) {
                Log.d("MainActivity", "You're outside!!!!!");
            } else {
                Log.d("MainActivity", "You're inside!!!!!");
            }
            double testDistance = gps.distanceFromITB(-6.888505, 107.613427);
            Log.d("MainActivity", String.format("Jaraknya segini: %f", testDistance));


        } else {
            Log.d("LOCATION", "Location can't be obtained");
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        if(savedInstanceState != null ) {
            int position = savedInstanceState.getInt(SAVE_INSTANCE_STATE_KEY);
            viewPager.setCurrentItem(position);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
//        Button button = findViewById(R.id.button3);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                signOut();
//            }
//        });

//        tabLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                viewPager.setCurrentItem(tabLayout.getSelectedTabPosition());
//            }
//        });
//
//        mainActivityFragmentsAdapter
    }
    int position;
    @Override
    protected void onPause() {
        super.onPause();
        position = viewPager.getCurrentItem();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewPager.setCurrentItem(position);
    }

    public void setTabIcons(TabLayout tabLayout) {
        int icons[]= {
                R.drawable.ic_calendar,
                R.drawable.ic_task_complete,
                R.drawable.ic_alarm_clock,
                R.drawable.ic_man_user
        };
        for(int i = 0; i < icons.length; i++) {
          tabLayout.getTabAt(i).setIcon(icons[i]);
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("MAIN ACTIVITY", "Saving instance state");
        outState.putInt(SAVE_INSTANCE_STATE_KEY, viewPager.getCurrentItem());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
