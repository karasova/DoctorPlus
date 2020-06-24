package com.example.doctorplus;

public class UserModel {
    int id;
    int user_id;
    String full_name;
    String position;
    float rate;

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
