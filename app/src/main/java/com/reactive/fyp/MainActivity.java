package com.reactive.fyp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.google.android.material.button.MaterialButton;
import com.reactive.fyp.Activities.HomeActivity;
import com.reactive.fyp.Activities.SignInActivitye;
import com.reactive.fyp.Utils.Helper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Helper.IsLogin(MainActivity.this)){
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(MainActivity.this, SignInActivitye.class));
                    finish();
                }

            }
        },2000);
    }
}