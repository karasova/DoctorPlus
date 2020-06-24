package com.example.doctorplus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {
    SharedPreferences preferences;
    ImageView logo;
    TextView date, greet;
    Date currentTime = Calendar.getInstance().getTime();
    SimpleDateFormat df = new SimpleDateFormat("dd MMMM");
    SimpleDateFormat tf = new SimpleDateFormat("HH");
    UsersFetch users_fetch = new UsersFetch();
    LinearLayout layout;

    ImageView calls, account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        layout = findViewById(R.id.layout);

        preferences = getSharedPreferences("user_id", Context.MODE_PRIVATE);

        logo = findViewById(R.id.logo);
        logo.setImageResource(R.drawable.logo);

        date = findViewById(R.id.date);
        String formattedDate = df.format(currentTime);
        date.setText(formattedDate);

        calls = findViewById(R.id.calls);
        calls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartActivity.this, CallActivity.class);
                startActivity(i);
            }
        });
        account = findViewById(R.id.account);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });


        users_fetch.execute(new int[]{1});

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartActivity.this, OpenCallsActivity.class);
                startActivity(i);
            }
        });
    }

    public class UsersFetch extends AsyncTask<int[], Integer, Void> {

        String API_URL = "http://192.168.0.21/";
        String table = "usersInfo";
        String action = "getAll";


        UserModel user;


        public UserModel getData() {
            int id = preferences.getInt("user_id", -1);
            String server_url = "http://192.168.0.14/doctorplus.nti-ar.ru/admin/?table=usersInfo&action=getByField&field=user_id&value=" + id;
            BufferedReader reader = null;

            try {
                URL url = new URL(server_url);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                StringBuilder buf = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    buf.append(line += "\n");
                }

                Gson gson = new Gson();
                String temp = buf.toString();
                temp.concat("}");
                Log.i("asynctest", "iii");
                UserModel user = gson.fromJson(buf.toString(), UserModel.class);
                return user;
            } catch (IOException e) {
                return new UserModel();
            }
        }

        protected Void doInBackground(int[]... values) {
            for (int value : values[0]) {
                this.user = getData();
//            id = reg.id;
//            user_id = reg.user_id;
//            password = reg.password;
//            stat = reg.status;
//            trents.edit().putInt("trent", id).apply();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            greet = findViewById(R.id.greet);
            String full_name = user.full_name;
            String[] name = full_name.split(" ");

            Log.i("user", name[1]);


            int formatedTime = Integer.parseInt(tf.format(currentTime));
            Log.i("user", "time = " + formatedTime);
            if (formatedTime >= 0 && formatedTime <= 4) {
                greet.setText("Доброй ночи, " + name[1] + ".");
            }
            if (formatedTime > 4 && formatedTime < 13) {
                greet.setText("Доброе утро, " + name[1] + ".");
            }
            if (formatedTime >= 13 && formatedTime < 19) {
                greet.setText("Добрый день, " + name[1] + ".");
            }
            if (formatedTime >= 19 && formatedTime <= 23) {
                greet.setText("Добрый вечер, " + name[1] + ".");
            }
        }

    }
}

