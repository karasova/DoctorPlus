package com.example.doctorplus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class OpenCallsActivity extends AppCompatActivity {
    final static String URL_LOC = "http://192.168.0.14/doctorplus.nti-ar.ru/admin/?table=calls&action=getAll";
    SharedPreferences preferences;

    CallsListAdapter adapter;
    ListView listView;
    ArrayList<CallModel> infos = new ArrayList<>();
    ArrayList<CallModel> infos_two = new ArrayList<>();

    ImageView calls, account, home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_calls);
        listView = findViewById(R.id.list);
        preferences = getSharedPreferences("user_id", Context.MODE_PRIVATE);
        adapter = new CallsListAdapter(this, infos, preferences);
        listView.setAdapter(adapter);

        calls = findViewById(R.id.calls);
        calls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OpenCallsActivity.this, CallActivity.class);
                startActivity(i);
            }
        });
        account = findViewById(R.id.account);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OpenCallsActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });
        home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OpenCallsActivity.this, StartActivity.class);
                startActivity(i);
            }
        });

        RequestTask task = new RequestTask();
        task.execute(new int[]{1});
    }

    public void onClick(View v) {
        Intent i = new Intent(OpenCallsActivity.this, StartActivity.class);
        startActivity(i);
    }

    class RequestTask extends AsyncTask<int[], Integer, Void> {

        public CallResponse getData() {
            BufferedReader reader = null;
            try {
                URL url = new URL(URL_LOC);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder buf = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    buf.append(line += "\n");
                }
//                Log.d("asynctest", buf.toString());
//                Log.d("asynctest", "ia tut");
                Gson gson = new Gson();
                String temp = buf.toString();
                temp = temp.replace('"', '\'');
                temp = temp.replace('[', '"');
                temp = temp.replace(']', '"');
                temp = temp.replace("'calls'", "\"calls\"");
                temp = temp.replace("},{", "};{");
                Log.d("asynctest", temp);
//                RegisterData reg = gson.fromJson(new InputStreamReader(urlConnection.getInputStream()), RegisterData.class);
                CallResponse call = gson.fromJson(temp, CallResponse.class);
//                Log.d("asynctest", call.calls);
                return call;

            } catch (IOException e) {
                return new CallResponse();
            }
        }

        public ArrayList<CallModel> getCalls(CallResponse call) {
            String[] temps = call.calls.split(";");
            ArrayList<CallModel> triko = new ArrayList<>();

            for (String temp : temps) {
                temp = temp.replace(":,", ":'',");
                temp = temp.replace('\'', '"');
                Gson gson = new Gson();
                CallModel info = gson.fromJson(temp, CallModel.class);
                if (info.status.equals("open")) {
                    triko.add(info);
                }
            }

            return triko;
        }

        @Override
        protected Void doInBackground(int[]... ints) {
            for (int var: ints[0]) {
                CallResponse reg = getData();
                infos_two = getCalls(reg);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            infos.clear();
            infos.addAll(infos_two);
            Log.d("asynctest", String.valueOf(infos.size()));
            adapter.notifyDataSetChanged();
            super.onPostExecute(aVoid);
        }
    }
}
