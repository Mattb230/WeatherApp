package com.bootsysoftware.weatherapp.controller;

import android.content.Context;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    
    private CurrentForecast mCurrentForecast;

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
                            mCurrentForecast = getCurrentDetails(jsonData);
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
        mTempTextView.setText(mCurrentForecast.getTemp() + "");
        mTimeTextView.setText("At " + mCurrentForecast.getFormattedTime() + " it will be");
        mHumidityValueTextView.setText(mCurrentForecast.getHumidity() + "" );
        mPrecipValueTextView.setText(mCurrentForecast.getPrecipChance() + "%");
        mSummaryTextView.setText(mCurrentForecast.getSummary() + "");
        //get the drawable object and set it as the icon
        Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), mCurrentForecast.getIconId());
        mIconImageView.setImageDrawable(drawable);
    }

    private CurrentForecast getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        //String timeZone =
        //Log.i(TAG, "From JSON: " + timeZone);

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
}//end class

