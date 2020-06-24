package com.example.doctorplus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.appcompat.app.AppCompatActivity;

public class EnterActivity extends AppCompatActivity {
    EditText et1, et2;
    String user_id, password, stat;
    TextView tv;
    int id;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        et1 = findViewById(R.id.user_id);
        et2 = findViewById(R.id.pass);
        tv = findViewById(R.id.t1);
        preferences = getSharedPreferences("user_id", Context.MODE_PRIVATE);
    }
    class RequestTask extends AsyncTask<int[], Integer, Void> {

        public Login getData() {
            String sid = et1.getText().toString();
            String set_server_url = "http://192.168.0.14/doctorplus.nti-ar.ru/admin/?table=usersPass&action=getByField&field=user_id&value="+sid;
            BufferedReader reader = null;

            try {
                URL url = new URL(set_server_url);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                StringBuilder buf = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    buf.append(line += "\n");
                }
                Login reg;
                Gson gson = new Gson();
                String temp = buf.toString();
                reg = gson.fromJson(buf.toString(), Login.class);
                return reg;
            }
            catch (IOException e) {
                return new Login();
            }
        }

        protected Void doInBackground(int[]... values) {
            for (int value : values[0]) {
                Login reg = getData();
                id = reg.id;
                user_id = reg.user_id;
                password = reg.password;
                stat = reg.status;
                Log.i("asynctest", ""+reg.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(stat == "net"){
                tv.setText("This user has not been added yet");
            }
            else {
                if (et2.getText().toString().equals(password)) {
                    tv.setText("Success");
                    preferences.edit().putInt("user_id", id).apply();
                    Log.i("user_id","user_id enter " + id);
                    Intent i = new Intent(EnterActivity.this, StartActivity.class);
                    startActivity(i);
                } else {
                    tv.setText("INCORRECT PASSWORD");
                }
            }
            //Intent i = new Intent(MainActivity.this, GameCLass.class);
            //i.putExtra("name", String.valueOf(nick));
            //i.putExtra("token", token);
            //startActivity(i);
        }

    }

    public void onClick(View v) throws IOException {
        RequestTask async = new RequestTask();
        async.execute(new int[]{1});
        Log.i("user_id","boo");
    }
}







