package com.example.doctorplus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class ActiveCallActivity extends AppCompatActivity {

    SharedPreferences preferences;
    int user_id;
    CallModel call;
    TextView adr, cause, patin, contact;
    TextView date;
    Date currentTime = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("dd MMMM");
    boolean emptycall = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = getSharedPreferences("user_id", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_call);
        preferences.edit().putInt("user_id", 1).apply();
        user_id = preferences.getInt("user_id", -1);

        date = findViewById(R.id.date);
        String formattedDate = df.format(currentTime);
        date.setText(formattedDate);

        adr = findViewById(R.id.adress);
        cause = findViewById(R.id.cause);
        patin = findViewById(R.id.patient_info);
        contact = findViewById(R.id.contact);

        Intent i = getIntent();
        call =  i.getParcelableExtra("calls");

        //Log.i("active_call", "2 " + call.cause);


//
        adr.setText(call.adress);
        cause.setText(call.cause);
        patin.setText(call.patient_info);
        contact.setText(call.contact_name + ", " + call.contact_number);

    }

    class CurrentTask extends AsyncTask<Boolean, Integer, Void> {

        public void changeStatus() {
            String url_server = "http://192.168.0.14/doctorplus.nti-ar.ru/admin/?table=calls&action=changeStatus&call_id=" + call.id;

            try {
                URL url = new URL(url_server);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            }
            catch (IOException e) {
                return;
            }
        }

        @Override
        protected Void doInBackground(Boolean... ints) {
            for (Boolean var: ints) {
                    changeStatus();
            }
            return null;
        }
    }

    public void onClick(View v) {
        if (v.getId() == R.id.card) {
            Log.d("mytag", String.valueOf(call.id));
        }

        if (v.getId() == R.id.complete) {
            CurrentTask tak2 = new CurrentTask();
            tak2.execute(false);
        }
    }
}
