package com.example.doctorplus;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SettingsActivity extends AppCompatActivity {

    EditText et1, et2;
    TextView tv;
    int prev;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        et1 = findViewById(R.id.logedit);
        et2 = findViewById(R.id.passedit);
        tv = findViewById(R.id.tres);
        prev = 1;
        preferences = getSharedPreferences("user_id", Context.MODE_PRIVATE);
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
                tv.setText("Ни одно поле не заполнено");
            }
            else
                tv.setText("Данные обновлены");
        }
    }

    public void onClick(View v) throws IOException, InterruptedException {
        Request1 async = new Request1();
        async.execute(new int[]{1});
    }
}
