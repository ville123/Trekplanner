package com.trekplanner.app.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.trekplanner.app.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

        String nickname = pref.getString("preference_nickname", null);
        if (nickname != null && !nickname.isEmpty()) {
            TextView view = findViewById(R.id.splash_subtext);
            view.setText(nickname);
        }
        Log.d("TREK_SplashActivity", "Found nickname for the user: " + nickname);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
            }
        }.start();
    }
}
