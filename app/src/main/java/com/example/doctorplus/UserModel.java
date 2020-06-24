package com.example.doctorplus;

import com.google.gson.annotations.SerializedName;

public class UserModel {
    @SerializedName("table")
    public String table;
    @SerializedName("id")
    public int id;
    @SerializedName("user_id")
    public String user_id;
    @SerializedName("full_name")
    public String full_name;
    @SerializedName("position")
    public String position;
    @SerializedName("rate")
    public double rate;

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", full_name='" + full_name + '\'' +
                ", position='" + position + '\'' +
                ", rate=" + rate +
                '}';
    }
}
