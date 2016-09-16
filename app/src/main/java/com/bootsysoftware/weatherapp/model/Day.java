package com.bootsysoftware.weatherapp.model;

/**
 * Created by Matthew Boydston on 9/16/2016.
 */
public class Day {
    private long mTime;
    private String mSummary;
    private double mTemperatureMax;
    private String mIcon;
    private String mTimezome;

    public Day(long time, String summary, double temperatureMax, String icon, String timezome) {
        mTime = time;
        mSummary = summary;
        mTemperatureMax = temperatureMax;
        mIcon = icon;
        mTimezome = timezome;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public double getTemperatureMax() {
        return mTemperatureMax;
    }

    public void setTemperatureMax(double temperatureMax) {
        mTemperatureMax = temperatureMax;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getTimezome() {
        return mTimezome;
    }

    public void setTimezome(String timezome) {
        mTimezome = timezome;
    }
}
