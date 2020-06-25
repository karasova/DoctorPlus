package com.example.doctorplus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class ClosedCallsActivity extends AppCompatActivity {
    SharedPreferences preferences;
    int user_id;
    CallsListAdapter adapter;
    ListView listView;
    ArrayList<CallModel> infos = new ArrayList<>();
    ArrayList<CallModel> infos_two = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closed_calls);
        listView = findViewById(R.id.list);
        preferences = getSharedPreferences("user_id", Context.MODE_PRIVATE);
        adapter = new CallsListAdapter(this, infos, preferences);
        listView.setAdapter(adapter);
        user_id = preferences.getInt("user_id", -1);
        ClosedTask task = new ClosedTask();
        task.execute(new int[]{1});
    }

    class ClosedTask extends AsyncTask<int[], Integer, Void> {

        public ArrayList<CallModel> getClosedCalls() {
            String url_server = "http://192.168.0.14/doctorplus.nti-ar.ru/admin/?table=calls&action=getByField&field=owner_id&value=" + user_id;

            try {
                URL url = new URL(url_server);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder buf = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    Log.d("callTasking", line);
                    buf.append(line += "\n");
                }

                String tert = buf.toString().replace("}{", "};{");
                String[] temps = tert.split(";");
                ArrayList<CallModel> for_adapter = new ArrayList<>();
                Log.d("callTasking", String.valueOf(temps.length));

                for (String temp: temps) {
                    Log.d("callTasking", temp);
                    Gson gson = new Gson();
                    CallModel model = gson.fromJson(temp, CallModel.class);
                    if (model.status.equals("closed")) {
                        for_adapter.add(model);
                    }
                }

                return for_adapter;
            }
            catch (IOException e) {
                return new ArrayList<>();
            }
        }

        @Override
        protected Void doInBackground(int[]... ints) {
            for (int var: ints[0]) {
                infos_two = getClosedCalls();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (infos_two.size() != 0) {
                infos.clear();
                infos.addAll(infos_two);
                adapter.notifyDataSetChanged();
            }
            super.onPostExecute(aVoid);
        }
    }

    public void onClick(View v) {
        Intent i = new Intent(ClosedCallsActivity.this, NoCallActivity.class);
        startActivity(i);
    }
}
