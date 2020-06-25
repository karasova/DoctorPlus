package com.example.doctorplus;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class CallModel implements Parcelable {
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
    @SerializedName("contact_name")
    String contact_name;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(adress);
        dest.writeString(status);
        dest.writeString(cause);
        dest.writeString(patient_info);
        dest.writeString(contact_name);
        dest.writeString(contact_number);
        dest.writeString(priority);
    }

    public static final Parcelable.Creator<CallModel> CREATOR = new Parcelable.Creator<CallModel>(){
        public CallModel createFromParcel (Parcel in) {
            return new CallModel(in);
        }

        public CallModel[] newArray(int size) {
            return new CallModel[size];
        }
    };

    private CallModel(Parcel in) {
        this.id = in.readInt();
        this.adress = in.readString();
        this.status = in.readString();
        this.cause = in.readString();
        this.patient_info = in.readString();
        this.contact_name = in.readString();
        this.contact_number = in.readString();
        this.priority = in.readString();
    }
}
