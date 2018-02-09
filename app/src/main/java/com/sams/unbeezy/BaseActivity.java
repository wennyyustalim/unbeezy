package com.sams.unbeezy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by kennethhalim on 2/9/18.
 */

public abstract class BaseActivity extends AppCompatActivity{
    @Override
    public void onStart(){
        super.onStart();
        checkAuthenticated();
    }

    public void checkAuthenticated() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        if(user == null) {
            Intent intent = new Intent(this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }
    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        checkAuthenticated();
    }

}
