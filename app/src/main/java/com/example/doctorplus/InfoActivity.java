package com.example.doctorplus;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class InfoActivity extends AppCompatActivity {
    String  full_name , position;
    TextView tv1, tv2, tv3, tv4, tv5;
    double score;
    String name[];
    RequestInfo ri = new RequestInfo();
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        tv1 = findViewById(R.id.famedit);
        tv2 = findViewById(R.id.nameedit);
        tv3 = findViewById(R.id.otedit);
        tv4 = findViewById(R.id.posedit);
        tv5 = findViewById(R.id.scoreedit);
        ri.execute(new int[]{1});
        Log.i("asynctest", "aao");
        preferences = getSharedPreferences("user_id", Context.MODE_PRIVATE);
    }
    class RequestInfo extends AsyncTask<int[], Integer, Void> {

        public UserModel getData() {
            int id = preferences.getInt("user_id", -1);
            Log.i("asynctest", "aaooo");
            String set_server_url = "http://192.168.0.14/doctorplus.nti-ar.ru/admin/?table=usersInfo&action=getByField&field=id&value="+id;
            BufferedReader reader = null;

            try {
                URL url = new URL(set_server_url);
                Log.i("asynctest", "aaooo");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                Log.i("asynctest", "ooo");
                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                StringBuilder buf = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    buf.append(line += "\n");
                }
                UserModel iu;
                Gson gson = new Gson();
                String temp = buf.toString();
                Log.i("asynctest", ""+temp);
                iu = gson.fromJson(buf.toString(), UserModel.class);
                Log.i("asynctest", "ooO");
                return iu;
            }
            catch (IOException e) {
                return new UserModel();
            }
        }

        protected Void doInBackground(int[]... values) {
            for (int value : values[0]) {
                UserModel iu = getData();
                full_name = iu.full_name;
                name = full_name.split(" ");
                score = iu.rate;
                position = iu.position;
                Log.i("asynctest", ""+iu.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            tv1.setText(name[0]);
            tv2.setText(name[1]);
            tv3.setText(name[2]);
            tv4.setText(position);
            tv5.setText(Double.toString(score));
            //Intent i = new Intent(MainActivity.this, GameCLass.class);
            //i.putExtra("name", String.valueOf(nick));
            //i.putExtra("token", token);
            //startActivity(i);
        }

    }
}