package com.bootsysoftware.weatherapp.controller;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bootsysoftware.weatherapp.R;
import com.bootsysoftware.weatherapp.model.Hour;

import java.util.Arrays;

public class HourlyForecastActivity extends AppCompatActivity {

    private Hour[] mHours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_forecast);

        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(MainActivity.HOURLY_FORECAST);
        mHours = Arrays.copyOf(parcelables, parcelables.length, Hour[].class);
    }
}
