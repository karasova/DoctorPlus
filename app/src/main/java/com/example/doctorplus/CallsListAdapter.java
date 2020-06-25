package com.example.doctorplus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import androidx.core.content.ContextCompat;

public class CallsListAdapter extends BaseAdapter {
    Context ctx;
    ArrayList<CallModel> calls;
    SharedPreferences preferences;

    public CallsListAdapter(Context ctx, ArrayList<CallModel> calls, SharedPreferences preferences) {
        this.ctx = ctx;
        this.calls = calls;
        this.preferences = preferences;
        Log.d("asynctest", "privet");
    }

    @Override
    public int getCount() {
        Log.d("asynctest", String.valueOf(calls.size()));
        return calls.size();
    }

    @Override
    public Object getItem(int position) {
        return calls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CallModel call = calls.get(position);


        convertView = LayoutInflater.from(ctx).inflate(R.layout.activity_call, parent, false);
        TextView adress = convertView.findViewById(R.id.adress);
        TextView cause = convertView.findViewById(R.id.cause);

        adress.setText(call.adress);
        cause.setText(call.cause);
        LinearLayout layout = convertView.findViewById(R.id.layout);
        if (call.priority.equals("yes")) {
            layout.setBackground(ContextCompat.getDrawable(ctx, R.drawable.field_orange));
            adress.setTextColor(ContextCompat.getColor(ctx, R.color.textColor));
            cause.setTextColor(ContextCompat.getColor(ctx, R.color.textColor));
        }
        else {
            layout.setBackground(ContextCompat.getDrawable(ctx, R.drawable.field_blue));
        }

        if (call.status.equals("closed")) {
            layout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    v.setBackgroundColor(Color.BLUE);
                }
            });
        }
        else {
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChangeTask task = new ChangeTask();
                    task.execute(call.id);
                }
            });
        }
        return convertView;
    }

    class ChangeTask extends AsyncTask<Integer, Integer, Void> {

        public void changeStatus(int call_id) {
            int user_id = preferences.getInt("user_id", -1);
            Log.i("testForAdapter", String.valueOf(user_id));
            String url_server = "http://192.168.0.14/doctorplus.nti-ar.ru/admin/?table=calls&action=changeStatus&call_id=" + call_id + "&owner_id=" + user_id;

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
        protected Void doInBackground(Integer... call) {
            for (Integer var: call) {
                changeStatus(var);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.i("testForAdapter", "i'm work!!!");
            Intent i = new Intent(ctx, CallActivity.class);
            ctx.startActivity(i);
            super.onPostExecute(aVoid);
        }
    }
}
