package com.sams.unbeezy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

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
    }

}
