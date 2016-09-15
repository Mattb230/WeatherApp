package com.bootsysoftware.weatherapp.controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bootsysoftware.weatherapp.R;
import com.bootsysoftware.weatherapp.model.CurrentWeather;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    
    private CurrentWeather mCurrentWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create the URL and store it in forecastUrL
        String apiKey = "c62c5864bec90aa70b94605fb5490a04";
        double lat =37.8267;
        double lon =-122.423;
        String forecastURL = "https://api.forecast.io/forecast/" + apiKey + "/" + lat + "," + lon;
        Toast.makeText(MainActivity.this, "Hello World!", Toast.LENGTH_SHORT).show();

        //Srart stuff for network call here
        if(isNetworkAvailable()) {
            //Create the okHttpClient and build the request
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(forecastURL).build();
            //make the HTTP call
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        //store the response raw text in the jsonData string
                        String jsonData = response.body().string();
                        if (response.isSuccessful()) {
                            //pass the json raw string to method, which will return a Current Weather
                            //object populated with the selected data
                            mCurrentWeather = getCurrentDetails(jsonData);

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
    }//end onCreate

    private CurrentWeather getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        //String timeZone =
        //Log.i(TAG, "From JSON: " + timeZone);

        JSONObject currently = forecast.getJSONObject("currently");

        CurrentWeather currentWeather = new CurrentWeather(
                currently.getString("icon"),
                currently.getLong("time"),
                currently.getDouble("temperature"),
                currently.getDouble("humidity"),
                currently.getDouble("precipProbability"),
                currently.getString("summary"),
                forecast.getString("timezone")
        );

        Log.d(TAG, currentWeather.getFormattedTime());

        return currentWeather;
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

