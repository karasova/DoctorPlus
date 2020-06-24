package com.example.doctorplus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = getSharedPreferences("user_id", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);


        Log.i("main", "hello");
        int user_id = preferences.getInt("user_id", -1);
        Log.i("user_id","user_id main " + user_id);
        if (user_id == -1) {
            Intent i = new Intent(MainActivity.this, EnterActivity.class);
            startActivity(i);
            Log.i("msg", "hello");
        }
        else {
            Intent i = new Intent(MainActivity.this, StartActivity.class);
            startActivity(i);
        }
    }
}












