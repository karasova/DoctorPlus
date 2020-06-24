package com.example.doctorplus;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.core.content.ContextCompat;

public class CallsListAdapter extends BaseAdapter {
    Context ctx;
    ArrayList<CallModel> calls;

    public CallsListAdapter(Context ctx, ArrayList<CallModel> calls) {
        this.ctx = ctx;
        this.calls = calls;
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
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(Color.RED);
            }
        });
        return convertView;
    }
}
