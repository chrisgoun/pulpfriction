package com.dev.gounaris.pulpfriction;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            openListPage();
        } else{
            openLogin();
        }
    }

    private void openListPage(){
        final Intent intent = new Intent(this, List.class);
        new Timer().schedule(new TimerTask(){
            @Override
            public void run(){
                startActivity(intent);
            }
        }, 400);
    }

    private void openLogin(){
        final Intent intent = new Intent(this, loginPage.class);
        new Timer().schedule(new TimerTask(){
            @Override
            public void run(){
                startActivity(intent);
            }
        }, 400);
    }

}
