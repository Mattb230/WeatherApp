package com.bootsysoftware.weatherapp.model;

/**
 * Created by Matthew Boydston on 9/16/2016.
 */
public class Forecast {
    private CurrentForecast mCurrentForecast;
    private Hour[] mHourlyForecast;
    private Day[] mDailyForecast;

    public CurrentForecast getCurrentForecast() {
        return mCurrentForecast;
    }

    public void setCurrentForecast(CurrentForecast currentForecast) {
        mCurrentForecast = currentForecast;
    }

    public Hour[] getHourlyForecast() {
        return mHourlyForecast;
    }

    public void setHourlyForecast(Hour[] hourlyForecast) {
        mHourlyForecast = hourlyForecast;
    }

    public Day[] getDailyForecast() {
        return mDailyForecast;
    }

    public void setDailyForecast(Day[] dailyForecast) {
        mDailyForecast = dailyForecast;
    }
}
