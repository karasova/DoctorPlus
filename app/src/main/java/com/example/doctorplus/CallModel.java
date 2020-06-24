package com.example.doctorplus;

import com.google.gson.annotations.SerializedName;

public class CallModel {
    @SerializedName("id")
    int id;
    @SerializedName("status")
    String status;
    @SerializedName("adress")
    String adress;
    @SerializedName("cause")
    String cause;
    @SerializedName("patient_info")
    String patient_info;
    @SerializedName("contact_number")
    String contact_number;
    @SerializedName("priority")
    String priority;

    @Override
    public String toString() {
        return "CallInfo{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", adress='" + adress + '\'' +
                ", cause='" + cause + '\'' +
                ", patient_info='" + patient_info + '\'' +
                ", contact_number='" + contact_number + '\'' +
                '}';
    }
}
