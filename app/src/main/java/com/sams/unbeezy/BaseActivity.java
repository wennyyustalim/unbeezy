package com.sams.unbeezy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.sams.unbeezy.services.FirebaseDatabaseService;

/**
 * Created by kennethhalim on 2/9/18.
 */

public abstract class BaseActivity extends AppCompatActivity{
    String personName;
    private String LOG_TAG = "BaseActivity";
    SharedPreferences pref;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getSharedPreferences("UnbeezyPref", Context.MODE_PRIVATE);
    }

    @Override
    public void onStart(){
        super.onStart();
        checkAuthenticated();
        FirebaseDatabaseService.getInstance().child("fcm_token").setValue(FirebaseInstanceId.getInstance().getToken());
    }

    public void checkAuthenticated() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if(user == null) {
            Intent intent = new Intent(this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            // Get the name of the person and store in shared preferences
            personName = user.getDisplayName();
            Log.d("BaseActivity", personName);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("personName", personName);
            editor.apply();
        }
    }
    public void signOut(View view) {
        FirebaseAuth.getInstance().signOut();
        checkAuthenticated();
    }

    public void closeActivity(){
        finish();
    }

    public void setToolbar(String title) {

        ImageButton backButton = findViewById(R.id.toolbar_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeActivity();
            }
        });
        TextView titleView = findViewById(R.id.title_view);
        titleView.setText(title);
        try {
            personName = pref.getString("personName", null);
            Log.d(LOG_TAG, String.format("Person's name: %s", personName));
            String initial = Character.toString(personName.charAt(0));
            Log.d(LOG_TAG, String.format("Initial: %s", initial));
            TextView nameInitial = findViewById(R.id.name_initial);
            nameInitial.setText(initial);
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.d(LOG_TAG, "Person's Name not found");
        }
    }

}
