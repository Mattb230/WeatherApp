package com.bootsysoftware.weatherapp.model;

import com.bootsysoftware.weatherapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Matthew Boydston on 9/15/2016.
 */
public class CurrentForecast {
    private String mIcon;
    private long mTime;
    private double mTemp;
    private double mHumidity;
    private double mPrecipChance;
    private String mSummary;


    private String mTimeZone;

    public CurrentForecast(String icon, long time, double temp, double humidity, double precipChance, String summary, String timeZone) {
        mIcon = icon;
        mTime = time;
        mTemp = temp;
        mHumidity = humidity;
        mPrecipChance = precipChance;
        mSummary = summary;
        mTimeZone = timeZone;

    }

    public int getIconId(){
        return Forecast.getIconId(mIcon);
    }

    public String getFormattedTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(mTimeZone));
        String timeString = formatter.format(new Date(mTime * 1000));
        return timeString;
    }


    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public int getTemp() {
        return (int)Math.round(mTemp);
    }

    public void setTemp(double temp) {
        mTemp = temp;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }

    public int getPrecipChance() {
        return (int)Math.round(mPrecipChance * 100);
    }

    public void setPrecipChance(double precipChance) {
        mPrecipChance = precipChance;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }
}
