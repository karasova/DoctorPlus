package com.example.doctorplus;

import com.google.gson.annotations.SerializedName;

public class CallResponse {
    @SerializedName("calls")
    String calls;

    @Override
    public String toString() {
        return "Call{" +
                "calls='" + calls + '\'' +
                '}';
    }
}
