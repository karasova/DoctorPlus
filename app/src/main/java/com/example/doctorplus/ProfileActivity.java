package com.example.doctorplus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    SharedPreferences preferences;
    ImageView calls, home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        preferences = getSharedPreferences("user_id", Context.MODE_PRIVATE);
        calls = findViewById(R.id.calls);
        calls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, ActiveCallActivity.class);
                startActivity(i);
            }
        });
        home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, StartActivity.class);
                startActivity(i);
            }
        });
    }

    public void onClick(View v){
        if (v.getId() == R.id.butexit) {
            preferences.edit().putInt("user_id", -1).apply();
            Intent i = new Intent(ProfileActivity.this, EnterActivity.class);
            startActivity(i);
        }
        if (v.getId() == R.id.butinf) {
            Log.i("profile", "hello");
            Intent i = new Intent(ProfileActivity.this, InfoActivity.class);
            startActivity(i);
        }
        if (v.getId() == R.id.butnastr) {
            Intent i = new Intent(ProfileActivity.this, SettingsActivity.class);
            startActivity(i);
        }
    }
}
