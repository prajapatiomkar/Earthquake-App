package com.example.earthquakeapp;

public class Earthquake {

    double mMagnitude;
    String mLocation;
    String mUrl;
    long mTimeInMilliSecond;

    Earthquake(double mMagnitude, String mLocation, long mTimeInMilliSecond, String mUrl) {
        this.mMagnitude = mMagnitude;
        this.mLocation = mLocation;
        this.mUrl = mUrl;
        this.mTimeInMilliSecond = mTimeInMilliSecond;
    }

    public double getmMagnitude() {
        return mMagnitude;
    }

    public String getmLocation() {
        return mLocation;
    }

    public String getmUrl() {
        return mUrl;
    }

    public long getmTimeInMilliSecond() {
        return mTimeInMilliSecond;
    }
}
