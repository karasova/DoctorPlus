package com.example.doctorplus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class NoCallActivity extends AppCompatActivity {
    TextView date;
    Date currentTime = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("dd MMMM");

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_call);
        Log.d("callTasking", "uge tut");

        date = findViewById(R.id.date);
        String formattedDate = df.format(currentTime);
        date.setText(formattedDate);


    }

    public void onClick(View v) {
        if (v.getId() == R.id.open_calls) {
            Log.d("mytag", "tut Intent");
            Intent i = new Intent(NoCallActivity.this, OpenCallsActivity.class);
            startActivity(i);
        }

        if (v.getId() == R.id.close_calls) {
            Intent i = new Intent(NoCallActivity.this, ClosedCallsActivity.class);
            startActivity(i);
        }
    }
}
