package com.bootsysoftware.weatherapp.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bootsysoftware.weatherapp.R;
import com.bootsysoftware.weatherapp.model.CurrentForecast;
import com.bootsysoftware.weatherapp.model.Day;
import com.bootsysoftware.weatherapp.model.Forecast;
import com.bootsysoftware.weatherapp.model.Hour;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String DAILY_FORECAST = "DAILY_FORECAST";
    //private CurrentForecast mCurrentForecast;
    private Forecast mForecast;
    private Day[] mDay;
    private Hour[] mHour;

    @BindView(R.id.timeTextView) TextView mTimeTextView;
    @BindView(R.id.tempTextView) TextView mTempTextView;
    @BindView(R.id.humidityValueTextView) TextView mHumidityValueTextView;
    @BindView(R.id.precipValueTextView) TextView mPrecipValueTextView;
    @BindView(R.id.summaryTextView) TextView mSummaryTextView;
    @BindView(R.id.iconImageView) ImageView mIconImageView;
    @BindView(R.id.refreshImageView) ImageView mRefreshImageView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mProgressBar.setVisibility(View.INVISIBLE);

        final double lat =37.8267;
        final double lon =-122.423;

        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForecast(lat, lon);
            }
        });
        getForecast(lat, lon);


    }//end onCreate

    private void getForecast(Double lat, Double lon) {
        //create the URL and store it in forecastUrL
        String apiKey = "c62c5864bec90aa70b94605fb5490a04";

        String forecastURL = "https://api.forecast.io/forecast/" + apiKey + "/" + lat + "," + lon;

        //Srart stuff for network call here
        if(isNetworkAvailable()) {
            toggleRefresh();
            //Create the okHttpClient and build the request
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(forecastURL).build();
            //make the HTTP call
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    try {
                        //store the response raw text in the jsonData string
                        String jsonData = response.body().string();
                        if (response.isSuccessful()) {
                            //pass the json raw string to method, which will return a Current Weather
                            //object populated with the selected data
                            //mCurrentForecast = getCurrentDetails(jsonData);
                            mForecast = parseForecastDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });
                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });//end call.enqueue
        } else {
            Toast.makeText(MainActivity.this, R.string.network_unavaileString, Toast.LENGTH_SHORT).show();
        }
    }

    private void toggleRefresh() {
        if(mProgressBar.getVisibility() == View.INVISIBLE){
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshImageView.setVisibility(View.INVISIBLE);
        } else {
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshImageView.setVisibility(View.VISIBLE);
        }
    }

    private void updateDisplay() {
        CurrentForecast current = mForecast.getCurrentForecast();

        mTempTextView.setText(current.getTemp() + "");
        mTimeTextView.setText("At " + current.getFormattedTime() + " it will be");
        mHumidityValueTextView.setText(current.getHumidity() + "" );
        mPrecipValueTextView.setText(current.getPrecipChance() + "%");
        mSummaryTextView.setText(current.getSummary() + "");
        //get the drawable object and set it as the icon
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), current.getIconId());
        mIconImageView.setImageDrawable(drawable);
    }

    private Forecast parseForecastDetails(String jsonData) throws JSONException {
        Forecast forecast = new Forecast();
        forecast.setCurrentForecast(getCurrentDetails(jsonData));

        forecast.setHourlyForecast(getHourlyForecast(jsonData));
        forecast.setDailyForecast(getDailyForecast(jsonData));
        return forecast;
    }

    private Day[] getDailyForecast(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        JSONObject daily = forecast.getJSONObject("daily");
        JSONArray data = daily.getJSONArray("data");

        Day[] days = new Day[data.length()];

        for (int i = 0; i < data.length(); i++) {
            JSONObject jsonDay = data.getJSONObject(i);
            Day day = new Day(
                    jsonDay.getLong("time"),
                    jsonDay.getString("summary"),
                    jsonDay.getDouble("temperatureMax"),
                    jsonDay.getString("icon"),
                    forecast.getString("timezone")
            );//end hour constructor
            days[i] = day;
        }//end for
        return days;
    }

    private Hour[] getHourlyForecast(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        JSONObject hourly = forecast.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");

        Hour[] hours = new Hour[data.length()];

        for (int i = 0; i < data.length(); i++) {
            JSONObject jsonHour = data.getJSONObject(i);
            Hour hour = new Hour(
                    jsonHour.getLong("time"),
                    jsonHour.getString("summary"),
                    jsonHour.getDouble("temperature"),
                    jsonHour.getString("icon"),
                    forecast.getString("timezone")
            );//end hour constructor
            hours[i] = hour;
        }//end for
        return hours;
    }//end getHourlyForecast

    private CurrentForecast getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        //String timeZone = forecast.getString("timezone");

        JSONObject currently = forecast.getJSONObject("currently");

        CurrentForecast currentForecast = new CurrentForecast(
                currently.getString("icon"),
                currently.getLong("time"),
                currently.getDouble("temperature"),
                currently.getDouble("humidity"),
                currently.getDouble("precipProbability"),
                currently.getString("summary"),
                forecast.getString("timezone")
        );

        Log.d(TAG, currentForecast.getFormattedTime());

        return currentForecast;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        //check if network exists and check if it is connected
        if(networkInfo != null && networkInfo.isConnected()){
            isAvailable = true;
        }
        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }

    @OnClick(R.id.dailyButton)
    public void startDailyActivity(View view){
        Intent intent = new Intent(this, DailyForecastActivity.class);
        intent.putExtra(DAILY_FORECAST, mForecast.getDailyForecast());
        startActivity(intent);
    }
}//end class

