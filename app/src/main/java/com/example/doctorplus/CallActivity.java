package com.example.doctorplus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CallActivity extends AppCompatActivity {

    SharedPreferences preferences;
    int user_id;
    CallModel call;
    boolean emptycall = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = getSharedPreferences("user_id", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        user_id = preferences.getInt("user_id", -1);
        CurrentTask task = new CurrentTask();
        task.execute(true);
    }

    class CurrentTask extends AsyncTask<Boolean, Integer, Void> {

        public void getCurrentCall() {
            String url_server = "http://192.168.0.14/doctorplus.nti-ar.ru/admin/?table=calls&action=getByField&field=owner_id&value=" + user_id + "&status=active";
            Log.i("callTasking", String.valueOf(user_id));
            try {
                URL url = new URL(url_server);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder buf = new StringBuilder();
                String line = null;

                while ((line = reader.readLine()) != null) {
                    buf.append(line += "\n");
                }

                Log.i("callTasking", buf.toString());

                if (buf.toString().contains("staus")) {
                    emptycall = true;
                    Log.d("callTasking", "Netu tek vis");
                    return;
                }

                Gson gson = new Gson();

                call = gson.fromJson(buf.toString(), CallModel.class);

                Log.d("callTasking", call.toString());
            }
            catch (IOException e) {
                return;
            }
        }

        @Override
        protected Void doInBackground(Boolean... ints) {
            for (Boolean var: ints) {
                getCurrentCall();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (emptycall) {
                Intent i = new Intent(CallActivity.this, NoCallActivity.class);
                startActivity(i);
            }
            else {
                Intent i = new Intent(CallActivity.this, ActiveCallActivity.class);
                i.putExtra("calls", call);
                Log.i("active_call", "" + call.cause);
                startActivity(i);
            }
            super.onPostExecute(aVoid);
        }
    }
}
