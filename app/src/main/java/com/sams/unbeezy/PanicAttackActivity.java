package com.sams.unbeezy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.sams.unbeezy.lists.DismisserServicesList;
import com.sams.unbeezy.services.dismisser.PanicDismisserService;
import com.sams.unbeezy.services.dismisser.RiseAndShineDismisserService;
import com.sams.unbeezy.services.dismisser.ShakeItOffDismisserService;

import org.w3c.dom.Text;

public class PanicAttackActivity extends BaseActivity {
    MediaPlayer mediaPlayer;
    BroadcastReceiver receiver;
    Class dismisser;
    String dismisserCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panic_attack);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
        mediaPlayer.setLooping(true);
        dismisserCode = getIntent().getStringExtra(DismisserServicesList.DISMISSER_CLASS_INTENT_CODE);
        dismisser = DismisserServicesList.getDismisserClass(dismisserCode);
        TextView titleView = findViewById(R.id.title_view);
        TextView descriptionView = findViewById(R.id.description_view);
        titleView.setText(DismisserServicesList.getTitle(dismisserCode));
        descriptionView.setText(DismisserServicesList.getDescription(dismisserCode));
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

        Intent intent = new Intent(this, dismisser);
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
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }
}
