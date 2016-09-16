package com.bootsysoftware.weatherapp.controller;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.bootsysoftware.weatherapp.R;
import com.bootsysoftware.weatherapp.adapters.DayAdapter;
import com.bootsysoftware.weatherapp.model.Day;

public class DailyForecastActivity extends ListActivity {
    private Day[] mDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);

        String[] daysOfTheWeek =
                { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };

        DayAdapter adapter = new DayAdapter(this, mDays);
    }
}
