package com.sams.unbeezy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sams.unbeezy.services.dismisser.PanicDismisserService;
import com.sams.unbeezy.services.dismisser.RiseAndShineDismisserService;
import com.sams.unbeezy.services.dismisser.ShakeItOffDismisserService;

public class PanicAttackActivity extends BaseActivity {
    MediaPlayer mediaPlayer;
    BroadcastReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panic_attack);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
        mediaPlayer.setLooping(true);
    }

    @Override
    public void onStart() {
        super.onStart();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PanicDismisserService.ACTION);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                finish();
            }
        };
        registerReceiver(receiver, intentFilter);

        Intent intent = new Intent(this, RiseAndShineDismisserService.class);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }
}
