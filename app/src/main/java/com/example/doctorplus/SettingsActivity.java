package com.example.doctorplus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SettingsActivity extends AppCompatActivity {

    EditText et1, et2;
    int prev;

    SharedPreferences preferences;
    ImageView calls, home, account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        et1 = findViewById(R.id.logedit);
        et2 = findViewById(R.id.passedit);
        prev = 1;
        preferences = getSharedPreferences("user_id", Context.MODE_PRIVATE);

        calls = findViewById(R.id.calls);
        calls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingsActivity.this, CallActivity.class);
                startActivity(i);
            }
        });
        home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingsActivity.this, StartActivity.class);
                startActivity(i);
            }
        });
        account = findViewById(R.id.account);
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingsActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });
    }


    class Request1 extends AsyncTask<int[], Integer, Void> {

        public int getData() {
            int id = preferences.getInt("user_id", -1);
            if((et1.getText().length()==0) && (et2.getText().length()==0)){
                return 1;
            }
            if(et1.getText().length()!=0){
                String log =  et1.getText().toString();

                String login_url = "http://192.168.0.14/doctorplus.nti-ar.ru/admin/?table=usersPass&action=update&field=user_id&idf="+id+"&newvalue="+log;
                try {
                    URL url1 = new URL(login_url);
                    HttpURLConnection urlConnection1 = (HttpURLConnection) url1.openConnection();
                    urlConnection1.connect();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection1.getInputStream()));
                } catch (IOException e) {
                    return 0;
                }
            }
            if(et2.getText().length()!=0){
                String pass =  et2.getText().toString();
                String pass_url = "http://192.168.0.14/doctorplus.nti-ar.ru/admin/?table=usersPass&action=update&field=password&idf="+id+"&newvalue="+pass;
                try {
                    URL url2 = new URL(pass_url);
                    HttpURLConnection urlConnection2 = (HttpURLConnection) url2.openConnection();
                    urlConnection2.connect();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection2.getInputStream()));
                } catch (IOException e) {
                    return 0;
                }
            }
            return 1;
        }
        protected Void doInBackground(int[]... values) {
            for (int value : values[0]) {
                int iu = getData();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if((et1.getText().length()==0) && (et2.getText().length()==0)){
                Toast toast = Toast.makeText( getApplicationContext(), "Ни одно поле не заполнено", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                Toast toast = Toast.makeText( getApplicationContext(), "Данные обновлены", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    public void onClick(View v) throws IOException, InterruptedException {
        if (v.getId() == R.id.butt) {
            Request1 async = new Request1();
            async.execute(new int[]{1});
        }
        if (v.getId() == R.id.back) {
            Intent i = new Intent(SettingsActivity.this, ProfileActivity.class);
            startActivity(i);
        }
    }
}
