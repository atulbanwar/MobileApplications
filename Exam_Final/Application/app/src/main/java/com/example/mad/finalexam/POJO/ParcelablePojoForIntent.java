package com.example.mad.finalexam.POJO;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelablePojoForIntent implements Parcelable {
    private int variable1;
    private String variable2;
    private String variable3;

    public ParcelablePojoForIntent(int variable1, String variable2) {
        this.variable1 = variable1;
        this.variable2 = variable2;
    }

    public int getVariable1() {
        return variable1;
    }

    public void setVariable1(int variable1) {
        this.variable1 = variable1;
    }

    public String getVariable2() {
        return variable2;
    }

    public void setVariable2(String variable2) {
        this.variable2 = variable2;
    }

    public String getVariable3() {
        return variable3;
    }

    public void setVariable3(String variable3) {
        this.variable3 = variable3;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(variable1);
        dest.writeString(variable2);
    }

    public static final Parcelable.Creator<ParcelablePojoForIntent> CREATOR
            = new Parcelable.Creator<ParcelablePojoForIntent>() {
        public ParcelablePojoForIntent createFromParcel(Parcel in) {
            return new ParcelablePojoForIntent(in);
        }

        public ParcelablePojoForIntent[] newArray(int size) {
            return new ParcelablePojoForIntent[size];
        }
    };

    private ParcelablePojoForIntent(Parcel in) {
        variable1 = in.readInt();
        variable2 = in.readString();
    }
}
